package org.example.tradingsimulation.service;

import org.example.tradingsimulation.dtos.WalletBalanceDto;

public interface IWalletService {
    WalletBalanceDto getWalletBalance(String username);

}
