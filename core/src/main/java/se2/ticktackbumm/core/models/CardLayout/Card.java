package se2.ticktackbumm.core.models.CardLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Card {
    private ArrayList<String> carddeck;

    public Stage stage;

    private Texture backside_texture;
    private Image backside_image;

    private String ranodmWord;


    private boolean isRevealed;

    public Card(SpriteBatch batch){

        carddeck = getCardFromEnum();

        isRevealed = false;

        //backside
        backside_texture = new Texture("backside.jpg");
        backside_image = new Image(backside_texture);

        //font side
        ranodmWord = getRandomWord();


        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

    }

    public void render(){
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                openCard();
            }
            drawBackSide();


    }


    public void drawBackSide(){
        setActorSettings(backside_image,0,0,170,240);
        stage.addActor(backside_image);

        stage.draw();
    }
    public void drawFontSide(){
        Label cardword_label = new Label(ranodmWord,new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Image background_image = new Image(new Texture("frontside.png"));


        setActorSettings(background_image,0,0,170,240);
        setActorSettings(cardword_label,((background_image.getX()+background_image.getWidth())/2)-(cardword_label.getWidth()/2),((background_image.getImageY()+background_image.getHeight())/2)-(cardword_label.getHeight()/2),50,27);

        stage.addActor(background_image);
        stage.addActor(cardword_label);

        stage.draw();

    }


    public void openCard(){
      /*  if (!isRevealed){
            isRevealed = true;
            this.ranodmWord = getRandomWord();
        }*/
        this.ranodmWord = getRandomWord();
        drawFontSide();
    }

    public void coverCard(){
        if (isRevealed){
            isRevealed = false;
        }
    }

    public ArrayList<String> getCardFromEnum(){
        ArrayList<String> words = new ArrayList<>();

        for (CardWords cardWord : CardWords.values()){
            words.add(cardWord.getWord());
        }
        return words;
    }

    public String getRandomWord(){
        Collections.shuffle(carddeck);
        return carddeck.get(0);
    }

    public void setActorSettings(Actor actor, float positionX, float positionY, float width, float height){
        actor.setBounds(positionX,positionY,width,height);
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }
}