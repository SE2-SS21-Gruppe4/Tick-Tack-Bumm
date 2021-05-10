package se2.ticktackbumm.core.client;

import com.esotericsoftware.kryonet.Client;

import se2.ticktackbumm.core.network.messages.SomeRequest;

/**
 * Handles the sending of messages from the client to the server.
 */
public class ClientMessageSender {
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
        SomeRequest someRequest = new SomeRequest();
        someRequest.text = text;
        client.sendTCP(someRequest);
    }
}
