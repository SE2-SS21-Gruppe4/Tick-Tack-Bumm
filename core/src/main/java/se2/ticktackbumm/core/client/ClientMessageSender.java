package se2.ticktackbumm.core.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.Avatars;
import se2.ticktackbumm.core.data.GameMode;
import se2.ticktackbumm.core.network.messages.client.*;

/**
 * Handles the sending of messages from the client to the server.
 */
public class ClientMessageSender {

    /**
     * The log tag is used to provide unique logging for the class.
     */
    private final String LOG_TAG = "SERVER_CLIENT_SENDER";

    /**
     * Client instance to handle sending for.
     */
    private final Client kryoClient;

    /**
     * Class constructor.
     * Creates a message sender, that handles the sending of client messages to the server.
     *
     * @param kryoClient the client to handle message sending for
     */
    public ClientMessageSender(Client kryoClient) {
        this.kryoClient = kryoClient;
    }

    /**
     * Helper method to allow easy logging of messages to send.
     *
     * @param messageType message type to log
     */
    private void logSendingMessage(String messageType) {
        Log.info(LOG_TAG, "Sending message " + messageType + " from player: " +
                TickTackBummGame.getTickTackBummGame().getLocalPlayer().getPlayerName());
    }

    /**
     * Method and message to test client server connection.
     * Sends a simple message containing a text to the server.
     *
     * @param text the outgoing message text
     */
    public void sendSomeRequest(String text) {
        kryoClient.sendTCP(new SomeRequest(text));
    }

    /**
     * Sends a {@link PlayerTaskCompleted} message to the server.
     *
     * @param userInput the user input to send to the server
     */
    public void sendPlayerTaskCompleted(String userInput) {
        logSendingMessage("TaskCompleted");
        kryoClient.sendTCP(new PlayerTaskCompleted(userInput));
    }

    /**
     * Sends a {@link BombExploded} message to the server.
     */
    public void sendBombExploded() {
        logSendingMessage("BombExploded");
        kryoClient.sendTCP(new BombExploded());
    }

    /**
     * Sends a {@link PlayerReady} message to the server with the chosen player name and avatar.
     *
     * @param playerName   the player's name to send to the server
     * @param playerAvatar the player's avatar to send to the server
     */
    public void sendPlayerReady(String playerName, Avatars playerAvatar) {
        logSendingMessage("PlayerReady");
        kryoClient.sendTCP(new PlayerReady(playerName, playerAvatar));
    }

    /**
     * Sends a {@link BombStart} message to the server.
     */
    public void sendStartBomb() {
        logSendingMessage("BombStart");
        kryoClient.sendTCP(new BombStart());
    }

    /**
     * Sends a {@link SpinWheelFinished} message to the server with the randomly chose game mode
     * from the spin wheel.
     *
     * @param gameMode the game mode randomly chosen in spin wheel
     */
    public void spinWheelFinished(GameMode gameMode) {
        logSendingMessage("SpinWheelFinished");
        kryoClient.sendTCP(new SpinWheelFinished(gameMode));
    }

    public void spinWheelStarted(GameMode currentGameMode) {
        logSendingMessage("SpinWheelStarted");
        kryoClient.sendTCP(new SpinWheelStarted(currentGameMode));
    }

    public void sendCardOpened(String word) {
        logSendingMessage("Card opened");
        kryoClient.sendTCP(new CardOpened(word));
    }
}
