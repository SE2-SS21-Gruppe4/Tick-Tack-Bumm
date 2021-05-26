package se2.ticktackbumm.core.models.cards;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;

import java.security.SecureRandom;

public class Card {

    private final String[] vorArray = new String[]{"SCH", "GE", "ANG", "VOR", "SEH", "FRE", "GER", "ACK", "EXP", "ORG"};
    private final String[] middleArray = new String[]{"TER", "UT", "RDI","LEN","ULT","TRA","AHN","KEL","SON","TEN"};
    private final String[] nachArray = new String[] {"UNG","SCH","SER","KEN","CHE","EIT","ATZ","NER","ICH","TUR"};

    private final GameData gameData;

    private Stage stage;

    private final Texture backsideTexture;
    private final Image backsideImage;

    private final Texture frontsideTexture;
    private final Image frontsideImage;

    private String wordFromArray;
    private  Label cardWordLabel;

    private final SecureRandom random;

    private boolean isRevealed;

    //isRevealed moved in GameData class
    //private boolean isRevealed; // TODO: card flip also represented for other players; add to game data?

    private AssetManager assetManager;
    private TickTackBummGame game;

    public Card() {

        game = TickTackBummGame.getTickTackBummGame();
        assetManager = game.getManager();

        gameData = TickTackBummGame.getTickTackBummGame().getGameData();

        stage = new Stage();

        random = new SecureRandom();

        isRevealed = false;

        assetManager.load("card/backside.png",Texture.class);
        assetManager.finishLoading();

        backsideTexture = assetManager.get("card/backside.png",Texture.class);
        backsideImage = new Image(backsideTexture);



        // TODO: add switch over game modes?
        gameData.setCurrentGameModeText(getRandomSyllable());

        assetManager.load("card/frontside.png",Texture.class);
        assetManager.finishLoading();
        frontsideTexture = assetManager.get("card/frontside.png",Texture.class);
        frontsideImage = new Image(frontsideTexture);

        wordFromArray = getRandomSyllable();
        // TODO: use skin instead of LabelStyle
        cardWordLabel = new Label(gameData.getCurrentGameModeText(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    }

    public void drawBomb() {
     /*   if (Gdx.input.isTouched()) {
            setupFrontSide(spriteBatch);
        }

        setupBackSide(spriteBatch);*/

        if (Gdx.input.isTouched()){
              drawFrontSide();
        }

        drawBackSide();
        stage.draw();

    }

    public void drawFrontSide(){
        wordFromArray = getRandomSyllable();
        // TODO: use skin instead of LabelStyle
        cardWordLabel = new Label(wordFromArray, new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        cardWordLabel.setScale(4);

        frontsideImage.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 400, 200);
        cardWordLabel.setBounds(Gdx.graphics.getWidth() / 2.0f - 50, Gdx.graphics.getHeight() / 2.0f + 110, cardWordLabel.getWidth(), cardWordLabel.getHeight());

        stage.addActor(frontsideImage);
        stage.addActor(cardWordLabel);

        gameData.setCardRevealed(true);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
              frontsideImage.remove();
              cardWordLabel.remove();

                drawBackSide();

            }
        },5);

        //TODO check if it make sense
        gameData.setCardRevealed(false);
    }

    public void drawBackSide(){
        backsideImage.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 400, 200);

        stage.addActor(backsideImage);
    }



    public String getRandomSyllable() {
        return vorArray[random.nextInt(vorArray.length)];
    }



}
