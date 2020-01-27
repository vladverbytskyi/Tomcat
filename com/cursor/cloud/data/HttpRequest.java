package com.cursor.cloud.data;

public class HttpRequest {

    private final String clientIpAddress;

    public HttpRequest(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }
}
