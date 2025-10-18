package org.example.tradingsimulation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class TradingSimulationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradingSimulationApplication.class, args);
    }
    @Bean
    CommandLineRunner checkDataSource(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("Database URL: " + conn.getMetaData().getURL());
            }
        };
    }
}
