package se2.ticktackbumm.server.network;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.network.messages.client.*;
import se2.ticktackbumm.core.network.messages.server.*;
import se2.ticktackbumm.core.player.Player;

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

    public void sendGameUpdate() {
        Log.info(LOG_TAG, "Sending message GameUpdate to all clients");
        server.sendToAllTCP(
                new GameUpdate(NetworkServer.getNetworkServer().getServerData().getGameData())
        );
    }

    public void sendStartGame() {
        Log.info(LOG_TAG, "Sending message StartGame to all clients");
        // TODO: collect data for game start; create useful message class
        server.sendToAllTCP(new StartGame());
    }

    public void sendNextTurn() {
        Log.info(LOG_TAG, "Sending message NextTurn to all clients");
        server.sendToAllTCP(new NextTurn());
    }

    public void sendNextRound() {
        Log.info(LOG_TAG, "Sending message NextRound to all clients");
        server.sendToAllTCP(new NextRound());
    }

    public void sendGameFinished(Player[] placedPlayers) {
        Log.info(LOG_TAG, "Sending message GameFinished to all clients");
        server.sendToAllTCP(new GameFinished(placedPlayers));
    }


    public void sendStartBomb() {
        Log.info(LOG_TAG, "Sending message StartBomb");
        server.sendToAllTCP(new StartBomb());
    }

    public void sendSpinWheelFinished() {
        server.sendToAllTCP(new SpinWheelFinished());
    }

    public void sendSpinWheelStarted() {
        server.sendToAllTCP((new SpinWheelStarted()));
    }

    public void sendCardOpened() {
        Log.info(LOG_TAG, "Sending message CardOpened");
        server.sendToAllTCP(new CardOpened());
    }

    public void sendBombExploded(int connectionId) {
        server.sendToAllExceptTCP(connectionId, new BombExploded());
    }
}
