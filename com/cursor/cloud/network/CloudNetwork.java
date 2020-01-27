package com.cursor.cloud.network;

import com.cursor.cloud.client.Client;
import com.cursor.cloud.server.TomcatServer;

public interface CloudNetwork {

    void establishConnectionWithTomcatServer(final TomcatServer server);

    void establishConnectionWithClient(final Client client);
}
