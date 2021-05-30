package se2.ticktackbumm.core.network.messages.server;

import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.data.GameMode;
import se2.ticktackbumm.core.player.Player;

import java.util.List;

public class GameUpdate {

    private List<Player> players;
    private int currentPlayerTurnIndex;
    private GameMode currentGameMode;
    private String currentGameModeText;

    public GameUpdate() {
        // kryonet
    }

//    public GameUpdate(List<Player> players) {
//        this.players = players;
//    }

    public GameUpdate(GameData gameData) {
        this.players = gameData.getPlayers();
        this.currentPlayerTurnIndex = gameData.getCurrentPlayerTurnIndex();
        this.currentGameMode = gameData.getCurrentGameMode();
        this.currentGameModeText = gameData.getCurrentGameModeText();
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

    public String getCurrentGameModeText() {
        return currentGameModeText;
    }
}