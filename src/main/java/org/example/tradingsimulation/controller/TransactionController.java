package org.example.tradingsimulation.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.dtos.ApiResponse;
import org.example.tradingsimulation.dtos.TransactionResponse;
import org.example.tradingsimulation.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Controller
@RequestMapping("/api/v1/transactions")
@Slf4j
public class TransactionController {
    @Autowired
    private TradingService tradingService;

    @GetMapping("/users/{username}/transactions")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getUserTransactions(@PathVariable String username) {
        log.info("Fetching transactions for user: {}", username);

        List<TransactionResponse> transactions = tradingService.getUserTransactions(username);
        return ResponseEntity.ok(ApiResponse.success(transactions));
    }
}
