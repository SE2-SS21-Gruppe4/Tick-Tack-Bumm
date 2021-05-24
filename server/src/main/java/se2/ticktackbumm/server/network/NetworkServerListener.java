package se2.ticktackbumm.server.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.network.messages.client.BombExploded;
import se2.ticktackbumm.core.network.messages.client.PlayerReady;
import se2.ticktackbumm.core.network.messages.client.PlayerTaskCompleted;
import se2.ticktackbumm.core.network.messages.client.SomeRequest;
import se2.ticktackbumm.core.network.messages.server.ConnectionRejected;
import se2.ticktackbumm.core.network.messages.server.ConnectionSuccessful;
import se2.ticktackbumm.core.player.Player;

/**
 * Listener for the TickTackBumm game server. Reacts to events on the server port.
 */
public class NetworkServerListener extends Listener {

    private final String LOG_TAG = "NETWORK_SERVER_LISTENER";

    private final NetworkServer networkServer;
    private final ServerMessageHandler serverMessageHandler;

    public NetworkServerListener(NetworkServer networkServer, ServerMessageHandler serverMessageHandler) {
        this.networkServer = networkServer;
        this.serverMessageHandler = serverMessageHandler;
    }

    @Override
    public void connected(Connection connection) {
        Log.info(LOG_TAG, "Player trying to connect: " + connection);

        Player connectedPlayer;
        if ((connectedPlayer = networkServer.getServerData().connectPlayer(connection.getID())) != null) {
            Log.info(LOG_TAG, "Player successfully connected: " + connection);

            connection.sendTCP(new ConnectionSuccessful(connectedPlayer));
        } else {
            Log.error(LOG_TAG, "Player connection failed: " + connection);

            connection.sendTCP(new ConnectionRejected());
            connection.close();
        }
    }

    @Override
    public void disconnected(Connection connection) {
        Log.info(LOG_TAG, "Disconnected player from server: " + connection.getID());

        networkServer.getServerData().disconnectPlayer(connection.getID());
    }

    /**
     * Handle all incoming messages from the game clients to the game server.
     *
     * @param connection the incoming client connection
     * @param object     the incoming message
     */
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof SomeRequest) {
            Log.info(LOG_TAG, "Disconnected player from server: " + connection.getID());
            serverMessageHandler.handleSomeRequest((SomeRequest) object);

        } else if (object instanceof PlayerTaskCompleted) {
            Log.info(LOG_TAG, "Received message PlayerTaskCompleted from ID: " + connection.getID());
            serverMessageHandler.handlePlayerTaskCompleted();

        } else if (object instanceof BombExploded) {
            Log.info(LOG_TAG, "Received message BombExploded from ID: " + connection.getID());
            serverMessageHandler.handleBombExploded(connection.getID());

        } else if (object instanceof PlayerReady) {
            Log.info(LOG_TAG, "Received message PlayerReady from ID: " + connection.getID());
            serverMessageHandler.handlePlayerReady((PlayerReady) object, connection.getID());

        }
    }
}
