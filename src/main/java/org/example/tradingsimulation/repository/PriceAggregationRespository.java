package org.example.tradingsimulation.repository;

import org.example.tradingsimulation.Enums.TransactionPair;
import org.example.tradingsimulation.models.PriceAggregation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceAggregationRespository extends JpaRepository<PriceAggregation, Long> {

    /**
     * Find the latest PriceAggregation for a given TransactionPair.
     *
     * @param transactionPair the transaction pair to search for
     * @return the latest PriceAggregation for the specified TransactionPair
     */
    @Query("SELECT p FROM PriceAggregation p " +
            "WHERE p.transactionPair = :transactionPair " +
            "ORDER BY p.timestamp DESC")
    PriceAggregation findLatestTransactionPair(TransactionPair transactionPair);

    /**
     * Find the latest PriceAggregations for all TransactionPairs.
     *
     * @return a list of the latest PriceAggregations for each TransactionPair
     */
    @Query("SELECT p FROM PriceAggregation p " +
            "WHERE p.timestamp IN" +
            "(SELECT MAX(p2.timestamp) FROM PriceAggregation p2 " +
            "GROUP BY p2.transactionPair)")
    List<PriceAggregation> findLastestPrices();
}
