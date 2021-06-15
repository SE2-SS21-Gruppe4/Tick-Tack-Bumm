package se2.ticktackbumm.server.network;

import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.data.GameMode;
import se2.ticktackbumm.core.network.messages.client.PlayerReady;
import se2.ticktackbumm.core.network.messages.client.PlayerTaskCompleted;
import se2.ticktackbumm.core.network.messages.client.SomeRequest;
import se2.ticktackbumm.core.player.Player;
import se2.ticktackbumm.server.data.ServerData;

import java.security.SecureRandom;

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
        Log.info(LOG_TAG, "<PlayerTaskCompleted> Handling message PlayerTaskCompleted");

        serverData.getGameData().setNextPlayerTurn();
        serverData.getGameData().getLockedWords().add(playerTaskCompleted.getUsedWord());

        serverMessageSender.sendGameUpdate();
        serverMessageSender.sendNextTurn();
    }

    public void handleBombExploded(int connectionId) {
        Log.info(LOG_TAG, "<BombExploded> Handling message BombExploded");

        serverData.getGameData().setNextPlayerTurn();
        serverData.getGameData().getPlayerByConnectionId(connectionId).incPlayerScore();
        serverData.getGameData().resetLockedWords();

        serverMessageSender.sendGameUpdate();

        if (serverData.hasGameFinished()) {
            serverMessageSender.sendGameFinished(serverData.getPlacedPlayers());
        } else {
            serverMessageSender.sendNextRound();
        }
    }

    public void handlePlayerReady(PlayerReady playerReady, int connectionId) {
        Log.info(LOG_TAG, "<PlayerReady> Handling message PlayerReady");

        Player currentPlayer = serverData.getGameData().getPlayerByConnectionId(connectionId);
        currentPlayer.setPlayerName(playerReady.getPlayerName());
        currentPlayer.setPlayerAvatar(playerReady.getPlayerAvatar());
        serverMessageSender.sendGameUpdate();

        serverData.incPlayersReady();
        if (serverData.arePlayersReady()) {
            serverMessageSender.sendStartGame();
        }
    }

    public void handleBombStart() {
        int timer = new SecureRandom().nextInt(30) + 1;
        serverMessageSender.sendBombStart(timer);
    }


    public void handleSpinWheelFinished(GameMode gameMode) {
        serverData.getGameData().setCurrentGameMode(gameMode);
        serverMessageSender.sendGameUpdate();
        serverMessageSender.sendSpinWheelFinished();
    }

    public void handleSpinWheelStarted(GameMode gameMode) {
        serverData.getGameData().setCurrentGameMode(gameMode);
        serverMessageSender.sendGameUpdate();
        serverMessageSender.sendSpinWheelStarted();
    }

    public void handleCardOpened(String word) {
        serverData.getGameData().setCurrentGameModeText(word);
        serverMessageSender.sendCardOpened(word);
        serverMessageSender.sendGameUpdate();

    }
}
