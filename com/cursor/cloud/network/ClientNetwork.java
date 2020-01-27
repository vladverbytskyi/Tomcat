package com.cursor.cloud.network;

import com.cursor.cloud.data.HttpRequest;

public interface ClientNetwork {

    void sendRequestToServer(final HttpRequest request);
}
