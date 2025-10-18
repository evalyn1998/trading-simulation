package org.example.tradingsimulation.dtos;

/*
* ApiResponse class to standardize API responses since we are getting different response formats from different endpoints.
* Why?
* Because different endpoints may return data in various structures, making it challenging to handle responses uniformly.
* By creating a standardized ApiResponse class, we can encapsulate the common elements of these responses,
* making it easier to process and manage the data consistently across our application.
* */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T> {
    private boolean success;
    private String message;
    private T data;


    //success response
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true,null, data);
    }
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    //failure response
    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(false, message, null);
    }
}

