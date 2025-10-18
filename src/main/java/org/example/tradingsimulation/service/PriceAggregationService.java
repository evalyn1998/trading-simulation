package org.example.tradingsimulation.service;

import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.dtos.BestPriceResponse;
import org.example.tradingsimulation.models.PriceAggregation;
import org.example.tradingsimulation.repository.PriceAggregationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceAggregationService implements IPriceAggregationService {
    @Autowired
    private PriceAggregationRespository priceAggregationRespository;

    @Autowired
    private IntegrationService integrationService;

    @Override
    public void generateAndStorePriceAggregations() {
        PriceAggregation bestPriceAggregation= integrationService.aggregatePrices();
        priceAggregationRespository.save(bestPriceAggregation);
    }

    /**
     * @return
     */
    @Override
    public BestPriceResponse getBestPrices() {
        return null;
    }


}
