package org.example.tradingsimulation.service;

import org.example.tradingsimulation.enums.TransactionPair;
import org.example.tradingsimulation.models.PriceAggregation;

import java.util.List;


public interface IPriceAggregationService {
    void generateAndStorePriceAggregations();
    PriceAggregation getLatestPriceByPair(TransactionPair transactionPair);
    List<PriceAggregation> getAllLatestPrices();

}
