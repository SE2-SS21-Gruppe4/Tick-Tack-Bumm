package se2.ticktackbumm.core.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import se2.ticktackbumm.core.network.messages.SomeResponse;

/**
 * Handles all incoming server messages for a client.
 */
public class ClientMessageHandler {
    /**
     * Client instance to handle messages for.
     */
    private final Client client;

    /**
     * Class constructor.
     * Creates a message handler, that handles all incoming server messages for a client.
     *
     * @param client the client to handle messages for
     */
    public ClientMessageHandler(Client client) {
        this.client = client;
    }

    // Test method
    public void handleSomeResponse(SomeResponse someResponse) {
        Log.info("MessageHandler", "Server response to " + someResponse.getClass()
                + ": " + someResponse.text);
    }
}
