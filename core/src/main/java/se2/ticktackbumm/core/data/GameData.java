package se2.ticktackbumm.core.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private final TickTackBummGame game;

    private final String LOG_TAG = "GAME_DATA";

    private final int maxGameScore;

    private List<Player> players;
    private int currentPlayerTurnIndex;
    private GameMode currentGameMode;
    private String currentGameModeText;
    private ArrayList<String> lockedWords;

    public GameData() {
        maxGameScore = 2; // TODO: hardcoded for testing purposes
        game = TickTackBummGame.getTickTackBummGame();

        players = new ArrayList<>();
        currentPlayerTurnIndex = 0;
        currentGameMode = GameMode.NONE;
        lockedWords = new ArrayList<>();
    }

    public int getMaxGameScore() {
        return maxGameScore;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getCurrentPlayerTurnIndex() {
        return currentPlayerTurnIndex;
    }

    public void setCurrentPlayerTurnIndex(int currentPlayerTurnIndex) {
        this.currentPlayerTurnIndex = currentPlayerTurnIndex;
    }

    public GameMode getCurrentGameMode() {
        return currentGameMode;
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

    public ArrayList<String> getLockedWords() {
        return lockedWords;
    }

    public void setLockedWords(ArrayList<String> lockedWords) {
        this.lockedWords = lockedWords;
    }

    public void resetLockedWords() {
        this.lockedWords = new ArrayList<>();
    }

    public int[] getPlayerScores() {
        int[] playerScores = new int[4];
        for (int i = 0; i < players.size(); i++) {
            playerScores[i] = players.get(i).getGameScore();
        }
        return playerScores;
    }

    public Image getUnfocusedAvatarImage(Player player) {
        Image avatar;
        switch (player.getPlayerAvatar()) {
            case BRUNETTE_GIRL:
                avatar = new Image(game.getManager().get("avatars/brunette_girl.png", Texture.class));
                break;
            case BLOND_GIRL:
                avatar = new Image(game.getManager().get("avatars/blond_girl.png", Texture.class));
                break;
            case BLACKHAIRED_GUY:
                avatar = new Image(game.getManager().get("avatars/blackhaired_guy.png", Texture.class));
                break;
            case BLOND_GUY:
                avatar = new Image(game.getManager().get("avatars/blond_guy.png", Texture.class));
                break;
            default:
                avatar = new Image(game.getManager().get("score/player1.png", Texture.class));
        }
        return avatar;
    }

    public Image getFocusedAvatarImage(Player player) {
        Image avatar;
        switch (player.getPlayerAvatar()) {
            case BRUNETTE_GIRL:
                avatar = new Image(game.getManager().get("avatars/brunette_girl_picked.png", Texture.class));
                break;
            case BLOND_GIRL:
                avatar = new Image(game.getManager().get("avatars/blond_girl_picked.png", Texture.class));
                break;
            case BLACKHAIRED_GUY:
                avatar = new Image(game.getManager().get("avatars/blackhaired_guy_picked.png", Texture.class));
                break;
            case BLOND_GUY:
                avatar = new Image(game.getManager().get("avatars/blond_guy_picked.png", Texture.class));
                break;
            default:
                avatar = new Image(game.getManager().get("score/player1.png", Texture.class));
        }
        return avatar;
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
