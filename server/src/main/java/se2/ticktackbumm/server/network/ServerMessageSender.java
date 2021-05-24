package se2.ticktackbumm.server.network;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.network.messages.server.PlayersUpdate;
import se2.ticktackbumm.core.network.messages.server.SomeResponse;
import se2.ticktackbumm.core.network.messages.server.StartGame;

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

    public void sendPlayersUpdate() {
        Log.info(LOG_TAG, "Sending message PlayersUpdate to all clients");
        server.sendToAllTCP(
                new PlayersUpdate(NetworkServer.getNetworkServer().getServerData().getGameData().getPlayers())
        );
    }

    public void sendStartGame() {
        Log.info(LOG_TAG, "Sending message StartGame to all clients");
        // TODO: collect data for game start; create useful message class
        server.sendToAllTCP(new StartGame());
    }
}
