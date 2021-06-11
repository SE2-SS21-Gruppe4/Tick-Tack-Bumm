package se2.ticktackbumm.core.listeners;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.Avatars;
import se2.ticktackbumm.core.screens.WaitingScreen;

public class ReadyButtonListener extends ClickListener {

    private final String LOG_TAG = "READY_BUTTON_LISTENER";

    private final Texture avatarError;
    private final Texture nameError;
    private final Texture avatarAndNameError;
    private final AssetManager assetManager;

    private final TickTackBummGame game;
    private final WaitingScreen waitingScreen;

    public ReadyButtonListener(WaitingScreen waitingScreen) {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.waitingScreen = waitingScreen;
        assetManager = game.getManager();

        avatarError = assetManager.get("waitingScreen/avatar.png", Texture.class);
        nameError = assetManager.get("waitingScreen/name.png", Texture.class);
        avatarAndNameError = assetManager.get("waitingScreen/avatarandname.png", Texture.class);
    }

    @SuppressWarnings("NewApi")
    @Override
    public void clicked(InputEvent event, float x, float y) {
        // TODO: get nickname and color, send in message, verify
        String playerName = waitingScreen.getPlayerNameTextField().getText();
        Avatars playerAvatar = game.getLocalPlayer().getPlayerAvatar();

        if (!isValidName(playerName)&&playerAvatar == null) {
            Log.error(LOG_TAG, "User input for player name is invalid and User selected no avatar " + playerName);
            waitingScreen.setBackground(avatarAndNameError);
            return;
        }

        if (!isValidName(playerName)) {
            Log.error(LOG_TAG, "User input for player name is invalid: " + playerName);
            waitingScreen.setBackground(nameError);
            return;
        }

        if (playerAvatar == null) {
            Log.error(LOG_TAG, "User selected no avatar, aborting");
            waitingScreen.setBackground(avatarError);
            return;
        }

        waitingScreen.getOriginalBackground();

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

    boolean isValidName(String playerName) {
        return playerName != null &&
                playerName.matches("^[0-9a-zA-Z]{4,12}$");
    }
}
