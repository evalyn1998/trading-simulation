
# Crypto Trading API

By: Evalyn Low Wei Xuan

This project provides a simple crypto trading system with APIs for wallet management, trading, and price aggregation. It supports trading pairs like `BTCUSDT` and `ETHUSDT`. All API responses are wrapped in a consistent `ApiResponse<T>` format.

---

## Base URL

```

[http://localhost:8080/api/v1](http://localhost:8080/api/v1)

```

---

## 1. Get Wallet Balance

**Endpoint:**  

```

GET /balances?username={username}

````

**Description:**  
Retrieve the current wallet balances for a specific user.  

**Request Parameters:**  

| Parameter | Type   | Description          |
|-----------|--------|--------------------|
| username  | String | The username of user |

**Response Example:**

```json
{
    "success": true,
    "message": "Retrieved!",
    "data": {
        "username": "default_user",
        "balances": [
            {
                "currency": "USDT",
                "amount": 50000.00000000
            },
            {
                "currency": "BTC",
                "amount": 0E-8
            },
            {
                "currency": "ETH",
                "amount": 0E-8
            }
        ]
    }
}
````

---

## 2. Get User Transactions

**Endpoint:**

```
GET /users/transactions?username={username}
```

**Description:**
Retrieve all trading transactions for a specific user in descending order of creation time.

**Request Parameters:**

| Parameter | Type   | Description          |
| --------- | ------ | -------------------- |
| username  | String | The username of user |

**Response Example:**

```json
{
    "success": true,
    "data": [
        {
            "id": 1,
            "transactionType": "BUY",
            "transactionPair": "BTCUSDT",
            "status": "COMPLETED",
            "quantity": 0.00030000,
            "price": 106688.02000000,
            "totalAmount": 32.00640600,
            "source": "BINANCE",
            "createdAt": "2025-10-19T14:36:20.192519",
            "userId": 1
        }
    ]
}
```

---

## 3. Execute Trade

**Endpoint:**

```
POST /trade
```

**Description:**
Execute a trade (BUY or SELL) for a user. The trade uses the latest aggregated price:

* **BUY:** Uses the lowest ask price.
* **SELL:** Uses the highest bid price.

**Request Body Example:**

```json
{
  "username": "default_user",
  "transactionType": "BUY",
  "qty": 0.000300,
  "transactionPair": "BTCUSDT"
}
```

**Response Example:**

```json
{
    "success": true,
    "message": "Trade executed successfully",
    "data": {
        "id": 1,
        "username": "default_user",
        "transactionType": "BUY",
        "transactionPair": "BTCUSDT",
        "qty": 0.000300,
        "price": 106688.02,
        "totalAmount": 32.00640600,
        "timestamp": "2025-10-19T14:36:20.192519"
    }
}
```

---

## 4. Get All Latest Prices

**Endpoint:**

```
GET /latest-prices
```

**Description:**
Retrieve the latest best prices for all supported trading pairs (`BTCUSDT`, `ETHUSDT`).

**Response Example:**

```json
{
    "success": true,
    "message": "Latest prices retrieved successfully",
    "data": [
        {
            "bestBidPrice": 106590.90,
            "bestAskPrice": 106558.62,
            "askSource": "BINANCE",
            "bidSource": "HUOBI",
            "transactionPair": "BTCUSDT"
        },
        {
            "bestBidPrice": 3877.07,
            "bestAskPrice": 3876.80,
            "askSource": "HUOBI",
            "bidSource": "BINANCE",
            "transactionPair": "ETHUSDT"
        }
    ]
}
```

---

## 5. Get Latest Price by Trading Pair

**Endpoint:**

```
GET /latest?transactionPair={transactionPair}
```

**Description:**
Retrieve the best bid and ask prices for a specific trading pair. Only `BTCUSDT` and `ETHUSDT` are supported.

**Request Parameters:**

| Parameter       | Type                    | Description              |
| --------------- | ----------------------- | ------------------------ |
| transactionPair | Enum (BTCUSDT, ETHUSDT) | Trading pair to retrieve |

**Response Example:**

```json
{
    "success": true,
    "message": "Latest price retrieved successfully",
    "data": {
        "bestBidPrice": 106810.01,
        "bestAskPrice": 106802.64,
        "askSource": "BINANCE",
        "bidSource": "HUOBI",
        "transactionPair": "BTCUSDT"
    }
}
```


**Database Schema**
Database Schema for all the tables 


# Database Schema

## 1. `user_info`

| Column      | Type          | Constraints                       |
|------------|---------------|----------------------------------|
| id         | BIGINT        | PK, AUTO_INCREMENT               |
| username   | VARCHAR(50)   | NOT NULL, UNIQUE                 |
| created_at | TIMESTAMP     | NOT NULL, default CURRENT_TIMESTAMP |

**Relationships:**

- One-to-Many with `wallet` (user_id)
- One-to-Many with `transactions` (user_id)

---

## 2. `wallet`

| Column     | Type          | Constraints                               |
|-----------|---------------|------------------------------------------|
| id        | BIGINT        | PK, AUTO_INCREMENT                        |
| user_id   | BIGINT        | FK -> `user_info.id`, NOT NULL           |
| currency  | VARCHAR(10)   | NOT NULL, ENUM (Currency)                 |
| balance   | DECIMAL(18,8) | NOT NULL                                  |
| updated_at| TIMESTAMP     | NOT NULL, default CURRENT_TIMESTAMP       |

**Constraints:**

- UNIQUE(user_id, currency)

**Relationships:**

- Many-to-One with `user_info` (user_id)

---

## 3. `transactions`

| Column          | Type          | Constraints                             |
|-----------------|---------------|----------------------------------------|
| id              | BIGINT        | PK, AUTO_INCREMENT                      |
| user_id         | BIGINT        | FK -> `user_info.id`, NOT NULL          |
| transaction_type| VARCHAR(4)    | NOT NULL, ENUM (TransactionType)        |
| transaction_pair| VARCHAR(10)   | NOT NULL, ENUM (TransactionPair)        |
| status          | VARCHAR(20)   | NOT NULL, ENUM (TransactionStatus)      |
| quantity        | DECIMAL(18,8) | NOT NULL                                |
| price           | DECIMAL(18,8) | NOT NULL                                |
| total_amount    | DECIMAL(18,8) | NOT NULL                                |
| created_at      | TIMESTAMP     | NOT NULL, default CURRENT_TIMESTAMP     |
| source          | VARCHAR(20)   | NOT NULL, ENUM (Source)                 |

**Indexes:**

- INDEX(user_id)
- INDEX(created_at)

**Relationships:**

- Many-to-One with `user_info` (user_id)

---

## 4. `price_aggregation`

| Column          | Type          | Constraints                             |
|-----------------|---------------|----------------------------------------|
| id              | BIGINT        | PK, AUTO_INCREMENT                      |
| transaction_pair| VARCHAR(10)   | ENUM (TransactionPair)                  |
| bid_price       | DECIMAL(18,8) | SELL price                               |
| ask_price       | DECIMAL(18,8) | BUY price                                |
| timestamp       | TIMESTAMP     | default CURRENT_TIMESTAMP               |
| ask_source      | VARCHAR(20)   | ENUM (Source)                           |
| bid_source      | VARCHAR(20)   | ENUM (Source)                           |

**Indexes:**

- INDEX(transaction_pair)
- INDEX(timestamp DESC)






