package org.example.tradingsimulation.repository;

import org.example.tradingsimulation.enums.TransactionPair;
import org.example.tradingsimulation.models.PriceAggregation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PriceAggregationRespository extends JpaRepository<PriceAggregation, Long> {
// Latest record for a specific trading pair
    PriceAggregation findTopByTransactionPairOrderByTimestampDesc(TransactionPair transactionPair);

    // Latest records for all trading pairs
    @Query("""
        SELECT p FROM PriceAggregation p
        WHERE p.id IN (
            SELECT MAX(p2.id)
            FROM PriceAggregation p2
            GROUP BY p2.transactionPair
        )
        ORDER BY p.transactionPair
    """)
    List<PriceAggregation> findLatestPrices();
}
