package org.example.tradingsimulation.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private String transactionType;
    private String transactionPair;
    private String status;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;
    private String source;
    private LocalDateTime createdAt;
    private Long userId;


}

