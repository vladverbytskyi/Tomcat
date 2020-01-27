package com.cursor.cloud.server;

import com.cursor.cloud.data.HttpRequest;
import com.cursor.cloud.data.HttpResponse;
import com.cursor.cloud.network.ServerNetwork;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.cursor.cloud.network.Network.establishNetwork;
import static com.cursor.util.ConsoleColor.CYAN;
import static com.cursor.util.ConsoleColor.RESET;

public class TomcatServer {

    private static final ServerNetwork NETWORK = establishNetwork();

    private final String serverIpAddress;

    public TomcatServer(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }

    public void processHttpRequest(final HttpRequest request) {
        processRequestInSeparateThread(request);
    }

    private void processRequestInSeparateThread(final HttpRequest request) {
        new Thread(() -> processRequest(request)).start();
    }

    private void processRequest(HttpRequest request) {
        try {
            long start = System.currentTimeMillis();
            Thread.sleep(ThreadLocalRandom.current().nextLong(3000, 9000));
            long end = System.currentTimeMillis();
            System.out.println(String.format(
                    CYAN + "sending server RESPONSE {serverIp: %s, clientIp: %s}, time in seconds to process request" +
                            " by server: %s" + RESET, serverIpAddress, request.getClientIpAddress(),
                    TimeUnit.MILLISECONDS.toSeconds(end - start)));
            NETWORK.sendResponseToClient(new HttpResponse(request.getClientIpAddress(), serverIpAddress));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }
}
