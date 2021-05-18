package se2.ticktackbumm.core.client;

import com.esotericsoftware.kryonet.Client;
import se2.ticktackbumm.core.network.messages.BombExploded;
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
        client.sendTCP(new PlayerTaskCompleted());
    }

    public void sendBombExploded() {
        client.sendTCP(new BombExploded());
    }
}
