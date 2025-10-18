package org.example.tradingsimulation.service;

import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.dtos.WalletBalanceDto;
import org.example.tradingsimulation.models.UserInfo;
import org.example.tradingsimulation.models.Wallet;
import org.example.tradingsimulation.repository.UserInfoRepository;
import org.example.tradingsimulation.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WalletService implements IWalletService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private WalletRepository walletRespository;

    /**
     * Retrieves the wallet balance for a given username.
     *
     * @param username the username of the wallet owner
     * @return a {@link WalletBalanceDto} containing the username and a list of balances for each currency
     * @throws IllegalArgumentException if the username is not found
     *
     *
     *
     *  */
    @Override
    public WalletBalanceDto getWalletBalance(String username) {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        List<Wallet> wallets = walletRespository.getWalletsByUser(userInfo);
        List<WalletBalanceDto.Balance> balances = wallets.stream().map(wallet -> {
            WalletBalanceDto.Balance balance = new WalletBalanceDto.Balance();
            balance.setCurrency(wallet.getCurrency());
            balance.setAmount(wallet.getBalance());
            return balance;
        }).toList();
        WalletBalanceDto walletBalanceDto = new WalletBalanceDto();
        walletBalanceDto.setUsername(username);
        walletBalanceDto.setBalances(balances);
        return walletBalanceDto;

    }
}
