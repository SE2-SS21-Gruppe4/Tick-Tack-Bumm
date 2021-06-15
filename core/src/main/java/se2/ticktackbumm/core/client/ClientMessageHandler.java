package se2.ticktackbumm.core.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.network.messages.client.BombStart;
import se2.ticktackbumm.core.network.messages.server.ConnectionSuccessful;
import se2.ticktackbumm.core.network.messages.server.GameFinished;
import se2.ticktackbumm.core.network.messages.server.GameUpdate;
import se2.ticktackbumm.core.network.messages.server.SomeResponse;
import se2.ticktackbumm.core.network.messages.client.StartBomb;
import se2.ticktackbumm.core.network.messages.server.*;
import se2.ticktackbumm.core.screens.WaitingScreen;

import java.util.Arrays;

/**
 * Handles all incoming server messages for a client.
 */
public class ClientMessageHandler {

    /**
     * The log tag is used to provide unique logging for the class.
     */
    private final String LOG_TAG = "CLIENT_MESSAGE_HANDLER";

    /**
     * The singleton instance of the game class. Provides functionality to read and alter the game's
     * state and data.
     */
    private final TickTackBummGame game;
    /**
     * The game data which is included in the singleton instance of the game class. Provides functionality
     * read and alter the game's general data.
     */
    private final GameData gameData;
    /**
     * The game's message sender, later contained in the singleton instance of the game class. Provides
     * functionality to send messages from client to server.
     */
    private final ClientMessageSender clientMessageSender;

    /**
     * Class constructor.
     * Creates a message handler, that handles all incoming server messages for a client.
     *
     * @param clientMessageSender the client message sender
     */
    public ClientMessageHandler(ClientMessageSender clientMessageSender) {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.gameData = game.getGameData();
        this.clientMessageSender = clientMessageSender;
    }

    /**
     * Method and message to test client server connection.
     *
     * @param someResponse the incoming message
     */
    public void handleSomeResponse(SomeResponse someResponse) {
        Log.info(LOG_TAG, "<SomeResponse> Server response to " + someResponse.getClass()
                + ": " + someResponse.getText());
    }

    /**
     * Handle incoming {@link ConnectionSuccessful} message from server to client.
     * Set new local player and goto {@link WaitingScreen}.
     *
     * @param connectionSuccessful the incoming message
     */
    public void handleConnectionSuccessful(ConnectionSuccessful connectionSuccessful) {
        Log.info(LOG_TAG, "<ConnectionSuccessful> Player successfully connected to server");

        game.setLocalPlayer(connectionSuccessful.getConnectedPlayer());
        Log.info(LOG_TAG, "<ConnectionSuccessful> Connected player added as local player: "
                + game.getLocalPlayer());

        Gdx.app.postRunnable(() -> game.setScreen(new WaitingScreen()));
    }

    /**
     * Handle incoming {@link StartGame} message from server to client.
     * Start new game on client side.
     */
    public void handleStartGame() {
        Log.info(LOG_TAG, "<StartGame> Starting game");

        Gdx.app.postRunnable(() -> Timer.schedule(new Timer.Task() { // TODO: testing only
            @Override
            public void run() {
                game.startNewGame();
            }
        }, 2f));
    }

    /**
     * Handle incoming {@link GameUpdate} message from server to client.
     * Set players array, current player turn index, game mode, game mode text and
     * locked words in client's {@link GameData}. Also update player labels, if
     * player is in {@link WaitingScreen}.
     *
     * @param gameUpdate the incoming message
     */
    public void handleGameUpdate(GameUpdate gameUpdate) {
        Log.info(LOG_TAG, "<GameUpdate> Updating game data");

        gameData.setPlayers(gameUpdate.getPlayers());
        gameData.setCurrentPlayerTurnIndex(gameUpdate.getCurrentPlayerTurnIndex());
        gameData.setCurrentGameMode(gameUpdate.getCurrentGameMode());
        gameData.setCurrentGameModeText(gameUpdate.getCurrentGameModeText());
        gameData.setLockedWords(gameUpdate.getLockedWords());

        // if waiting for other players, update player names in WaitingScreen
        if (game.getScreen() instanceof WaitingScreen) {
            ((WaitingScreen) game.getScreen()).updatePlayerLabels();
        }
    }

    /**
     * Handle incoming {@link NextTurn} message from server to client.
     * Start next turn on client side.
     */
    public void handleNextTurn() {
        Log.info(LOG_TAG, "<NextTurn> Starting next turn");

        game.startNextTurn();
    }

    /**
     * Handle incoming {@link NextRound} message from server to client.
     * Start next round on client side.
     */
    public void handleNextRound() {
        Log.info(LOG_TAG, "<NextRound> Starting next round");

        game.startNextRound();
    }

    /**
     * Handle incoming {@link GameFinished} message from server to client.
     * Trigger finish game on main game screen.
     */
    public void handleGameFinished(GameFinished gameFinished) {
        Log.info(LOG_TAG, "<GameFinished> Finishing game; displaying final scores");
        Log.info(LOG_TAG, "<GameFinished> " + Arrays.toString(gameFinished.getPlacedPlayers()));

        game.finishGame();
    }

    /**
     * Handle incoming {@link StartBomb} message from server to client.
     * Set the bomb timer in game screen to the time value received from server.
     *
     * @param startBomb the incoming message
     */
    public void handleStartBomb(StartBomb startBomb) {
        game.setBombToTick(startBomb.getBombTimer());
    }

    public void handleSpinWheelFinished(){
        Log.info(LOG_TAG, "<SpinWheelFinished-CurrentGameMode> : "  + gameData.getCurrentGameMode());
        game.spinWheelFinished();
    }

    public void handleSpinWheelStarted() {
        Log.info(LOG_TAG, "<SpinWheelStarted> : "  + gameData.getCurrentGameMode());
        game.spinWheelStarted();
    }
}
