package org.example.tradingsimulation.service;

import org.example.tradingsimulation.dtos.BestPriceResponse;


public interface IPriceAggregationService {
    void generateAndStorePriceAggregations();
    BestPriceResponse getBestPrices();

}
