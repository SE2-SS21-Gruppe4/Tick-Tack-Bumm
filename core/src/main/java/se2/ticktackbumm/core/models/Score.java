package se2.ticktackbumm.core.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;

public class Score {
    private final TickTackBummGame game;
    private final GameData gameData;
    private Label player1;
    private Label player2;
    private Label player3;
    private Label player4;
    private Skin skin;

    private int[] playerScore;

    private final ArrayList<Label> playerScoreLabels;

    public Score() {
        game = TickTackBummGame.getTickTackBummGame();
        gameData = game.getGameData();

        playerScore = gameData.getPlayerScores();

        skin = game.getManager().get("ui/uiskin.json", Skin.class);
        skin.getFont("default-font").getData().setScale(3f);

        playerScoreLabels = new ArrayList<>();
        initScores();
        addScoresToList();
    }

    public void initScores() {
        player1 = new Label(String.valueOf(playerScore[0]), skin);
        player2 = new Label(String.valueOf(playerScore[1]), skin);
        //player3 = new Label(String.valueOf(playerScore[2]), skin);
        //player4 = new Label(String.valueOf(playerScore[3]), skin);
        player1.setColor(Color.WHITE);
        player2.setColor(Color.WHITE);
        //player3.setColor(Color.WHITE);
        //player4.setColor(Color.WHITE);
    }

    public void setPlayerScore(int[] playerScore) {
        this.playerScore = playerScore;
        initScores();
    }

    public void addScoresToList() {
        playerScoreLabels.add(player1);
        playerScoreLabels.add(player2);
        playerScoreLabels.add(player3);
        playerScoreLabels.add(player4);
    }

    public Label getPlayer1() {
        return player1;
    }

    public Label getPlayer2() {
        return player2;
    }

    public Label getPlayer3() {
        return player3;
    }

    public Label getPlayer4() {
        return player4;
    }

    public ArrayList<Label> getPlayerScoreLabels() {
        return playerScoreLabels;
    }

    public void setPlayer1(Label player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Label player2) {
        this.player2 = player2;
    }

    public void setPlayer3(Label player3) {
        this.player3 = player3;
    }

    public void setPlayer4(Label player4) {
        this.player4 = player4;
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

