package se2.ticktackbumm.core.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.network.messages.server.*;
import se2.ticktackbumm.core.screens.MenuScreen;

/**
 * Listener for the TickTackBumm game client. Reacts to events on the client port.
 */
public class NetworkClientListener extends Listener {

    private final String LOG_TAG = "NETWORK_CLIENT_LISTENER";

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
            logReceivedMessage("SomeResponse");
            clientMessageHandler.handleSomeResponse((SomeResponse) object);

        } else if (object instanceof ConnectionSuccessful) {
            logReceivedMessage("ConnectionSuccessful");
            clientMessageHandler.handleConnectionSuccessful((ConnectionSuccessful) object);

        } else if (object instanceof ConnectionRejected) {
            logReceivedMessage("ConnectionRejected");
            Log.error(LOG_TAG, "Player connection was rejected by server");

        } else if (object instanceof StartGame) {
            logReceivedMessage("StartGame");
            clientMessageHandler.handleStartGame((StartGame) object);

        } else if (object instanceof PlayersUpdate) {
            logReceivedMessage("PlayersUpdate");
            clientMessageHandler.handlePlayersUpdate((PlayersUpdate) object);

        }
    }

    private void logReceivedMessage(String messageType) {
        Log.info(LOG_TAG, "Received message " + messageType + " from server");
    }
}
