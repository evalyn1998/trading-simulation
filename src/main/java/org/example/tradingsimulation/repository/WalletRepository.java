package org.example.tradingsimulation.repository;

import org.example.tradingsimulation.Enums.Currency;
import org.example.tradingsimulation.models.UserInfo;
import org.example.tradingsimulation.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> getWalletsByUser(UserInfo user);
    Wallet findByUserAndCurrency(UserInfo user, Currency currency);

}
