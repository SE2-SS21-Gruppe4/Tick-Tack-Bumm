package se2.ticktackbumm.core.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import se2.ticktackbumm.core.network.messages.SomeResponse;

/**
 * Listener for the TickTackBumm game client. Reacts to events on the client port.
 */
public class NetworkClientListener extends Listener {
    private final ClientMessageHandler clientMessageHandler;

    public NetworkClientListener(ClientMessageHandler clientMessageHandler) {
        this.clientMessageHandler = clientMessageHandler;
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    /**
     * Handle all incoming messages from the game server to the game client.
     *
     * @param connection the incoming client connection
     * @param object     the incoming message
     */
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof SomeResponse) {
            clientMessageHandler.handleSomeResponse((SomeResponse) object);
        }
    }
}
