package org.example.tradingsimulation.dtos.Binance;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceResponse {// following the Binance API response structure
    private String symbol;
    private String bidPrice;
    private String bidqQy;
    private String askPrice;
    private String askQty;

    @Override
    public String toString() {
        return "BinanceResponse{" +
                "symbol='" + symbol + '\'' +
                ", bidPrice='" + bidPrice + '\'' +
                ", bidqQy='" + bidqQy + '\'' +
                ", askPrice='" + askPrice + '\'' +
                ", askQty='" + askQty + '\'' +
                '}';
    }
}
