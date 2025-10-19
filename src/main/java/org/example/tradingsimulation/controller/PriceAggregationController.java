package org.example.tradingsimulation.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.dtos.ApiResponse;
import org.example.tradingsimulation.dtos.BestPriceResponse;
import org.example.tradingsimulation.enums.TransactionPair;
import org.example.tradingsimulation.models.PriceAggregation;
import org.example.tradingsimulation.service.PriceAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("api/v1/price-aggregation")
public class PriceAggregationController {

    @Autowired
    private PriceAggregationService priceAggregationService;
    /**
     * GET the best prices
     * GET /api/v1/price-aggregation/best-prices
     * Create an api to retrieve the best prices
     * */
    @GetMapping("/latest-prices")
    public ResponseEntity<ApiResponse<List<BestPriceResponse>>> getLatestPrices() {
        List<PriceAggregation> prices = priceAggregationService.getLatestPrices();

        List<BestPriceResponse> response = prices.stream()
                .map(p -> new BestPriceResponse(
                        p.getBidPrice(),
                        p.getAskPrice(),
                        p.getAskSource(),
                        p.getBidSource(),
                        p.getTransactionPair()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success("Latest prices retrieved successfully", response));
    }

    @GetMapping("/latest/{tradingPair}")
    public ResponseEntity<ApiResponse<BestPriceResponse>> getLatestPriceByPair(
            @PathVariable TransactionPair tradingPair) {
        PriceAggregation price = priceAggregationService.getLatestPriceAggregation(tradingPair);

        BestPriceResponse response = new BestPriceResponse(
                price.getBidPrice(),
                price.getAskPrice(),
                price.getAskSource(),
                price.getBidSource(),
                price.getTransactionPair()
        );

        return ResponseEntity.ok(ApiResponse.success("Latest price retrieved successfully", response));
    }
}
