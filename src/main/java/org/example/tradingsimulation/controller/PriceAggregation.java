package org.example.tradingsimulation.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.dtos.ApiResponse;
import org.example.tradingsimulation.dtos.BestPriceResponse;
import org.example.tradingsimulation.dtos.WalletBalanceDto;
import org.example.tradingsimulation.service.PriceAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("api/v1/price-aggregation")
public class PriceAggregation {

    @Autowired
    private PriceAggregationService priceAggregationService;
    /**
     * GET the best prices
     * GET /api/v1/price-aggregation/best-prices
     *
     *
     * Create an api to retrieve the best prices
     * */
    @GetMapping("/best-prices")
    public ResponseEntity<ApiResponse<BestPriceResponse>> getWalletBalance(@RequestParam String username) {
        BestPriceResponse bestPriceResponse = priceAggregationService.getBestPrices();
        ApiResponse<BestPriceResponse> response = new ApiResponse<>(true,"Retrieved!",bestPriceResponse);
        return ResponseEntity.ok(response);

    }
}
