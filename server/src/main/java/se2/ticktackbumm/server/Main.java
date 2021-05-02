package se2.ticktackbumm.server;

import java.io.IOException;

import se2.ticktackbumm.core.network.NetworkConstants;

/**
 * Simple wrapper around {@link NetworkServer} to launch a {@link NetworkServer} instance on the
 * port specified in {@link NetworkConstants}.
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
