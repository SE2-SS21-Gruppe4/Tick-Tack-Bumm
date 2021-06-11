package se2.ticktackbumm.core.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.network.messages.client.BombStart;
import se2.ticktackbumm.core.network.messages.server.ConnectionSuccessful;
import se2.ticktackbumm.core.network.messages.server.GameFinished;
import se2.ticktackbumm.core.network.messages.server.GameUpdate;
import se2.ticktackbumm.core.network.messages.server.SomeResponse;
import se2.ticktackbumm.core.screens.WaitingScreen;

import java.util.Arrays;

/**
 * Handles all incoming server messages for a client.
 */
public class ClientMessageHandler {

    private final String LOG_TAG = "CLIENT_MESSAGE_HANDLER";

    private final TickTackBummGame game;
    private final GameData gameData;

    /**
     * Client instance to handle messages for.
     */
    private final Client client;
    private final ClientMessageSender clientMessageSender;

    /**
     * Class constructor.
     * Creates a message handler, that handles all incoming server messages for a client.
     *
     * @param client the client to handle messages for
     */
    public ClientMessageHandler(Client client, ClientMessageSender clientMessageSender) {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.gameData = game.getGameData();

        this.client = client;
        this.clientMessageSender = clientMessageSender;
    }

    // Test method
    public void handleSomeResponse(SomeResponse someResponse) {
        Log.info(LOG_TAG, "<SomeResponse> Server response to " + someResponse.getClass()
                + ": " + someResponse.getText());
    }

    public void handleConnectionSuccessful(ConnectionSuccessful connectionSuccessful) {
        Log.info(LOG_TAG, "<ConnectionSuccessful> Player successfully connected to server");

        game.setLocalPlayer(connectionSuccessful.getConnectedPlayer());
        Log.info(LOG_TAG, "<ConnectionSuccessful> Connected player added as local player: "
                + game.getLocalPlayer());

        Gdx.app.postRunnable(() -> game.setScreen(new WaitingScreen()));
    }

    public void handleStartGame() {
        Log.info(LOG_TAG, "<StartGame> Starting game");

        Gdx.app.postRunnable(() -> Timer.schedule(new Timer.Task() { // TODO: testing only
            @Override
            public void run() {
                game.startNewGame();
            }
        }, 2f));
    }

    public void handleGameUpdate(GameUpdate gameUpdate) {
        Log.info(LOG_TAG, "<GameUpdate> Updating game data");

        gameData.setPlayers(gameUpdate.getPlayers());
        gameData.setCurrentPlayerTurnIndex(gameUpdate.getCurrentPlayerTurnIndex());
        gameData.setCurrentGameMode(gameUpdate.getCurrentGameMode());
        gameData.setCurrentGameModeText(gameUpdate.getCurrentGameModeText());

        // if waiting for other players, update player names in WaitingScreen
        if (game.getScreen() instanceof WaitingScreen) {
            ((WaitingScreen) game.getScreen()).updatePlayerLabels();
        }
    }

    public void handleNextTurn() {
        Log.info(LOG_TAG, "<NextTurn> Starting next turn");

        game.startNextTurn();
    }

    public void handleNextRound() {
        Log.info(LOG_TAG, "<NextRound> Starting next round");

        game.startNextRound();
    }

    public void handleGameFinished(GameFinished gameFinished) {
        Log.info(LOG_TAG, "<GameFinished> Finishing game; displaying final scores");
        Log.info(LOG_TAG, "<GameFinished> " + Arrays.toString(gameFinished.getPlacedPlayers()));

        game.finishGame();
    }

    public void handleStartBomb(BombStart bombStart) {
        game.setBombToTick(bombStart.getBombTimer());
    }
}
