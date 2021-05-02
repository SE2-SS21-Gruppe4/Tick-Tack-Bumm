package at.aau.se2.server;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

import at.aau.se2.network.KryoRegisterer;
import at.aau.se2.network.NetworkConstants;

public class NetworkServer {
    private final Server server;

    public NetworkServer() {
        Log.info("Starting server on port " + NetworkConstants.TCP_PORT);

        this.server = new Server();
        KryoRegisterer.registerMessages(this.server.getKryo());

        server.addListener(new NetworkServerListener());

        Log.info("Server started on port " + NetworkConstants.TCP_PORT);
    }

    public void startServer() throws IOException {
        server.start();
        server.bind(NetworkConstants.TCP_PORT);
    }
}