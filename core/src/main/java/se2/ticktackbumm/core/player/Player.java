package se2.ticktackbumm.core.player;

import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.data.Avatars;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.screens.WaitingScreen;

/**
 * The Player class provides all data and methods necessary for the client side of the game.
 * It holds information about the games players with data like the ID, the game score and the players
 * name. It is primarily used in the players array of {@link GameData}, to track all players of the game.
 */
public class Player {

    /**
     * The log tag is used to provide unique logging for the class.
     */
    private static final String LOG_TAG = "PLAYER";

    /**
     * The connection ID of the player to the server. The connection ID on the server side is incremented
     * every time a new connection is established and assigned to the player.
     */
    private int connectionId;
    /**
     * The player ID indicates on which position the player is in the players array of
     * {@link GameData}. Thus you can easily use this ID to retrieve the
     * player from {@link GameData}.
     */
    private int playerId;

    /**
     * The current game score of the player. It indicates how many times the bomb exploded in the players
     * hands. The higher the score, the worse the player is currently doing.
     */
    private int gameScore;
    /**
     * Indicates whether the player has cheated in the game before. If he has already cheated he can not
     * cheat again this game.
     */
    private boolean hasCheated;

    /**
     * The name the player chose for themself in the {@link WaitingScreen}.
     */
    private String playerName;
    /**
     * The avatar the player chose for themself in the {@link WaitingScreen}.
     */
    private Avatars playerAvatar;

    /**
     * Empty default constructor for kryonet and testing.
     */
    public Player() {
        // kryonet
    }

    /**
     * @param connectionId the connection ID the server assigned to the player
     * @param playerId     the position of the player in the players array in
     *                     {@link GameData}
     */
    public Player(int connectionId, int playerId) {
        this.connectionId = connectionId;
        this.playerId = playerId;
        this.gameScore = 0;
        this.hasCheated = false;
    }

    /**
     * Increments the player score, if it is smaller than the max game score.
     */
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

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public boolean getHasCheated() {
        return hasCheated;
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
        return "Player '" + playerName + "' with avatar '" + playerAvatar + "', ID " + playerId +
                ", connection ID " + connectionId + " and game score: " + gameScore;
    }

}
