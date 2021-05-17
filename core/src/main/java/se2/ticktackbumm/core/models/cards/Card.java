package se2.ticktackbumm.core.models.cards;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;

import java.util.Random;

public class Card {

    private final String[] syllableArray;
    private GameData gameData;

    public Stage stage;

    private Texture backsideTexture;
    private Image backsideImage;

    private Texture frontsideTexture;
    private Image frontsideImage;

    private Random random;

    private boolean isRevealed; // TODO: card flip also represented for other players; add to game data?

    public Card() {
        gameData = TickTackBummGame.getTickTackBummGame().getGameData();

        syllableArray = new String[]{"SPA", "VOR", "EIT", "ANG", "SAM", "FRE", "WER", "GER", "ACK", "EXP", "UNG"};

        random = new Random();

        isRevealed = false;

        // TODO: load and get with AssetManager
        backsideTexture = new Texture("card/backside.jpg");
        backsideImage = new Image(backsideTexture);

        // TODO: add switch over game modes?
        gameData.setCurrentGameModeText(getRandomSyllable());

        // TODO: load and get with AssetManager
        frontsideTexture = new Texture("card/frontside.png");
        frontsideImage = new Image(frontsideTexture);

        stage = new Stage();
    }

    public void render() {
        // TODO: set touch listener on card?
        if (Gdx.input.isTouched()) {
            drawFontSide();
        }
        drawBackSide();
    }

    // TODO: refactor draw methods/render methods
    public void drawBackSide() {
        setActorSettings(backsideImage, Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 400, 200);
        stage.addActor(backsideImage);

        stage.act();
        stage.draw();
    }

    // TODO: refactor draw methods/render methods
    public void drawFontSide() {
        Label cardWordLabel = new Label(gameData.getCurrentGameModeText(), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        cardWordLabel.setFontScale(3);

        setActorSettings(frontsideImage, Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 400, 200);
        setActorSettings(cardWordLabel, Gdx.graphics.getWidth() / 2.0f - 50, Gdx.graphics.getHeight() / 2.0f + 90, cardWordLabel.getWidth(), cardWordLabel.getHeight());

        stage.addActor(frontsideImage);
        stage.addActor(cardWordLabel);

        stage.act();
        stage.draw();
    }

    public String getRandomSyllable() {
        return syllableArray[random.nextInt(syllableArray.length)];
    }

    // TODO: refactor to have more logic, split image and label?
    public void setActorSettings(Actor actor, float positionX, float positionY, float width, float height) {
        actor.setBounds(positionX, positionY, width, height);
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }
}
