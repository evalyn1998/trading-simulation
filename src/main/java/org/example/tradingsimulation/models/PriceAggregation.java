package org.example.tradingsimulation.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tradingsimulation.Enums.TransactionPair;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "price_aggregation", indexes = {
        @Index(name = "transaction_pair", columnList = "transactionPair"),
        @Index(name = "timestamp", columnList = "timestamp DESC")
})
public class PriceAggregation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TransactionPair transactionPair;
    private BigDecimal bidPrice; //SELL
    private BigDecimal askPrice; //BUY
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}
