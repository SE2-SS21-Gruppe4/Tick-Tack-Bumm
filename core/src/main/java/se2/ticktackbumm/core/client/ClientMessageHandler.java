package se2.ticktackbumm.core.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.network.messages.server.ConnectionSuccessful;
import se2.ticktackbumm.core.network.messages.server.PlayersUpdate;
import se2.ticktackbumm.core.network.messages.server.SomeResponse;
import se2.ticktackbumm.core.network.messages.server.StartGame;
import se2.ticktackbumm.core.screens.WaitingScreen;

/**
 * Handles all incoming server messages for a client.
 */
public class ClientMessageHandler {

    private final String LOG_TAG = "CLIENT_MESSAGE_HANDLER";

    private final TickTackBummGame game;
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
        Log.info(LOG_TAG, "<ConnectionSuccessful> Connected player added as local player: " + game.getLocalPlayer());

        Gdx.app.postRunnable(() -> game.setScreen(new WaitingScreen()));
    }

    public void handleStartGame(StartGame startGame) {
        Log.info(LOG_TAG, "<StartGame> Starting game");
        // TODO: parse data from message; init players and game

//        Gdx.app.postRunnable(() -> Timer.schedule(new Timer.Task() { // TODO: testing only
//            @Override
//            public void run() {
//                game.setScreen(new SpinWheelScreen());
//            }
//        }, 2f));
    }

    public void handlePlayersUpdate(PlayersUpdate playersUpdate) {
        Log.info(LOG_TAG, "<PlayersUpdate> Updating players in GameData");
        game.getGameData().setPlayers(playersUpdate.getPlayers());

        // if waiting for player, update player names in WaitingScreen
        if (game.getScreen() instanceof WaitingScreen) {
            ((WaitingScreen) game.getScreen()).updatePlayerLabels();
        }
    }
}
