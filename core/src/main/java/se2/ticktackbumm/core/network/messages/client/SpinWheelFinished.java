package se2.ticktackbumm.core.network.messages.client;

import se2.ticktackbumm.core.data.GameMode;

public class SpinWheelFinished {

    private GameMode gameMode;

    public SpinWheelFinished(){
        //kryonet
    }

    public SpinWheelFinished(GameMode gameMode){
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }


}
