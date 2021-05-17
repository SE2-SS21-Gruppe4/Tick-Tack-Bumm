package se2.ticktackbumm.core.models.cards;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.awt.Font;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import sun.font.Font2D;
import sun.font.FontScaler;

public class Card {

    private String[] arraywords;

    public static Stage stage;

    private Texture backsideTexture;
    private Image backsideImage;

    private String randomWord;
    private Texture frontsideTexture;
    private Image frontsideImage;

    private Random random;

    private boolean isRevealed;




    public Card() {
        arraywords = new String[]{"SPA", "VOR", "EIT", "ANG", "SAM", "FRE", "WER",
                "GER", "ACK", "EXP", "UNG"};

        random = new Random();

        isRevealed = false;

        //TODO with assetmanager called
        backsideTexture = new Texture("card/backside.jpg");
        backsideImage = new Image(backsideTexture);

        randomWord = getRandomWord();
        //TODO with assetmanager called
        frontsideTexture = new Texture("card/frontside.png");
        frontsideImage = new Image(frontsideTexture);

        stage = new Stage();

    }

    public void render() {

        if (Gdx.input.isTouched()) {
            drawFontSide();
        }

        drawBackSide();

    }

    public void drawBackSide() {
        setActorSettings(backsideImage, Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 400, 200);
        stage.addActor(backsideImage);

        stage.act();
        stage.draw();
    }

    public void drawFontSide() {

        Label cardwordLabel = new Label(randomWord, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        cardwordLabel.setFontScale(3);

        setActorSettings(frontsideImage, Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 400, 200);
        setActorSettings(cardwordLabel, Gdx.graphics.getWidth() / 2.0f -50, Gdx.graphics.getHeight() / 2.0f+90,cardwordLabel.getWidth(), cardwordLabel.getHeight());

        stage.addActor(frontsideImage);
        stage.addActor(cardwordLabel);

        stage.act();
        stage.draw();
    }


    public String getRandomWord() {
        int randomIndex = random.nextInt(18) + 1;
        return arraywords[randomIndex];
    }

    public void setActorSettings(Actor actor, float positionX, float positionY, float width, float height) {
        actor.setBounds(positionX, positionY, width, height);
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }

    public String getWord() {
        return this.randomWord;
    }
}
