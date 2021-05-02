package at.aau.se2.server;

import java.io.IOException;

/**
 * Simple wrapper around {@link NetworkServer} to launch a {@link NetworkServer} instance on the
 * port specified in {@link at.aau.se2.network.NetworkConstants}.
 */
public class Main {
    public static void main(String[] args) {
        NetworkServer server = new NetworkServer();
        try {
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
