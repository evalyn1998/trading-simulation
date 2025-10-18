package org.example.tradingsimulation.repository;

import org.example.tradingsimulation.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
}
