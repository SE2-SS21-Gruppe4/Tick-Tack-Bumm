package se2.ticktackbumm.core.models.cards;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.data.GameMode;

import java.security.SecureRandom;
import java.util.ArrayList;

public class Card {

  /*  //TODO delete after implementation with ArrayList
    private final String[] vorArray = new String[]{"SCH", "GE", "ANG", "VOR", "SEH", "FRE", "GER", "ACK", "EXP", "ORG"};
    private final String[] middleArray = new String[]{"TER", "UT", "RDI","LEN","ULT","TRA","AHN","KEL","SON","TEN"};
    private final String[] nachArray = new String[] {"UNG","SCH","SER","KEN","CHE","EIT","ATZ","NER","ICH","TUR"};*/

    private ArrayList<Texture> vorWordsList;
    private ArrayList<Texture> middleWordsList;
    private ArrayList<Texture> afterWordsList;

    private final GameData gameData;


    private Stage stage;

    private final Texture backsideTexture;
   // private final Image backsideImage;

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

    private Sprite fontSprite;
    private Sprite backSprite;


    private Sound openCardSound;
    private Sound cardSchufflingSound;

    public Card() {

        game = TickTackBummGame.getTickTackBummGame();
        assetManager = game.getManager();

        gameData = TickTackBummGame.getTickTackBummGame().getGameData();

        stage = new Stage();

        random = new SecureRandom();

        isRevealed = false;

        vorWordsList =getWordsFromAsset("vorWords");
        middleWordsList = getWordsFromAsset("middleWords");
        afterWordsList = getWordsFromAsset("afterWords");

        assetManager.load("card/backside.png",Texture.class);
        assetManager.finishLoading();

        backsideTexture = assetManager.get("card/backside.png",Texture.class);
       // backsideImage = new Image(backsideTexture);

        backSprite = new Sprite(backsideTexture);
        backSprite.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 500, 300);

        assetManager.load("card/frontside.png",Texture.class);
        assetManager.finishLoading();

        frontsideTexture = assetManager.get("card/frontside.png",Texture.class);
        frontsideImage = new Image(frontsideTexture);

        fontSprite = new Sprite(vorWordsList.get(0));
        fontSprite.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 500, 300);

      /*  wordFromArray = getWordsDependOnMode();

        labelSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        labelSkin.getFont("default-font").getData().setScale(3);

        cardWordLabel = new Label(getWordsDependOnMode(), labelSkin);*/

    }

    public void drawCard(SpriteBatch spriteBatch) {
        if (Gdx.input.isTouched()){
            if (isRevealed){
                isRevealed = false;
            }
            else{
                isRevealed = true;
            }
        }
        if (!isRevealed){
            fontSprite.draw(spriteBatch);
           /* this.backsideImage.setVisible(true);
            drawBackSide();*/
        }
        else{
           backSprite.draw(spriteBatch);
           // this.backsideImage.setVisible(false);*/
           // drawFront2Side();
        }

    }

  /*  public void addLabel(){
        gameData.setCurrentGameMode(GameMode.POSTFIX);
        wordFromArray = "";
        wordFromArray = getWordsDependOnMode();
        // TODO: use skin instead of LabelStyle
        cardWordLabel = new Label(wordFromArray,labelSkin);
        cardWordLabel.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 200, 200);
        stage.addActor(cardWordLabel);
        stage.draw();
    }

 /*   public void drawFrontSide(){
        //TODO connect with real game mode; it is just test
        gameData.setCurrentGameMode(GameMode.INFIX);
        wordFromArray = getWordsDependOnMode();

        cardWordLabel = new Label(wordFromArray,labelSkin);

     //   cardWordLabel.setV
    }

    public void drawFront2Side(){

        this.backsideImage.setVisible(false);


        gameData.setCurrentGameMode(GameMode.POSTFIX);
        wordFromArray = "";
        wordFromArray = getWordsDependOnMode();
        // TODO: use skin instead of LabelStyle
        cardWordLabel = new Label(wordFromArray,labelSkin);


        frontsideImage.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 400, 200);
        cardWordLabel.setBounds(Gdx.graphics.getWidth() / 2.0f - 50, Gdx.graphics.getHeight() / 2.0f + 110, cardWordLabel.getWidth(), cardWordLabel.getHeight());

        stage.addActor(frontsideImage);
        stage.addActor(cardWordLabel);

        gameData.setCardRevealed(true);

      //  stage.draw();

    }

  /*  public void drawBackSide(){

        this.cardWordLabel.setVisible(false);
        this.frontsideImage.setVisible(false);


        backsideImage.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 400, 200);

        stage.addActor(backsideImage);

      //  stage.draw();
    }


  /*  public String getWordsDependOnMode(){
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

    }*/

    public ArrayList<Texture> getWordsFromAsset(String path){
        ArrayList<Texture> wordsFromAsset = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            assetManager.load("card/"+path+"/"+i+".png",Texture.class);
            assetManager.finishLoading();

            Texture wordImage = assetManager.get("card/"+path+"/"+i+".png",Texture.class);

            wordsFromAsset.add(wordImage);
        }

        return wordsFromAsset;
    }


/*//TODO delete after implementation with list
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
    }*/
}
