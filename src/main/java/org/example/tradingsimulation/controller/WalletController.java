package org.example.tradingsimulation.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.dtos.ApiResponse;
import org.example.tradingsimulation.dtos.WalletBalanceDto;
import org.example.tradingsimulation.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Slf4j
@Controller
@RequestMapping("api/v1/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    /**
     * GET all the wallet balances
     * GET /api/v1/wallet/balances?username={username}
     *  username (default_user)
     * Create an api to retrieve the user’s crypto currencies wallet balance (4)
     */
    @GetMapping("/balances")
    public ResponseEntity<ApiResponse<WalletBalanceDto>> getWalletBalance(@RequestParam String username) {
        WalletBalanceDto walletBalanceDto = walletService.getWalletBalance(username);
        ApiResponse<WalletBalanceDto> response = new ApiResponse<>(true,"Retrieved!",walletBalanceDto);
        return ResponseEntity.ok(response);

    }


}
