package com.cursor.cloud.network;

import com.cursor.cloud.data.HttpResponse;

public interface ServerNetwork {

    void sendResponseToClient(final HttpResponse response);
}
