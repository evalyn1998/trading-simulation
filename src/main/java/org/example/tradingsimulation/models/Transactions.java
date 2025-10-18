package org.example.tradingsimulation.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tradingsimulation.enums.Source;
import org.example.tradingsimulation.enums.TransactionPair;
import org.example.tradingsimulation.enums.TransactionStatus;
import org.example.tradingsimulation.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions", indexes = {
        @Index(name = "user_id", columnList = "user_id"),
        @Index(name = "created_at", columnList = "created_at")
})
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private TransactionPair transactionPair;

    @Column(nullable = false, length = 4)
    @Enumerated(EnumType.STRING)
    private TransactionType type; // BUY or SELL

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal quantity; // Amount of crypto (ETH or BTC)

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal price; // Price per unit in USDT

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal totalAmount; // Total USDT amount (quantity * price)

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Source source;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = TransactionStatus.COMPLETED;
        }
        // Calculate total amount
        if (totalAmount == null && quantity != null && price != null) {
            totalAmount = quantity.multiply(price);
        }
    }
}