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
        System.out.println("Best Price Aggregations: " + bestPriceAggregation);
        priceAggregationRespository.saveAll(bestPriceAggregation);
    }

/*   Retrieves the best bid and ask prices along with their respective timestamps and sources.
 @return a {@link BestPriceResponse} containing the best bid and ask prices, or null if no data is available.
*/
    @Override
    public PriceAggregation getLatestPriceAggregation(TransactionPair transactionPair) {
        return priceAggregationRespository.findLatestByTradingPair(transactionPair);
    }
    @Override
    public List<PriceAggregation> getLatestPrices() {
        return priceAggregationRespository.findLatestPrices();
    }


}
