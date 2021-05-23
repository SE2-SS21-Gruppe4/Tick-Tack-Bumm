package se2.ticktackbumm.core.listeners;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.screens.WaitingScreen;

public class ReadyButtonListener extends ClickListener {

    private final TickTackBummGame game;
    private final WaitingScreen waitingScreen;

    public ReadyButtonListener(WaitingScreen waitingScreen) {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.waitingScreen = waitingScreen;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        // TODO: get nickname and color, send in message, verify

        game.getNetworkClient().getClientMessageSender().sendPlayerReady();

        waitingScreen.getReadyButton().getStyle().disabledFontColor = new Color(0, 1, 0, 1);
        waitingScreen.getReadyButton().setDisabled(true);
        waitingScreen.getReadyButton().clearListeners();
    }
}
