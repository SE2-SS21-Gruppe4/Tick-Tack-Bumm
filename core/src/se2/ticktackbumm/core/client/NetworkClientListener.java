package se2.ticktackbumm.core.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import se2.ticktackbumm.core.network.messages.Messages;

public class NetworkClientListener extends Listener {
    private final MessageHandler messageHandler;

    public NetworkClientListener(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof se2.ticktackbumm.core.network.messages.Messages.SomeResponse) {
            messageHandler.handleSomeResponse((Messages.SomeResponse) object);
        }
    }
}
