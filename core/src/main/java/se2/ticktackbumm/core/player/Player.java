package se2.ticktackbumm.core.player;

import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.data.Avatars;
import sun.net.NetworkServer;

public class Player {
    private static final String LOG_TAG = "PLAYER";

    private int connectionId;
    private int playerId;

    private int gameScore;
    private boolean hasCheated;

    private String playerName;
    private Avatars playerAvatar;

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

    // TODO: refactor to work on server side with max player constant
    public void incPlayerScore() {
        if (gameScore < 10) {
            Log.info(LOG_TAG, "Increasing game score of player: " + getPlayerName());
            gameScore++;
        }

        Log.info(LOG_TAG, "Player game score: " + getGameScore());
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Avatars getPlayerAvatar() {
        return playerAvatar;
    }

    public void setPlayerAvatar(Avatars playerAvatar) {
        this.playerAvatar = playerAvatar;
    }

    @Override
    public String toString() {
        return "Player " + playerId + " with connectionId " + connectionId;
    }
}
