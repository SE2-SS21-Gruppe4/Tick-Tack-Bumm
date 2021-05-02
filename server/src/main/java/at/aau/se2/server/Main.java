package at.aau.se2.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ServerMain server = new ServerMain();
        try {
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
