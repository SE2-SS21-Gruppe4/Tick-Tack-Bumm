package se2.ticktackbumm.core.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.network.messages.ConnectionRejected;
import se2.ticktackbumm.core.network.messages.ConnectionSuccessful;
import se2.ticktackbumm.core.network.messages.SomeResponse;
import se2.ticktackbumm.core.network.messages.StartGame;
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
            Log.info(LOG_TAG, "Received message SomeResponse from server");
            clientMessageHandler.handleSomeResponse((SomeResponse) object);

        } else if (object instanceof ConnectionSuccessful) {
            Log.info(LOG_TAG, "Received message ConnectionSuccessful from server");
            clientMessageHandler.handleConnectionSuccessful((ConnectionSuccessful) object);

        } else if (object instanceof ConnectionRejected) {
            Log.info(LOG_TAG, "Received message ConnectionRejected from server");
            Log.error(LOG_TAG, "Player connection was rejected by server");

        } else if (object instanceof StartGame) {
            Log.info(LOG_TAG, "Received message StartGame from server");
            clientMessageHandler.handleStartGame((StartGame) object);
        }
    }
}
