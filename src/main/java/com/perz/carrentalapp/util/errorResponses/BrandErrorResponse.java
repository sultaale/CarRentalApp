package com.perz.carrentalapp.util.errorResponses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandErrorResponse {
    private String message;
    private long timestamp;

    public BrandErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
