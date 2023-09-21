package com.perz.carrentalapp.util.errorResponses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleErrorResponse {
    private String message;
    private long timestamp;

    public RoleErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
