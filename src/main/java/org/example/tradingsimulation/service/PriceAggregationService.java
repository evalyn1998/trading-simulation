package org.example.tradingsimulation.service;

import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.dtos.BestPriceResponse;
import org.example.tradingsimulation.models.PriceAggregation;
import org.example.tradingsimulation.repository.PriceAggregationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

/*   Retrieves the best bid and ask prices along with their respective timestamps and sources.
 @return a {@link BestPriceResponse} containing the best bid and ask prices, or null if no data is available.
*/
    @Override
    public BestPriceResponse getBestPrices() {
        PriceAggregation bestBid = priceAggregationRespository.findLatestBestBid();
        PriceAggregation bestAsk = priceAggregationRespository.findLatestBestAsk();

        if(bestBid != null || bestAsk != null) {
            BigDecimal bestBidPrice = bestBid != null ? bestBid.getBidPrice() : null;
            BigDecimal bestAskPrice = bestAsk != null ? bestAsk.getAskPrice() : null;

            LocalDateTime dateTimeBid = bestBid != null ? bestBid.getTimestamp() : null;
            LocalDateTime dateTimeAsk = bestAsk != null ? bestAsk.getTimestamp() : null;

            return new BestPriceResponse(
                    bestBidPrice,
                    bestAskPrice,
                    dateTimeBid,
                    dateTimeAsk,
                    bestBid != null ? bestBid.getBidSource() : null,
                    bestAsk != null ? bestAsk.getAskSource(): null
            );
        }
        return null;
    }


}
