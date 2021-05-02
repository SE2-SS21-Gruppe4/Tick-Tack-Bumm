package at.aau.se2.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

import at.aau.se2.network.NetworkConstants;
import at.aau.se2.network.messages.Messages;

public class ServerMain {
    private final Server server;
    private final ServerData serverData;

    public ServerMain() {
        Log.info("Starting server on port " + NetworkConstants.TCP_PORT);

        this.server = new Server();
        this.serverData = new ServerData();

        Kryo kryo = server.getKryo();
        kryo.register(Messages.SomeRequest.class);
        kryo.register(Messages.SomeResponse.class);

        server.addListener(new ServerListener());

        Log.info("Server started on port " + NetworkConstants.TCP_PORT);
    }

    public void startServer() throws IOException {
        server.start();
        server.bind(NetworkConstants.TCP_PORT);
    }
}