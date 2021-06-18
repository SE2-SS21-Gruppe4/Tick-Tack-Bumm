package se2.ticktackbumm.core.network.messages.client;

import se2.ticktackbumm.core.data.GameMode;

public class SpinWheelStarted {

    private GameMode gameMode;

    public SpinWheelStarted() {
        //kryonet
    }

    public SpinWheelStarted(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
}
