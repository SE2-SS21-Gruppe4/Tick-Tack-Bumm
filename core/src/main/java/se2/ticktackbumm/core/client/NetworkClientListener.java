package se2.ticktackbumm.core.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.network.messages.ConnectionRejected;
import se2.ticktackbumm.core.network.messages.ConnectionSuccessful;
import se2.ticktackbumm.core.network.messages.SomeResponse;
import se2.ticktackbumm.core.screens.MenuScreen;

/**
 * Listener for the TickTackBumm game client. Reacts to events on the client port.
 */
public class NetworkClientListener extends Listener {
    private final ClientMessageHandler clientMessageHandler;

    public NetworkClientListener(ClientMessageHandler clientMessageHandler) {
        this.clientMessageHandler = clientMessageHandler;
    }

    @Override
    public void disconnected(Connection connection) {
        Gdx.app.postRunnable(() -> TickTackBummGame.getTickTackBummGame().setScreen(new MenuScreen()));
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
        } else if (object instanceof ConnectionSuccessful) {
            clientMessageHandler.handleConnectionSuccessful();
        } else if (object instanceof ConnectionRejected) {
            Log.error("Player connection was rejected by server");
        }
    }
}
