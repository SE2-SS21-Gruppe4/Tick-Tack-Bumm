package se2.ticktackbumm.core.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;

import java.util.ArrayList;
/**
 * Score is to display the playerScores and communicate with the server to update the Score each round
 *
 * @author Daniel Fabian Frankl
 * @version 1.0
 */
public class Score {
    /**
     * game constants
     */
    private TickTackBummGame game;
    private GameData gameData;
    /**
     * Scene 2D UI
     */
    private Label player1;
    private Label player2;
    private Label player3;
    private Label player4;
    private Skin skin;
    /**
     * List and array for the labels and the scores
     */
    private ArrayList<Label> playerScoreLabels;
    private int[] playerScore;
    /**
     * Class constructor.
     * for the testclass
     */
    public Score(int[] playerScore) {
        this.playerScore = playerScore;
    }
    /**
     * Class constructor.
     * init variables, init list and array
     * methodcalls
     */
    public Score() {
        //init game constants
        game = TickTackBummGame.getTickTackBummGame();
        gameData = game.getGameData();

        //load playerscores in the array from gameData
        playerScore = gameData.getPlayerScores();

        //init skin
        skin = game.getManager().get("ui/uiskin.json", Skin.class);
        skin.getFont("default-font").getData().setScale(3f);

        //init the List
        playerScoreLabels = new ArrayList<>();

        //methodcalls
        initScores();
        addScoresToList();
    }

    public int getPlayerScore(int position) {
        return playerScore[position];
    }

    /**
     * load the score of each player from the playerScore array into Player
     */
    public void initScores() {
        player1 = new Label(String.valueOf(playerScore[0]) + "/4", skin);
        player2 = new Label(String.valueOf(playerScore[1]) + "/4", skin);
        //player3 = new Label(String.valueOf(playerScore[2]), skin);
        //player4 = new Label(String.valueOf(playerScore[3]), skin);
        player1.setColor(Color.WHITE);
        player2.setColor(Color.WHITE);
        //player3.setColor(Color.WHITE);
        //player4.setColor(Color.WHITE);
    }

    /**
     * set the playerScore array and update the Scores for the MainGameScreen
     */
    public void setPlayerScore(int[] playerScore) {
        this.playerScore = playerScore;
        initScores();
    }

    /**
     * add alle the initialized players to the labels list
     */
    public void addScoresToList() {
        playerScoreLabels.add(player1);
        playerScoreLabels.add(player2);
        playerScoreLabels.add(player3);
        playerScoreLabels.add(player4);
    }

    public Label getPlayer1() {
        return player1;
    }

    public void setPlayer1(Label player1) {
        this.player1 = player1;
    }

    public Label getPlayer2() {
        return player2;
    }

    public void setPlayer2(Label player2) {
        this.player2 = player2;
    }

    public Label getPlayer3() {
        return player3;
    }

    public void setPlayer3(Label player3) {
        this.player3 = player3;
    }

    public Label getPlayer4() {
        return player4;
    }

    public void setPlayer4(Label player4) {
        this.player4 = player4;
    }

    public Label getPlayerScoreLabels(int position) {
        return playerScoreLabels.get(position);
    }

    @Override
    public String toString() {
        return "Score{" +
                "player1=" + player1 +
                ", player2=" + player2 +
                ", player3=" + player3 +
                ", player4=" + player4 +
                '}';
    }
}

