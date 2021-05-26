package se2.ticktackbumm.core.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.Avatars;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.screens.WaitingScreen;

public class AvatarButtonListener extends ClickListener {

    private final TickTackBummGame game;
    private final GameData gameData;

    private final WaitingScreen waitingScreen;
    private final Avatars avatar;

    public AvatarButtonListener(WaitingScreen waitingScreen, Avatars avatar) {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.gameData = game.getGameData();

        this.waitingScreen = waitingScreen;
        this.avatar = avatar;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        game.getLocalPlayer().setPlayerAvatar(avatar);
        Log.info("Selected avatar set to: " +
                game.getLocalPlayer().getPlayerAvatar().toString());
    }
}
