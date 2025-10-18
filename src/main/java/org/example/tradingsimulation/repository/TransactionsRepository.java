package org.example.tradingsimulation.repository;

import org.example.tradingsimulation.models.Transactions;
import org.example.tradingsimulation.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    /**
     * Find all transactions for a given user, ordered by creation date in descending order.
     *
     * @param user the user whose transactions are to be retrieved
     * @return a list of transactions for the specified user, ordered by creation date descending
     *
     * can use for transaction history (5)
     */
    List<Transactions> findByUserOrderByCreatedAtDesc(UserInfo user);
}
