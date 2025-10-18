package org.example.tradingsimulation.service;

import org.example.tradingsimulation.dtos.TradeResponse;
import org.example.tradingsimulation.models.TradeRequest;
import org.example.tradingsimulation.models.Transactions;

import java.util.List;

public interface ITradingService {
    TradeResponse executeTrade(TradeRequest request);
    List<Transactions> getUserTransactions(String username);
}
