package org.example.tradingsimulation.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.enums.Currency;
import org.example.tradingsimulation.enums.TransactionPair;
import org.example.tradingsimulation.enums.TransactionStatus;
import org.example.tradingsimulation.enums.TransactionType;
import org.example.tradingsimulation.dtos.TradeResponse;
import org.example.tradingsimulation.exception.InsufficientBalanceException;
import org.example.tradingsimulation.exception.InvalidTransactionPairException;
import org.example.tradingsimulation.models.*;
import org.example.tradingsimulation.repository.PriceAggregationRespository;
import org.example.tradingsimulation.repository.TransactionsRepository;
import org.example.tradingsimulation.repository.UserInfoRepository;
import org.example.tradingsimulation.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TradingService implements ITradingService {
    @Autowired
    private TransactionsRepository transactionRepository;
    @Autowired
    private WalletRepository walletRespository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PriceAggregationRespository priceAggregationRespository;

    private Currency getCryptoFromTradingPair(TransactionPair tradingPair) {
        switch (tradingPair) {
            case BTCUSDT -> {
                return Currency.BTC;
            }
            case ETHUSDT -> {
                return Currency.ETH;
            }
            default -> throw new IllegalArgumentException("Unsupported trading pair: " + tradingPair);
        }
    }

    private Currency getFiatFromTradingPair(TransactionPair tradingPair) {
        return switch (tradingPair) {
            case ETHUSDT, BTCUSDT -> Currency.USDT;
            default -> throw new IllegalArgumentException("Unsupported trading pair: " + tradingPair);
        };
    }
    @Override
    @Transactional
    public TradeResponse executeTrade(TradeRequest request) {
        log.info("Processing trade: {} {} for user {}",
                request.getTransactionType(), request.getQty(), request.getUsername());

        // 1. Find user
        UserInfo user = userInfoRepository.findByUsername(request.getUsername());


        // 2. Get latest aggregated price
        PriceAggregation latestPrice = priceAggregationRespository
                .findLatestByTradingPair(request.getTransactionPair());

        // 3. Determine price based on trade type
        // BUY: use askPrice (lowest selling price)
        // SELL: use bidPrice (highest buying price)
        BigDecimal tradePrice = request.getTransactionType() == TransactionType.BUY
                ? latestPrice.getAskPrice()
                : latestPrice.getBidPrice();
        if (tradePrice == null) {
            throw new InvalidTransactionPairException(
                    "No " + (request.getTransactionType() == TransactionType.BUY ? "ask" : "bid") +
                            " price available for " + request.getTransactionPair());
        }
        // 4. Calculate total amount
        BigDecimal totalAmount = request.getQty().multiply(tradePrice);

        //5. Currency cryptoCurrency extraction
        Currency cryptoCurrency = getCryptoFromTradingPair(request.getTransactionPair());
        Currency fiatCurrency = getFiatFromTradingPair(request.getTransactionPair());

        // 5. Execute trade based on type
        if (request.getTransactionType() == TransactionType.BUY) {
            executeBuy(user, cryptoCurrency,fiatCurrency, request.getQty(), totalAmount);
        } else {
            executeSell(user, cryptoCurrency, fiatCurrency, request.getQty(), totalAmount);
        }


        // 7. Create transaction record
        Transactions transaction = new Transactions();
        transaction.setTransactionType(request.getTransactionType());
        transaction.setTransactionPair(request.getTransactionPair());
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setQuantity(request.getQty());
        transaction.setPrice(tradePrice);
        transaction.setTotalAmount(totalAmount);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setSource(request.getTransactionType()==TransactionType.BUY ? latestPrice.getAskSource() : latestPrice.getBidSource());


        transaction = transactionRepository.save(transaction);

        log.info("Trade completed: Transaction ID {}", transaction.getId());

        // 8. Return response
        return new TradeResponse(
                transaction.getId(),
                user.getUsername(),
                transaction.getTransactionType(),
                transaction.getTransactionPair(),
                transaction.getQuantity(),
                transaction.getPrice(),
                transaction.getTotalAmount(),
                transaction.getCreatedAt()
        );
    }
    private void executeBuy(UserInfo user, Currency cryptoCurrency, Currency fiatCurrency,
                            BigDecimal quantity, BigDecimal totalAmount) {
        log.info("Executing BUY: {} {} for {} {}", quantity, cryptoCurrency, totalAmount, fiatCurrency);

        // Deduct fiat from user's wallet
        Wallet fiatWallet = getOrCreateWallet(user, fiatCurrency);
        if (fiatWallet.getBalance().compareTo(totalAmount) < 0) {
            throw new InsufficientBalanceException(
                    String.format("Insufficient %s balance. Required: %s, Available: %s",
                            fiatCurrency, totalAmount, fiatWallet.getBalance()));
        }
        fiatWallet.setBalance(fiatWallet.getBalance().subtract(totalAmount));
        walletRespository.save(fiatWallet);

        // Add crypto to user's wallet
        Wallet cryptoWallet = getOrCreateWallet(user, cryptoCurrency);
        cryptoWallet.setBalance(cryptoWallet.getBalance().add(quantity));
        walletRespository.save(cryptoWallet);

        log.info("BUY executed: {} balance = {}, {} balance = {}",
                fiatCurrency, fiatWallet.getBalance(),
                cryptoCurrency, cryptoWallet.getBalance());
    }

    private void executeSell(UserInfo user, Currency cryptoCurrency, Currency fiatCurrency,
                             BigDecimal quantity, BigDecimal totalAmount) {
        log.info("Executing SELL: {} {} for {} {}", quantity, cryptoCurrency, totalAmount, fiatCurrency);

        // Deduct crypto from user's wallet
        Wallet cryptoWallet = getOrCreateWallet(user, cryptoCurrency);
        if (cryptoWallet.getBalance().compareTo(quantity) < 0) {
            throw new InsufficientBalanceException(
                    String.format("Insufficient %s balance. Required: %s, Available: %s",
                            cryptoCurrency, quantity, cryptoWallet.getBalance()));
        }
        cryptoWallet.setBalance(cryptoWallet.getBalance().subtract(quantity));
        walletRespository.save(cryptoWallet);

        // Add fiat to user's wallet
        Wallet fiatWallet = getOrCreateWallet(user, fiatCurrency);
        fiatWallet.setBalance(fiatWallet.getBalance().add(totalAmount));
        walletRespository.save(fiatWallet);

        log.info("SELL executed: {} balance = {}, {} balance = {}",
                cryptoCurrency, cryptoWallet.getBalance(),
                fiatCurrency, fiatWallet.getBalance());
    }

    private Wallet getOrCreateWallet(UserInfo user, Currency currency) {
        return walletRespository.findByUserAndCurrency(user, currency);
    }


    @Override
    public List<Transactions> getUserTransactions(String username) {
        UserInfo user = userInfoRepository.findByUsername(username);
        return transactionRepository.findByUserOrderByCreatedAtDesc(user);
    }

}


