package org.example.tradingsimulation.service;

import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.enums.TransactionPair;
import org.example.tradingsimulation.models.PriceAggregation;
import org.example.tradingsimulation.repository.PriceAggregationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PriceAggregationService implements IPriceAggregationService {
    @Autowired
    private PriceAggregationRespository priceAggregationRespository;

    @Autowired
    private IntegrationService integrationService;

    @Override
    public void generateAndStorePriceAggregations() {
        List<PriceAggregation> bestPriceAggregation= integrationService.aggregatePrices();
        priceAggregationRespository.saveAll(bestPriceAggregation);
    }

    /**
     * Returns the latest price aggregation for a given transaction pair.
     * Create an api to retrieve the latest best aggregated price.(2)
     * */
    @Override
    public PriceAggregation getLatestPriceByPair(TransactionPair transactionPair) {
        return priceAggregationRespository.findTopByTransactionPairOrderByTimestampDesc(transactionPair);
    }
    @Override
    public List<PriceAggregation> getAllLatestPrices() {
        return priceAggregationRespository.findLatestPrices();
    }


}
