package se2.ticktackbumm.core.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;

public class Score {
    private BitmapFont player1Score;
    private BitmapFont player2Score;
    private BitmapFont player3Score;
    private BitmapFont player4Score;

    private final ArrayList<BitmapFont> bitmapFonts;

    public Score() {
        initPictures();
        bitmapFonts = new ArrayList<>();
        addScores();
        addBitmaps();
    }

    public void initPictures() {
        player1Score = new BitmapFont();
        player2Score = new BitmapFont();
        player3Score = new BitmapFont();
        player4Score = new BitmapFont();
    }


    public void addScores() {
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
    }

    public void addBitmaps() {
        bitmapFonts.add(player1Score);
        bitmapFonts.add(player2Score);
        bitmapFonts.add(player3Score);
        bitmapFonts.add(player4Score);
    }

    public ArrayList<BitmapFont> getBitmaps() {
        return bitmapFonts;
    }
}

