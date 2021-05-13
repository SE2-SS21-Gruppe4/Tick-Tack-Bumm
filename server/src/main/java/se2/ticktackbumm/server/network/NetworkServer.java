package se2.ticktackbumm.server.network;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.network.KryoRegisterer;
import se2.ticktackbumm.core.network.NetworkConstants;
import se2.ticktackbumm.server.data.ServerData;

import java.io.IOException;

/**
 * The central network server for the TickTackBumm game.
 * Uses a Kryonet-{@link Server} to handle connecting clients, game state and incoming messages.
 */
public class NetworkServer {
    /**
     * Kryonet-Server instance.
     */
    private final Server server;

    private final ServerData serverData;

    /**
     * Class constructor.
     * Create the Kryonet-{@link Server}, register all Kryo message classes and adds a
     * {@link NetworkServerListener} to handle messages.
     */
    public NetworkServer() {
        this.server = new Server();
        KryoRegisterer.registerMessages(this.server.getKryo());
        server.addListener(new NetworkServerListener());

        this.serverData = new ServerData(server);
    }

    /**
     * Starts the Kryonet-{@link Server} in a new thread and binds him to the TCP-Port specified
     * in {@link NetworkConstants}.
     *
     * @throws IOException if the server could not be opened.
     */
    public void startServer() throws IOException {
        Log.info("Starting server on port " + NetworkConstants.TCP_PORT);

        server.start();
        server.bind(NetworkConstants.TCP_PORT);

        Log.info("Server started on port " + NetworkConstants.TCP_PORT);
    }
}
