package se2.ticktackbumm.core.gamedata;

import se2.ticktackbumm.core.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private final int maxGameScore;

    private List<Player> players;
    private int currentPlayerTurnIndex;
    private GameMode currentGameMode;

    public GameData() {
        maxGameScore = 10; // hardcoded for testing purposes

        players = new ArrayList<>();
        currentPlayerTurnIndex = 0;
        currentGameMode = GameMode.NONE;
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
}
