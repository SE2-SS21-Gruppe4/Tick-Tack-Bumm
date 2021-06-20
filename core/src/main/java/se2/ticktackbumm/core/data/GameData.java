package se2.ticktackbumm.core.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.player.Player;
import se2.ticktackbumm.core.screens.WinnerScreen;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class GameData {

    /**
     * The log tag is used to provide unique logging for the class.
     */
    private final String LOG_TAG = "GAME_DATA";

    /**
     * The singleton instance of the game class. Provides functionality to read and alter the game's
     * state and data.
     */
    private final TickTackBummGame game;

    /**
     * The maximum game score the players can reach. When the score is reached by a player, he loses
     * and the game is finished.
     */
    private final int maxGameScore;

    /**
     * The list of active players in this game. The player's ID corresponds to the position in
     * this list.
     */
    private List<Player> players;
    /**
     * The index in the players list of the player that currently has his turn.
     */
    private int currentPlayerTurnIndex;
    /**
     * The current game mode chosen by the spin wheel at the start of each round.
     */
    private GameMode currentGameMode;
    /**
     * The current task text the players have (syllable, scrambled word, ...). Set at the start of
     * each round when revealing the next card.
     */
    private String currentGameModeText;
    /**
     * The array of players sorted by their game score in ascending order. Used for the {@link WinnerScreen}
     * to display the winners.
     */
    private Player[] placedPlayers;
    /**
     * The list of words the players already used this round. The list is reset when a new round starts.
     */
    private ArrayList<String> lockedWords;

    /**
     * Indicates whether the card is currently revealed in the main game screen or not.
     */
    private boolean isCardRevealed;

    /**
     * Takes time to count down the bomb coming from the server
     */
    private float bombTimer;

    /**
     * Constructs a new game data instance. This is used for each player and the data on the
     * game server side.
     */
    public GameData() {
        maxGameScore = 4; // hardcoded for testing purposes
        game = TickTackBummGame.getTickTackBummGame();

        placedPlayers = null;

        players = new ArrayList<>();
        currentPlayerTurnIndex = 0;
        currentGameMode = GameMode.NONE;
        lockedWords = new ArrayList<>();

        isCardRevealed = false;
        currentGameModeText = "";

        bombTimer = 0;
    }

    /**
     * Returns an array containing all game scores of the active players in a game. Used for
     * displaying the game scores in the main game screen.
     *
     * @return int[] of the players game scores
     */
    public int[] getPlayerScores() {
        int[] playerScores = new int[4];
        for (int i = 0; i < players.size(); i++) {
            playerScores[i] = players.get(i).getGameScore();
        }
        return playerScores;
    }

    /**
     * Returns the image of players avatar for the players who are not on the turn
     *
     * @return Image of the players avatar
     */
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

    /**
     * Returns the image of players avatar for the players who are on the turn
     *
     * @return Image of the players avatar
     */
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

    /**
     * Returns the {@link Player} object with the specified connection ID from the players list.
     *
     * @param connectionId the player's connection ID
     * @return if found the requested {@link Player} object, null otherwise
     */
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

    /**
     * Sets the current player turn index to the next player in the list. Does a wrap around
     * to the first player, after the player's turn.
     */
    public void setNextPlayerTurn() {
        currentPlayerTurnIndex = (currentPlayerTurnIndex + 1) % players.size();
    }

    public void setRandomBombTimer(int upperBoundInclusive, int offset) {
        bombTimer = new SecureRandom().nextInt(upperBoundInclusive + 1) + offset;
    }

    // simple getters & setters

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

    public boolean isCardRevealed() {
        return this.isCardRevealed;
    }

    public void setCardRevealed(boolean cardRevealed) {
        this.isCardRevealed = cardRevealed;
    }

    public Player[] getPlacedPlayers() {
        return placedPlayers;
    }

    public void setPlacedPlayers(Player[] placedPlayers) {
        this.placedPlayers = placedPlayers;
    }

    public float getBombTimer() {
        return this.bombTimer;
    }

    public void setBombTimer(float timer) {
        this.bombTimer = timer;
    }

}
