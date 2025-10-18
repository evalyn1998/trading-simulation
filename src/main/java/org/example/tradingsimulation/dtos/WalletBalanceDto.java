package org.example.tradingsimulation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tradingsimulation.Enums.Currency;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object for Wallet Balance information.
 * This class can be expanded to include fields such as currency type and balance amount.
 * Why?
 * This DTO is designed to encapsulate the wallet balance details of a user.
 * It includes:
 * - username: The username of the wallet owner.
 * - balances: A list of Balance objects representing different currency balances in the wallet.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletBalanceDto {
    private String username;
    private List<Balance> balances;

/** Inner class to represent individual currency balances.
 *  Why?
 *  This inner class encapsulates the details of each currency balance within the wallet.
 *  It includes:
 *  - currency: The type of currency (e.g., USD, EUR, BTC).
 *  - amount: The balance amount for that currency.
 * */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Balance {
        private Currency currency;
        private BigDecimal amount;

    @Override
    public String toString() {
        return "Balance{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }
}

    @Override
    public String toString() {
        return "WalletBalanceDto{" +
                "username='" + username + '\'' +
                ", balances=" + balances +
                '}';
    }
}
