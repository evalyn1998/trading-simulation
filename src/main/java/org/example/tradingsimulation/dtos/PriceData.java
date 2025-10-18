package org.example.tradingsimulation.dtos;


import java.math.BigDecimal;


public record PriceData(BigDecimal bidPrice, BigDecimal askPrice) {
}
