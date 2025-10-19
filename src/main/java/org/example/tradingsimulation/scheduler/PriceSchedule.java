package org.example.tradingsimulation.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.service.PriceAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler to periodically generate and store price aggregations.
 * This task runs every 10 seconds.
 *
 */
@Component
@Slf4j
public class PriceSchedule {
    @Autowired
    private PriceAggregationService priceAggregationService;

    @Scheduled(fixedRate = 10000)
    public void scheduleGetPrices() {
        log.info("Scheduled Task: Generating and Storing Price Aggregations");
        priceAggregationService.generateAndStorePriceAggregations();
    }
}
