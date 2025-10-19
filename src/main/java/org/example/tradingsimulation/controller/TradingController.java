package org.example.tradingsimulation.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.dtos.ApiResponse;
import org.example.tradingsimulation.dtos.TradeResponse;
import org.example.tradingsimulation.models.TradeRequest;
import org.example.tradingsimulation.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/trading")
@RequiredArgsConstructor
@Slf4j
public class TradingController {

    @Autowired
    private TradingService tradingService;

    /**
     * POST /api/v1/trading/trade
     * Trade Request includes:
             * username, (default_user)
             * transactionPair,
             * transactionType
             * (BUY/SELL),
             * qty
     * Create an api which allows users to trade based on the latest best aggregated
     * price. (3)
     * */
    @PostMapping("/trade")
    public ResponseEntity<ApiResponse<TradeResponse>> executeTrade(@RequestBody TradeRequest request) {
        log.info("Received trade request: {} {} {} for user {}",
                request.getTransactionType(), request.getQty(), request.getTransactionPair(), request.getUsername());

        TradeResponse response = tradingService.executeTrade(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Trade executed successfully", response));
    }
}
