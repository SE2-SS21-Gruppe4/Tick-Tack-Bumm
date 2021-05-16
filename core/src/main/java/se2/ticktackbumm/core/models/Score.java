package se2.ticktackbumm.core.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.models.attributes.Position;

public class Score{
    private final Image score1;
    private final Image score2;
    private final Image score3;
    private final Image score4;
    private final BitmapFont player1Score;
    private final BitmapFont player2Score;
    private final BitmapFont player3Score;
    private final BitmapFont player4Score;
    private final ArrayList<Image> player;
    private final ArrayList<BitmapFont> bitmapFonts;

    public Score() {
        player1Score = new BitmapFont();
        player2Score = new BitmapFont();
        player3Score = new BitmapFont();
        player4Score = new BitmapFont();
        Texture player1 = new Texture("score/player1.png");
        Texture player2 = new Texture("score/player2.png");
        Texture player3 = new Texture("score/player3.png");
        Texture player4 = new Texture("score/player4.png");
        score1 = new Image(player1);
        score2 = new Image(player2);
        score3 = new Image(player3);
        score4 = new Image(player4);
        player = new ArrayList<>();
        bitmapFonts = new ArrayList<>();
        addPlayer();
        addBitmaps();
    }


    public void addPlayer(){
        player1Score.setColor(Color.BLACK);
        player1Score.getData().setScale(3);
        player1Score.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        player2Score.setColor(Color.BLACK);
        player2Score.getData().setScale(3);
        player2Score.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        player3Score.setColor(Color.BLACK);
        player3Score.getData().setScale(3);
        player3Score.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        player4Score.setColor(Color.BLACK);
        player4Score.getData().setScale(3);
        player4Score.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        player.add(score1);
        player.add(score2);
        player.add(score3);
        player.add(score4);
    }

    public void addBitmaps(){
        bitmapFonts.add(player1Score);
        bitmapFonts.add(player2Score);
        bitmapFonts.add(player3Score);
        bitmapFonts.add(player4Score);
    }

    public void placePlayer(){
        player.get(0).setPosition((float)TickTackBummGame.WIDTH/2-200, (float)TickTackBummGame.HEIGHT/2-200);
        score1.setPosition(100f,10f);
        score1.setPosition(10f,100f);
        score1.setPosition(100f,100f);
    }

    public ArrayList<BitmapFont> getBitmaps() {
        return bitmapFonts;
    }

    public ArrayList<Image> getPlayer() {
        return player;
    }
}
