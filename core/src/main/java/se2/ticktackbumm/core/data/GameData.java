package se2.ticktackbumm.core.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.esotericsoftware.minlog.Log;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.listeners.AvatarButtonListener;
import se2.ticktackbumm.core.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    TickTackBummGame game;

    private final String LOG_TAG = "GAME_DATA";

    private final int maxGameScore;

    private List<Player> players;
    private int currentPlayerTurnIndex;
    private GameMode currentGameMode;
    private String currentGameModeText;

    public GameData() {
        game = TickTackBummGame.getTickTackBummGame();

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

    public String getCurrentGameModeText() {
        return currentGameModeText;
    }

    public void setCurrentGameModeText(String currentGameModeText) {
        this.currentGameModeText = currentGameModeText;
    }

    public int[] getPlayerScores() {
        int[] playerScores = new int[4];
        for (int i = 0; i < players.size(); i++) {
            playerScores[i] = players.get(i).getGameScore();
        }
        return playerScores;
    }

    public Image getPlayerAvatarImage(Player player) {
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
                avatar = new Image(game.getManager().get("score/blond_guy.png", Texture.class));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + player.getPlayerAvatar());
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
