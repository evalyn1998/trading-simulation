package org.example.tradingsimulation.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.tradingsimulation.enums.Source;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PriceData {
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
    private Enum<Source> source;
}
