package org.example.tradingsimulation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.tradingsimulation.enums.TransactionPair;
import org.example.tradingsimulation.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TradeResponse {
    private Long id;
    private String username;
    private TransactionType transactionType;
    private TransactionPair transactionPair;
    private BigDecimal qty;
    private BigDecimal price;
    private BigDecimal totalAmount;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "TradeResponse{" +
                "id=" + id +
                ", usernname='" + username + '\'' +
                ", transactionType=" + transactionType +
                ", qty=" + qty +
                ", price=" + price +
                ", totalAmount=" + totalAmount +
                ", timestamp=" + timestamp +
                '}';
    }
}
