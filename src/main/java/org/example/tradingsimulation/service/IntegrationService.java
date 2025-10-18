package org.example.tradingsimulation.service;


import lombok.extern.slf4j.Slf4j;
import org.example.tradingsimulation.enums.Source;
import org.example.tradingsimulation.enums.TransactionPair;
import org.example.tradingsimulation.dtos.Binance.BinanceResponse;
import org.example.tradingsimulation.dtos.Houbi.HuobiResponse;
import org.example.tradingsimulation.dtos.PriceData;
import org.example.tradingsimulation.models.PriceAggregation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class IntegrationService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.binance.url}")
    private String binanceApiUrl;

    @Value("${api.huobi.url}")
    private String huobiApiUrl;


    private TransactionPair mapBinanceSymbol(String symbol) {
        return switch (symbol) {
            case "ETHUSDT" -> TransactionPair.ETHUSDT;
            case "BTCUSDT" -> TransactionPair.BTCUSDT;
            default -> null;
        };
    }

    private TransactionPair mapHuobiSymbol(String symbol) {
        return switch (symbol) {
            case "ethusdt" -> TransactionPair.ETHUSDT;
            case "btcusdt" -> TransactionPair.BTCUSDT;
            default -> null;
        };
    }

    public void fetchBinancePrices(Map<TransactionPair, List<PriceData>> priceDataMap) {
        try {
            BinanceResponse[] binanceResponse = restTemplate.getForObject(binanceApiUrl, BinanceResponse[].class);

            if (binanceResponse != null) {
                log.info("Fetched {} records from Binance API", binanceResponse.length);
                for (BinanceResponse response : binanceResponse) {
                    TransactionPair pair = mapBinanceSymbol(response.getSymbol());
                    if (pair != null) {
                        PriceData priceData = new PriceData(
                                new BigDecimal(response.getBidPrice()),
                                new BigDecimal(response.getAskPrice()), Source.BINANCE);
                        priceDataMap.computeIfAbsent(pair, k -> Collections.singletonList(priceData));
                        log.info("Binance {}: Bid={}, Ask={}", pair, priceData.getBidPrice(), priceData.getAskPrice());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error fetching data from Binance API: {}", e.getMessage());
        }
    }


    private void fetchHuobiPrices(Map<TransactionPair, List<PriceData>> priceDataMap) {
        try {
            HuobiResponse response = restTemplate.getForObject(huobiApiUrl, HuobiResponse.class);
            if (response != null && response.getData() != null && Objects.equals(response.getStatus(), "ok")) {
                log.info("Fetched {} records from HuobiAPI", response.getData().size());
                for (HuobiResponse.Huobidata ticker : response.getData()) {
                    TransactionPair pair = mapHuobiSymbol(ticker.getSymbol());
                    if (pair != null && ticker.getBid() != null && ticker.getAsk() != null) {
                        PriceData priceData = new PriceData(ticker.getBid(), ticker.getAsk(), Source.HUOBI);
                        System.out.println("Huobi " +priceData );
                        priceDataMap.computeIfAbsent(pair, k -> Collections.singletonList(priceData));
                        log.info("Huobi {}: Bid={}, Ask={}", pair, priceData.getBidPrice(), priceData.getAskPrice());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error fetching Huobi prices: {}", e.getMessage());
        }
    }

    public PriceAggregation aggregatePrices() {
        log.info("Starting price aggregation...");

        try {
            // Fetch prices from both exchanges
            Map<TransactionPair, List<PriceData>> pricesMap = new HashMap<>();

            // Fetch from Binance
            fetchBinancePrices(pricesMap);

            // Fetch from Huobi
            fetchHuobiPrices(pricesMap);

            // Aggregate and save best prices
            for (Map.Entry<TransactionPair, List<PriceData>> entry : pricesMap.entrySet()) {
                TransactionPair tradingPair = entry.getKey();
                List<PriceData> prices = entry.getValue();

                if (prices.isEmpty()) {
                    log.warn("No prices found for {}", tradingPair);
                    continue;
                }

                // Find best bid (highest) and best ask (lowest)
                PriceData bestBidData = prices.stream()
                        .max(Comparator.comparing(PriceData::getBidPrice))
                        .orElse(null);

                PriceData bestAskData = prices.stream()
                        .min(Comparator.comparing(PriceData::getAskPrice))
                        .orElse(null);

                PriceAggregation priceAgg = new PriceAggregation();
                priceAgg.setTransactionPair(tradingPair);
                priceAgg.setBidPrice(bestBidData.getBidPrice());
                priceAgg.setAskPrice(bestAskData.getAskPrice());
                priceAgg.setTimestamp(LocalDateTime.now());
                priceAgg.setAskSource(bestAskData.getSource());
                priceAgg.setBidSource(bestBidData.getSource());

                log.info("Saved best price for {}: Bid={}, Ask={}", tradingPair, priceAgg.getBidPrice(), priceAgg.getAskPrice());
                return priceAgg;


            }

        } catch (Exception e) {
            log.error("Error during price aggregation: {}", e.getMessage(), e);
        }
        return null;
    }

}

