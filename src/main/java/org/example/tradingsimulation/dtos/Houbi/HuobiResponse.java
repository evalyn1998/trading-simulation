package org.example.tradingsimulation.dtos.Houbi;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class HuobiResponse { // following the Huobi API response structure
    private List<Huobidata> data;
    private String status;
    private Long timestamp;
    @Getter
    @Setter
    public static class Huobidata {
        private String symbol;
        private BigDecimal open;
        private BigDecimal high;
        private BigDecimal low;
        private BigDecimal close;
        private BigDecimal amount;
        private BigDecimal vol;
        private BigDecimal count;
        private BigDecimal bid;
        private BigDecimal bidSize;
        private BigDecimal ask;
        private BigDecimal askSize;


    }
}
