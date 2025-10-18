package org.example.tradingsimulation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * A DTO representing an error response.
 * Why?
 * This class is intended to encapsulate error information that can be sent back to clients
 * in case of failures or exceptions during API calls.
 * Fields typically included are:
 * - errorCode: A specific code representing the type of error.
 * - errorMessage: A human-readable message describing the error.
 * - timestamp: The time when the error occurred.
 * This standardization helps in consistent error handling and reporting across the application.
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private LocalDateTime timestamp;

public ErrorResponse(String errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.timestamp = LocalDateTime.now();
}

}
