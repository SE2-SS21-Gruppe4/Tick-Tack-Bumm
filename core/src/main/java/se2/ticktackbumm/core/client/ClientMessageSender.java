package se2.ticktackbumm.core.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.network.messages.BombExploded;
import se2.ticktackbumm.core.network.messages.PlayerReady;
import se2.ticktackbumm.core.network.messages.PlayerTaskCompleted;
import se2.ticktackbumm.core.network.messages.SomeRequest;

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

    // Test method
    public void sendSomeRequest(String text) {
        client.sendTCP(new SomeRequest(text));
    }

    public void sendPlayerTaskCompleted() {
        Log.info(LOG_TAG, "Sending message TaskCompleted from player " + TickTackBummGame.getTickTackBummGame().getLocalPlayer().getPlayerId());
        client.sendTCP(new PlayerTaskCompleted());
    }

    public void sendBombExploded() {
        Log.info(LOG_TAG, "Sending message BombExploded from player " + TickTackBummGame.getTickTackBummGame().getLocalPlayer().getPlayerId());
        client.sendTCP(new BombExploded());
    }

    public void sendPlayerReady() {
        Log.info(LOG_TAG, "Sending message PlayerReady from player " + TickTackBummGame.getTickTackBummGame().getLocalPlayer().getPlayerId());
        client.sendTCP(new PlayerReady());
    }
}
