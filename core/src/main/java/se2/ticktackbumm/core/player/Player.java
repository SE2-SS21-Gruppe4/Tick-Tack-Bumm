package se2.ticktackbumm.core.player;

public class Player {
    private int connectionId;
    private int playerId;

    private int gameScore;
    private boolean hasCheated;

    public Player() {
        // kryonet
    }

    public Player(int connectionId, int playerId) {
        this.connectionId = connectionId;
        this.playerId = playerId;
        this.gameScore = 0;
        this.hasCheated = false;
    }

    void updatePlayer(Player player) {
        if (this.connectionId == player.connectionId && this.playerId == player.playerId) {
            this.setGameScore(player.getGameScore());
            this.setHasCheated(player.getHasCheated());
        }
    }

    public int getConnectionId() {
        return connectionId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getGameScore() {
        return gameScore;
    }

    public boolean getHasCheated() {
        return hasCheated;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public void setHasCheated(boolean hasCheated) {
        this.hasCheated = hasCheated;
    }
}
