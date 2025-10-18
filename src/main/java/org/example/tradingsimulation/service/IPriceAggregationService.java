package org.example.tradingsimulation.service;

import org.example.tradingsimulation.Enums.TransactionPair;
import org.example.tradingsimulation.models.PriceAggregation;

import java.util.List;

public interface IPriceAggregationService {
    List<PriceAggregation> getLatestPriceAggregations();
    PriceAggregation getLatestPriceAggregation(TransactionPair transactionPair);
    void generateAndStorePriceAggregations();

}
