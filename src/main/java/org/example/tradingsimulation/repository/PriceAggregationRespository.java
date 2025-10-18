package org.example.tradingsimulation.repository;

import org.example.tradingsimulation.models.PriceAggregation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PriceAggregationRespository extends JpaRepository<PriceAggregation, Long> {

    @Query("SELECT p FROM PriceAggregation p ORDER BY p.timestamp DESC, p.askPrice ASC LIMIT 1")
    PriceAggregation findLatestBestAsk();

    @Query("SELECT p FROM PriceAggregation p ORDER BY p.timestamp DESC, p.bidPrice DESC LIMIT 1")
    PriceAggregation findLatestBestBid();
}
