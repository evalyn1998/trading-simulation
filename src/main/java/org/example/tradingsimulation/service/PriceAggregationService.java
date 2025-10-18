package org.example.tradingsimulation.service;

import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.Enums.TransactionPair;
import org.example.tradingsimulation.models.PriceAggregation;
import org.example.tradingsimulation.repository.PriceAggregationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PriceAggregationService implements IPriceAggregationService {
    @Autowired
    private PriceAggregationRespository priceAggregationRespository;

    @Autowired
    private IntegrationService integrationService;

    /**
     * @return
     */
    @Override
    public List<PriceAggregation> getLatestPriceAggregations() {
        return priceAggregationRespository.findLastestPrices();
    }

    /**
     * @param transactionPair
     * @return
     */
    @Override
    public PriceAggregation getLatestPriceAggregation(TransactionPair transactionPair) {
       return priceAggregationRespository.findLatestTransactionPair(transactionPair);
    }

    /**
     *
     */
    @Override
    public void generateAndStorePriceAggregations() {
        PriceAggregation bestPriceAggregation= integrationService.aggregatePrices();
        priceAggregationRespository.save(bestPriceAggregation);
    }


}
