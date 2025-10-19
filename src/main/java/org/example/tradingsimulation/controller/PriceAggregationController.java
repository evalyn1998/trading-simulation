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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("api/v1/price-aggregation")
public class PriceAggregationController {

    @Autowired
    private PriceAggregationService priceAggregationService;
    /**
     * GET all latest prices
     * GET /api/v1/price-aggregation/latest-prices
     * Create an api to retrieve all latest prices
     * */
    @GetMapping("/latest-prices")
    public ResponseEntity<ApiResponse<List<BestPriceResponse>>> getAllLatestPrices() {
        List<PriceAggregation> prices = priceAggregationService.getAllLatestPrices();

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

    /**
     * GET the best price by trading pair
     * GET /api/v1/price-aggregation/latest?transactionPair={transactionPair}
     * ONLY BTCUSDT and ETHUSDT are supported
     * Create an api to retrieve the best price by trading pair (2)
     * */
    @GetMapping("/latest")
    public ResponseEntity<ApiResponse<BestPriceResponse>> getLatestPriceByPair(
            @RequestParam TransactionPair transactionPair) {
        PriceAggregation price = priceAggregationService.getLatestPriceByPair(transactionPair);

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
