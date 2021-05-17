package se2.ticktackbumm.core.network.messages;

public class PlayerTaskCompleted {

    private int playerIndex;

    public PlayerTaskCompleted() {
        // kryonet
    }

    public PlayerTaskCompleted(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
