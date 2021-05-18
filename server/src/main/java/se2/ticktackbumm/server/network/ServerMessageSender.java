package se2.ticktackbumm.server.network;

import com.esotericsoftware.kryonet.Server;
import se2.ticktackbumm.core.network.messages.SomeResponse;

/**
 * Handles the sending of messages from the server to the clients.
 */
public class ServerMessageSender {

    private final String LOG_TAG = "SERVER_MESSAGE_SENDER";

    /**
     * Server instance to handle sending for.
     */
    private final Server server;

    /**
     * Class constructor.
     * Creates a message sender, that handles the sending of server messages to the clients.
     *
     * @param server the server to handle message sending for
     */
    public ServerMessageSender(Server server) {
        this.server = server;
    }

    // Test method
    public void sendSomeResponse(String text) {
        server.sendToAllTCP(new SomeResponse(text));
    }

    public void sendPlayerUpdate() {
        // TODO: update all clients with the new player turn and new player score
    }
}