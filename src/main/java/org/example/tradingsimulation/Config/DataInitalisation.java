package org.example.tradingsimulation.Config;

import org.example.tradingsimulation.Enums.Currency;
import org.example.tradingsimulation.models.UserInfo;
import org.example.tradingsimulation.models.Wallet;
import org.example.tradingsimulation.repository.UserInfoRepository;
import org.example.tradingsimulation.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Abstract class for data initialization at application startup.
 * To start data initialization so that the 50000 wallet balances are created for each user.
 */
@Component
public class DataInitalisation implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitalisation.class);

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void run(String... args) {
        log.info("Starting data initialization...");
        // Check if there are no users in the repository, if so create a default user
        if (userInfoRepository.count() == 0) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername("default_user");
            userInfo.setCreatedAt(LocalDateTime.now());
            userInfoRepository.save(userInfo);

            Wallet moneyWallet = new Wallet();
            moneyWallet.setUser(userInfo);
            moneyWallet.setCurrency(Currency.USDT);
            moneyWallet.setBalance(new BigDecimal("50000.0"));
            moneyWallet.setUpdatedAt(LocalDateTime.now());
            walletRepository.save(moneyWallet);
            // Initialize money wallet for the default user with a balance of 50000

            Wallet BTCWallet = new Wallet();
            BTCWallet.setUser(userInfo);
            BTCWallet.setCurrency(Currency.BTC);
            BTCWallet.setBalance(BigDecimal.ZERO);
            BTCWallet.setUpdatedAt(LocalDateTime.now());
            walletRepository.save(BTCWallet);

            Wallet ETHWallet = new Wallet();
            ETHWallet.setUser(userInfo);
            ETHWallet.setCurrency(Currency.ETH);
            ETHWallet.setBalance(BigDecimal.ZERO);
            ETHWallet.setUpdatedAt(LocalDateTime.now());
            walletRepository.save(ETHWallet);

            log.info("Initalised User: " + userInfo.getUsername() + " with " + moneyWallet.getBalance() + "USDT");


        }
    }
}
