package org.example.tradingsimulation.service;

import org.example.tradingsimulation.Enums.TransactionPair;
import org.example.tradingsimulation.models.PriceAggregation;

import java.util.List;

public interface IPriceAggregationService {
    void generateAndStorePriceAggregations();
    BestPriceResponse getBestPrices();

}
