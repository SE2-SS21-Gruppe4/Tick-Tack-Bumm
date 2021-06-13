package se2.ticktackbumm.core.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.Avatars;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.data.GameMode;
import se2.ticktackbumm.core.network.messages.client.*;

/**
 * Handles the sending of messages from the client to the server.
 */
public class ClientMessageSender {

    private final String LOG_TAG = "SERVER_CLIENT_SENDER";

    /**
     * Client instance to handle sending for.
     */
    private final Client client;

    /**
     * Class constructor.
     * Creates a message sender, that handles the sending of client messages to the server.
     *
     * @param client the client to handle message sending for
     */
    public ClientMessageSender(Client client) {
        this.client = client;
    }

    private void logSendingMessage(String messageType) {
        Log.info(LOG_TAG, "Sending message " + messageType + " from player: " +
                TickTackBummGame.getTickTackBummGame().getLocalPlayer().getPlayerName());
    }

    // Test method
    public void sendSomeRequest(String text) {
        client.sendTCP(new SomeRequest(text));
    }

    public void sendPlayerTaskCompleted(String userInput) {
        logSendingMessage("TaskCompleted");
        client.sendTCP(new PlayerTaskCompleted(userInput));
    }

    public void sendBombExploded() {
        logSendingMessage("BombExploded");
        client.sendTCP(new BombExploded());
    }

    public void sendPlayerReady(String playerName, Avatars playerAvatar) {
        logSendingMessage("PlayerReady");
        client.sendTCP(new PlayerReady(playerName, playerAvatar));
    }

    public void sendStartBomb(){
        logSendingMessage("BombStart");
        client.sendTCP(new BombStart());
    }

    public void spinWheelFinished(GameMode gameMode) {
        logSendingMessage("SpinWheelFinished");
        client.sendTCP(new SpinWheelFinished(gameMode));
    }

    public void sendCardOpened(String word){
        logSendingMessage("Card opened");
        client.sendTCP(new CardOpened(word));
    }
}
