package se2.ticktackbumm.server.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.network.messages.ConnectionRejected;
import se2.ticktackbumm.core.network.messages.ConnectionSuccessful;
import se2.ticktackbumm.core.network.messages.SomeRequest;
import se2.ticktackbumm.core.network.messages.SomeResponse;

/**
 * Listener for the TickTackBumm game server. Reacts to events on the server port.
 */
public class NetworkServerListener extends Listener {

    private final String LOG_TAG = "NETWORK_SERVER_LISTENER";

    private final NetworkServer networkServer;

    public NetworkServerListener(NetworkServer networkServer) {
        this.networkServer = networkServer;
    }

    @Override
    public void connected(Connection connection) {
        Log.info(LOG_TAG, "Player trying to connect: " + connection);

        if (networkServer.getServerData().connectPlayer(connection.getID())) {
            Log.info(LOG_TAG, "Player successfully connected: " + connection);

            connection.sendTCP(new ConnectionSuccessful());
        } else {
            Log.error(LOG_TAG, "Player connection failed: " + connection);

            connection.sendTCP(new ConnectionRejected());
            connection.close();
        }
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
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
            SomeRequest request = (SomeRequest) object;
            System.out.println(request.text);

            SomeResponse response = new SomeResponse();
            response.text = "Thanks";
            connection.sendTCP(response);
        }
    }
}
