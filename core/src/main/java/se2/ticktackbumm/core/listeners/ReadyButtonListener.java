package se2.ticktackbumm.core.listeners;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.Avatars;
import se2.ticktackbumm.core.screens.WaitingScreen;

public class ReadyButtonListener extends ClickListener {

    /**
     * The log tag is used to provide unique logging for the class.
     */
    private final String LOG_TAG = "READY_BUTTON_LISTENER";

    /**
     * The singleton instance of the game class. Provides functionality to read and alter the game's
     * state and data.
     */
    private final TickTackBummGame game;
    /**
     * Instance of the waiting screen of the current game.
     */
    private final WaitingScreen waitingScreen;

    /**
     * Constructs a listener for the ready button in the waiting screen. The listener takes a reference to
     * the instance of the waiting screen to modify the state of the screen when clicked.
     *
     * @param waitingScreen instance of the waiting screen of the current game
     */
    public ReadyButtonListener(WaitingScreen waitingScreen) {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.waitingScreen = waitingScreen;
    }

    @SuppressWarnings("NewApi")
    @Override
    public void clicked(InputEvent event, float x, float y) {
        String playerName = waitingScreen.getPlayerNameTextField().getText();
        Avatars playerAvatar = game.getLocalPlayer().getPlayerAvatar();

        if (!isValidName(playerName)) {
            Log.error(LOG_TAG, "User input for player name is invalid: " + playerName);
            return;
        }

        if (playerAvatar == null) {
            Log.error(LOG_TAG, "User selected no avatar, aborting");
            return;
        }

        game.getLocalPlayer().setPlayerName(playerName);

        game.getNetworkClient().getClientMessageSender().sendPlayerReady(playerName, playerAvatar);

        // disable ready button
        waitingScreen.getReadyButton().getStyle().disabledFontColor = new Color(0, 1, 0, 1);
        waitingScreen.getReadyButton().setDisabled(true);
        waitingScreen.getReadyButton().clearListeners();

        // disable avatar buttons
        waitingScreen.getAvatarButtonGroup().getButtons().forEach(Actor::clearListeners);

        // disable name text field
        waitingScreen.getPlayerNameTextField().setDisabled(true);
    }

    /**
     * @param playerName the input player name to validate
     * @return true if
     */
    boolean isValidName(String playerName) {
        return playerName != null &&
                playerName.matches("^[0-9a-zA-Z]{4,12}$");
    }
}
