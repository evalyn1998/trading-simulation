package org.example.tradingsimulation.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tradingsimulation.enums.Source;
import org.example.tradingsimulation.enums.TransactionPair;

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
    @Enumerated(EnumType.STRING)
    private TransactionPair transactionPair;

    private BigDecimal bidPrice; //SELL

    private BigDecimal askPrice; //BUY

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private Source askSource;

    @Enumerated(EnumType.STRING)
    private Source bidSource;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}
