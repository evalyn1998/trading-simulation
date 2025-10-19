package org.example.tradingsimulation.service;

import org.example.tradingsimulation.dtos.TradeResponse;
import org.example.tradingsimulation.dtos.TransactionResponse;
import org.example.tradingsimulation.models.TradeRequest;

import java.util.List;

public interface ITradingService {
    TradeResponse executeTrade(TradeRequest request);
    List<TransactionResponse> getUserTransactions(String username);
}
