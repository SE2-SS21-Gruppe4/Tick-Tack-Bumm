package se2.ticktackbumm.core.network.messages.client;

public class StartBomb {

    private int bombTimer;

    public StartBomb() {

    }

    public StartBomb(int bombTimer) {
        this.bombTimer = bombTimer;
    }

    public int getBombTimer() {
        return this.bombTimer;
    }

}
