package org.example.tradingsimulation.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.dtos.ApiResponse;
import org.example.tradingsimulation.dtos.TransactionResponse;
import org.example.tradingsimulation.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/api/v1/transactions")
@Slf4j
public class TransactionController {
    @Autowired
    private TradingService tradingService;

    /**
     * GET /api/v1/transactions/users/transactions?username={username}
     * Request Param: username (default_user)
     * Create an api to retrieve all transactions for a user. (5)
     * */
    @GetMapping("/users/transactions")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getUserTransactions(@RequestParam String username) {
        log.info("Fetching transactions for user: {}", username);
        List<TransactionResponse> transactions = tradingService.getUserTransactions(username);
        return ResponseEntity.ok(ApiResponse.success(transactions));
    }
}
