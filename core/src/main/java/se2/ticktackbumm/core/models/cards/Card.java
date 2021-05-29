package se2.ticktackbumm.core.models.cards;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.data.GameMode;

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
    private Skin labelSkin;
    private  Label cardWordLabel;

    private final SecureRandom random;

    private boolean isRevealed;

    //isRevealed moved in GameData class
    //private boolean isRevealed; // TODO: card flip also represented for other players; add to game data?

    private AssetManager assetManager;
    private TickTackBummGame game;

    private Sound openCardSound;
    private Sound cardSchufflingSound;

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



        assetManager.load("card/frontside.png",Texture.class);
        assetManager.finishLoading();

        frontsideTexture = assetManager.get("card/frontside.png",Texture.class);
        frontsideImage = new Image(frontsideTexture);


        wordFromArray = getWordsDependOnMode();

        labelSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        labelSkin.getFont("default-font").getData().setScale(3);

        cardWordLabel = new Label(getWordsDependOnMode(), labelSkin);

    }

    public void drawCard() {
        if (Gdx.input.isTouched()){
              drawFrontSide();
        }

        drawBackSide();
        stage.draw();

    }

    public void drawFrontSide(){
        gameData.setCurrentGameMode(GameMode.POSTFIX);
        wordFromArray = getWordsDependOnMode();
        // TODO: use skin instead of LabelStyle
        cardWordLabel = new Label(wordFromArray,labelSkin);


        frontsideImage.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 400, 200);
        cardWordLabel.setBounds(Gdx.graphics.getWidth() / 2.0f - 50, Gdx.graphics.getHeight() / 2.0f + 110, cardWordLabel.getWidth(), cardWordLabel.getHeight());

        stage.addActor(frontsideImage);
        stage.addActor(cardWordLabel);

        gameData.setCardRevealed(true);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                wordFromArray = "";
              frontsideImage.remove();
              cardWordLabel.remove();

                 stage.draw();
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

    public String getWordsDependOnMode(){
        switch (gameData.getCurrentGameMode()){
            case NONE:
                return "";
            case PREFIX:
                return getRandomSyllable(this.vorArray);

            case INFIX:
                return getRandomSyllable(this.middleArray);

            case POSTFIX:
                return getRandomSyllable(this.nachArray);

            default:
                return null;
        }

    }

    public String getRandomSyllable(String[] currentArray) {
       // return vorArray[random.nextInt(vorArray.length)];

        return currentArray[random.nextInt(currentArray.length)];
    }

    public String[] getVorArray(){
        return this.vorArray;
    }
    public String[] getMiddleArray(){
        return this.middleArray;
    }
    public String[] getNachArray(){
        return this.nachArray;
    }


    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }
}
