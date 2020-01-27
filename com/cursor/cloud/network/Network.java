package com.cursor.cloud.network;

import com.cursor.cloud.client.Client;
import com.cursor.cloud.data.HttpRequest;
import com.cursor.cloud.data.HttpResponse;
import com.cursor.cloud.server.TomcatServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.cursor.util.ConsoleColor.MAGENTA;
import static com.cursor.util.ConsoleColor.RESET;

public final class Network implements CloudNetwork, ServerNetwork, ClientNetwork {

    private static Network network;
    private List<TomcatServer> servers = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private Network() {

    }

    public static Network establishNetwork() {
        if (network == null) {
            network = new Network();
        }
        return network;
    }

    public void establishConnectionWithTomcatServer(final TomcatServer server) {
        this.servers.add(server);
    }

    public void establishConnectionWithClient(final Client client) {
        this.clients.add(client);
    }

    public void sendRequestToServer(final HttpRequest request) {

        final TomcatServer serverToProcessRequest = getRandomNetworkTomcatServer();
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(10000, 25000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format(MAGENTA +
                        "redirecting client request to SERVER {serverIp: %s, clientIp: %s, time in seconds to process" +
                        " request by network: %s\"}", serverToProcessRequest.getServerIpAddress(),
                request.getClientIpAddress(),
                TimeUnit.MILLISECONDS.toSeconds(end - start)) + RESET);
        serverToProcessRequest.processHttpRequest(request);
    }

    public void sendResponseToClient(final HttpResponse response) {
        final Client clientToAcceptResponse = getRequestSender(response);
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(10000, 25000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        // выводим всю инфу в консоль
        System.out.println(MAGENTA + String.format(
                "redirecting server response to CLIENT {serverIp: %s, clientIp: %s, time in seconds to process " +
                        "response by network: %s\"}", response.getServerIpAddress(), response.getClientIpAddress(),
                TimeUnit.MILLISECONDS.toSeconds(end - start)) + RESET);
        clientToAcceptResponse.acceptServerResponse(response);
    }

    private TomcatServer getRandomNetworkTomcatServer() {
        return servers.get(new Random().nextInt(servers.size()));
    }

    private Client getRequestSender(HttpResponse response) {
        return clients.stream().filter(client -> client.getIpAddress().equals(response.getClientIpAddress())).findAny()
                      .orElseThrow(() -> new IllegalStateException("No connected client associated with request"));
    }
}
