package se2.ticktackbumm.core.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.network.messages.client.CardOpened;
import se2.ticktackbumm.core.network.messages.client.SpinWheelFinished;
import se2.ticktackbumm.core.network.messages.client.SpinWheelStarted;
import se2.ticktackbumm.core.network.messages.client.StartBomb;
import se2.ticktackbumm.core.network.messages.server.*;
import se2.ticktackbumm.core.screens.MenuScreen;

/**
 * Listener for the TickTackBumm game client. Reacts to events on the client port.
 */
public class NetworkClientListener extends Listener {

    /**
     * The log tag is used to provide unique logging for the class.
     */
    private final String LOG_TAG = "NETWORK_CLIENT_LISTENER";

    /**
     * The game's message sender, later contained in the singleton instance of the game class. Provides
     * functionality to send messages from client to server.
     */
    private final ClientMessageHandler clientMessageHandler;

    /**
     * Constructs a new listener for the Kryonet-{@link Client}. Receives a {@link ClientMessageHandler}
     * instance to handle incoming server messages.
     *
     * @param clientMessageHandler the handler for the incoming messages
     */
    public NetworkClientListener(ClientMessageHandler clientMessageHandler) {
        this.clientMessageHandler = clientMessageHandler;
    }

    /**
     * Handle a disconnect of the client from the server. Sets client back to {@link MenuScreen}.
     *
     * @param connection the connection that was closed
     */
    @Override
    public void disconnected(Connection connection) {
        Gdx.app.postRunnable(() -> TickTackBummGame.getTickTackBummGame().setScreen(new MenuScreen()));
    }

    /**
     * Receive all incoming messages from the game server to the game client and pass them to the
     * {@link ClientMessageHandler}.
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

        } else if (object instanceof GameUpdate) {
            logReceivedMessage("GameUpdate");
            clientMessageHandler.handleGameUpdate((GameUpdate) object);

        } else if (object instanceof StartGame) {
            logReceivedMessage("StartGame");
            clientMessageHandler.handleStartGame();

        } else if (object instanceof NextTurn) {
            logReceivedMessage("NextTurn");
            clientMessageHandler.handleNextTurn();

        } else if (object instanceof NextRound) {
            logReceivedMessage("NextRound");
            clientMessageHandler.handleNextRound();

        } else if (object instanceof GameFinished) {
            logReceivedMessage("GameFinished");
            clientMessageHandler.handleGameFinished((GameFinished) object);

        } else if (object instanceof StartBomb) {
            logReceivedMessage("StartBomb");
            clientMessageHandler.handleStartBomb((StartBomb) object);

        } else if (object instanceof SpinWheelFinished) {
            logReceivedMessage("SpinWheelFinished");
            clientMessageHandler.handleSpinWheelFinished();

        } else if (object instanceof SpinWheelStarted) {
            logReceivedMessage("SpinWheelStarted");
            clientMessageHandler.handleSpinWheelStarted();

        } else if (object instanceof CardOpened) {
            logReceivedMessage("CardOpened");
            clientMessageHandler.handleCardOpened();

        }
    }

    /**
     * Helper method to allow easy logging received messages.
     *
     * @param messageType message type to log
     */
    private void logReceivedMessage(String messageType) {
        Log.info(LOG_TAG, "Received message " + messageType + " from server");
    }
}
