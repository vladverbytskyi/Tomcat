package com.cursor.cloud.data;

public class HttpResponse {

    private final String clientIpAddress;
    private final String serverIpAddress;

    public HttpResponse(String clientIpAddress, String serverIpAddress) {
        this.clientIpAddress = clientIpAddress;
        this.serverIpAddress = serverIpAddress;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }
}
