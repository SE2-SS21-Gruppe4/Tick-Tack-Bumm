package se2.ticktackbumm.core.data;

import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private final String LOG_TAG = "GAME_DATA";

    private final int maxGameScore;

    private List<Player> players;
    private int currentPlayerTurnIndex;
    private GameMode currentGameMode;
    private String currentGameModeText;
    private boolean isCardRevealed;

    public GameData() {
        maxGameScore = 10; // hardcoded for testing purposes

        players = new ArrayList<>();
        currentPlayerTurnIndex = 0;
        currentGameMode = GameMode.NONE;
        isCardRevealed = false;
    }

    public int getMaxGameScore() {
        return maxGameScore;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getCurrentPlayerTurnIndex() {
        return currentPlayerTurnIndex;
    }

    public GameMode getCurrentGameMode() {
        return currentGameMode;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setCurrentPlayerTurnIndex(int currentPlayerTurnIndex) {
        this.currentPlayerTurnIndex = currentPlayerTurnIndex;
    }

    public void setCurrentGameMode(GameMode currentGameMode) {
        this.currentGameMode = currentGameMode;
    }

    public String getCurrentGameModeText() {
        return currentGameModeText;
    }

    public void setCurrentGameModeText(String currentGameModeText) {
        this.currentGameModeText = currentGameModeText;
    }

    public void setCardRevealed(boolean cardRevealed){
        this.isCardRevealed = cardRevealed;
    }
    public boolean getCardRevealed(){
        return this.isCardRevealed;
    }

    public int[] getPlayerScores() {
        int[] playerScores = new int[4];
        for (int i = 0; i < players.size(); i++) {
            playerScores[i] = players.get(i).getGameScore();
        }
        return playerScores;
    }

    public Player getPlayerByConnectionId(int connectionId) {
        for (Player player : players) {
            if (player.getConnectionId() == connectionId) {
                return player;
            }
        }

        Log.error(LOG_TAG, "Could not find player with connection ID '" + connectionId
                + "' in players");

        return null;
    }

    public void setNextPlayerTurn() {
        currentPlayerTurnIndex = (currentPlayerTurnIndex + 1) % players.size();
    }
}
