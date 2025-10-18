package org.example.tradingsimulation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.tradingsimulation.Enums.Source;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BestPriceResponse {
    private BigDecimal bestBidPrice;
    private BigDecimal bestAskPrice;
    private LocalDateTime bestBidTimestamp;
    private LocalDateTime bestAskTimestamp;
    private Enum<Source> askSource;
    private Enum<Source> bidSource;
}


