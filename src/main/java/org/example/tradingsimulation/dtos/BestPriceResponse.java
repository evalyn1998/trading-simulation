package org.example.tradingsimulation.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.tradingsimulation.enums.Source;
import org.example.tradingsimulation.enums.TransactionPair;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class BestPriceResponse {
    private BigDecimal bestBidPrice;

    private BigDecimal bestAskPrice;

    @Enumerated(EnumType.STRING)
    private Source askSource;

    @Enumerated(EnumType.STRING)
    private Source bidSource;

    private TransactionPair transactionPair;
}


