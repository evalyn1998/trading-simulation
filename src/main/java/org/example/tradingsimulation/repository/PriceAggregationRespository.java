package org.example.tradingsimulation.repository;

import org.example.tradingsimulation.enums.TransactionPair;
import org.example.tradingsimulation.models.PriceAggregation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PriceAggregationRespository extends JpaRepository<PriceAggregation, Long> {

    @Query("SELECT p FROM PriceAggregation p WHERE p.transactionPair = :transactionPair ORDER BY p.timestamp DESC")
    PriceAggregation findLatestByTradingPair(TransactionPair tradingPair);

    @Query("SELECT p FROM PriceAggregation p WHERE p.id IN " +
            "(SELECT MAX(p2.id) FROM PriceAggregation p2 GROUP BY p2.transactionPair)")
    List<PriceAggregation> findLatestPrices();
}
