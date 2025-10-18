package org.example.tradingsimulation.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.tradingsimulation.Enums.Source;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BestPriceResponse {
    private BigDecimal bestBidPrice;
    private BigDecimal bestAskPrice;
    private LocalDateTime timestamp;
    private Enum<Source> source;
}


