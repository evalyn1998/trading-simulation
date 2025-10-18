package org.example.tradingsimulation.repository;

import org.example.tradingsimulation.models.Transactions;
import org.example.tradingsimulation.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findByUserOrderByCreatedAtCreatedAtDesc(UserInfo user);
}
