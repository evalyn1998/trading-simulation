package org.example.tradingsimulation.models;

import lombok.*;
import org.example.tradingsimulation.enums.TransactionPair;
import org.example.tradingsimulation.enums.TransactionType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeRequest {
    @NonNull
    private String username;
    @NonNull
    private TransactionType transactionType;
    @NonNull
    private BigDecimal qty;
    @NonNull
    private TransactionPair transactionPair;
}
