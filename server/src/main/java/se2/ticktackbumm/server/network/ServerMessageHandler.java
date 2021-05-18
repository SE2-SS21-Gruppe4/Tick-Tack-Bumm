package se2.ticktackbumm.server.network;

import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.network.messages.PlayerTaskCompleted;
import se2.ticktackbumm.core.network.messages.SomeRequest;
import se2.ticktackbumm.server.data.ServerData;

/**
 * Handles all incoming client messages for the server.
 */
public class ServerMessageHandler {

    private final String LOG_TAG = "SERVER_MESSAGE_HANDLER";

    /**
     * Server instance to handle messages for.
     */
    private final NetworkServer networkServer;
    private final ServerMessageSender serverMessageSender;
    private final ServerData serverData;

    /**
     * Class constructor.
     * Creates a message handler, that handles all incoming client messages for the server.
     *
     * @param networkServer       the server to handle messages for
     * @param serverMessageSender message sender from server to clients
     */
    public ServerMessageHandler(NetworkServer networkServer, ServerMessageSender serverMessageSender) {
        this.networkServer = networkServer;
        this.serverMessageSender = serverMessageSender;
        this.serverData = networkServer.getServerData();
    }

    // Test method
    public void handleSomeRequest(SomeRequest someRequest) {
        Log.info(LOG_TAG, "Server response to " + someRequest.getClass()
                + ": " + someRequest.getText());

        serverMessageSender.sendSomeResponse("Hello from the server.");
    }

    public void handlePlayerTaskCompleted(PlayerTaskCompleted playerTaskCompleted) {
        Log.info(LOG_TAG,
                "Server got message (" + playerTaskCompleted.getClass() +
                        ") from player with ID " + playerTaskCompleted.getPlayerIndex());

        // TODO: setup player data on game start
        // serverData.incPlayerScoreByConnectionId(playerTaskCompleted.getPlayerIndex());

        // TODO: update client state
        // serverMessageSender.sendPlayerUpdate();
    }
}
