package se2.ticktackbumm.server;

import java.io.IOException;

import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.network.NetworkConstants;
import se2.ticktackbumm.server.network.NetworkServer;

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
            Log.error("Error starting game server instance: " + e.getMessage());
        }
    }
}
