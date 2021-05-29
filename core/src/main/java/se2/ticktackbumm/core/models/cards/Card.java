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
import java.util.Collection;
import java.util.Collections;

public class Card {

  /*  //TODO delete after implementation with ArrayList
    private final String[] vorArray = new String[]{"SCH", "GE", "ANG", "VOR", "SEH", "FRE", "GER", "ACK", "EXP", "ORG"};
    private final String[] middleArray = new String[]{"TER", "UT", "RDI","LEN","ULT","TRA","AHN","KEL","SON","TEN"};
    private final String[] nachArray = new String[] {"UNG","SCH","SER","KEN","CHE","EIT","ATZ","NER","ICH","TUR"};*/

    private ArrayList<Texture> vorWordsList;
    private ArrayList<Texture> middleWordsList;
    private ArrayList<Texture> afterWordsList;

    private final GameData gameData;

    private final Texture backsideTexture;

    private final Texture frontsideTexture;


    private boolean isRevealed;

    //isRevealed moved in GameData class
    //private boolean isRevealed; // TODO: card flip also represented for other players; add to game data?

    private AssetManager assetManager;
    private TickTackBummGame game;

    private Texture wordFromStack;

    private Sprite fontSprite;
    private Sprite backSprite;


    private Sound openCardSound;
    private Sound cardSchufflingSound;

    public Card() {

        game = TickTackBummGame.getTickTackBummGame();
        assetManager = game.getManager();

        gameData = TickTackBummGame.getTickTackBummGame().getGameData();
        //Only for testing
        gameData.setCurrentGameMode(GameMode.PREFIX);


        isRevealed = false;

        vorWordsList =getWordsFromAsset("vorWords");
        middleWordsList = getWordsFromAsset("middleWords");
        afterWordsList = getWordsFromAsset("afterWords");

        assetManager.load("card/backside.png",Texture.class);
        assetManager.finishLoading();

        backsideTexture = assetManager.get("card/backside.png",Texture.class);


        backSprite = new Sprite(backsideTexture);

        assetManager.load("card/frontside.png",Texture.class);
        assetManager.finishLoading();

        frontsideTexture = assetManager.get("card/frontside.png",Texture.class);

        wordFromStack = getTextureDepentOnMode();
        fontSprite = new Sprite(wordFromStack);
        fontSprite.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 500, 300);


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
        if (isRevealed){
            fontSprite.draw(spriteBatch);

        }
        else{
           drawBackSide(spriteBatch);
        }
    }

    public void drawBackSide(SpriteBatch spriteBatch){
        backSprite.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 500, 300);
        backSprite.draw(spriteBatch);
    }



   /* public void drawFontSide(SpriteBatch spriteBatch){
        Texture randomWord = getTextureDepentOnMode();

        fontSprite = new Sprite(randomWord);
        fontSprite.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 500, 300);

        fontSprite.draw(spriteBatch);

    }*/


    public Texture getTextureDepentOnMode(){
        switch (gameData.getCurrentGameMode()){
            case PREFIX:
               return getRandomWordFromList(vorWordsList);

            case INFIX:
                return getRandomWordFromList(middleWordsList);


            case POSTFIX:
              return  getRandomWordFromList(afterWordsList);

            default:
                //TODO check if in this case backside should be drawn
                return null;
        }
    }


    public Texture getRandomWordFromList(ArrayList<Texture> words){
        Collections.shuffle(words);

        return words.get(0);
    }




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


    //set method in mainscreen to open card every time on new start
    public void setWordFromStack(Texture texture){
        this.wordFromStack = wordFromStack;
    }

    public Texture getWordFromStack(){
        return this.wordFromStack;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public ArrayList<Texture> getVorWordsList() {
        return vorWordsList;
    }

    public void setVorWordsList(ArrayList<Texture> vorWordsList) {
        this.vorWordsList = vorWordsList;
    }

    public ArrayList<Texture> getMiddleWordsList() {
        return middleWordsList;
    }

    public void setMiddleWordsList(ArrayList<Texture> middleWordsList) {
        this.middleWordsList = middleWordsList;
    }

    public ArrayList<Texture> getAfterWordsList() {
        return afterWordsList;
    }

    public void setAfterWordsList(ArrayList<Texture> afterWordsList) {
        this.afterWordsList = afterWordsList;
    }

    public Sprite getFontSprite() {
        return fontSprite;
    }

    public void setFontSprite(Sprite fontSprite) {
        this.fontSprite = fontSprite;
    }

    public Sprite getBackSprite() {
        return backSprite;
    }

    public void setBackSprite(Sprite backSprite) {
        this.backSprite = backSprite;
    }
}
