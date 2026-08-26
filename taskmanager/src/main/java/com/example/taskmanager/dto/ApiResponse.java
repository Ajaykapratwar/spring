package com.example.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Generic envelope used for API responses.
 *
 * @param <T> type of the response payload
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        /** HTTP-style status code for the operation. */
        int statusCode,
        /** Human-readable result message. */
        String message,
        /** Optional operation payload. */
        T data
) {
}
