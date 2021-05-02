package se2.ticktackbumm.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import se2.ticktackbumm.core.network.messages.SomeRequest;
import se2.ticktackbumm.core.network.messages.SomeResponse;

/**
 * Listener for the TickTackBumm game server. Reacts to events on the server port.
 */
public class NetworkServerListener extends Listener {
    @Override
    public void connected(Connection connection) {
        super.connected(connection);
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