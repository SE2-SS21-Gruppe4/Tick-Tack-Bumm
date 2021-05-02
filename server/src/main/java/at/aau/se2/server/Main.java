package at.aau.se2.server;

import java.io.IOException;

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
