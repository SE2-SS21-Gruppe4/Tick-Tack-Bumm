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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Card{
    private ArrayList<String> carddeck;

    public Stage stage;

    private Skin skin;

    private Texture backside_texture;
    private Image backside_image;

    private String ranodmWord;
    private Texture frontside_texture;
    private Image frontside_image;


    private boolean isRevealed;

    public Card(SpriteBatch batch){
        carddeck = getCardFromEnum();

        isRevealed = false;

        backside_texture = new Texture("card/backside.jpg");
        backside_image = new Image(backside_texture);

        ranodmWord = getRandomWord();
        frontside_texture = new Texture("card/frontside.png");
        frontside_image = new Image(frontside_texture);

        stage = new Stage();

    }

    public void render(){
            stage.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    openCard();
                    return true;
                }

            });
            drawBackSide();

    }

    public void drawBackSide(){
        setActorSettings(backside_image,0,0,170,240);
        stage.addActor(backside_image);

        stage.act();
        stage.draw();
    }
    public void drawFontSide(){
        Label cardword_label = new Label(ranodmWord,new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        setActorSettings(frontside_image,0,0,170,240);
        setActorSettings(cardword_label,((frontside_image.getX()+frontside_image.getWidth())/2)-(cardword_label.getWidth()/2),((frontside_image.getImageY()+frontside_image.getHeight())/2)-(cardword_label.getHeight()/2),50,27);

        stage.addActor(frontside_image);
        stage.addActor(cardword_label);

        stage.act();
        stage.draw();

        java.util.Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                frontside_image.remove();
                cardword_label.remove();
            }
        };

        timer.schedule(task,3000);
    }


    public void openCard(){
        this.ranodmWord = getRandomWord();
        drawFontSide();


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

    public ArrayList<String> getCardDeck(){
        return this.carddeck;
    }
    public void setCarddeck(ArrayList<String> carddeck){
        this.carddeck = carddeck;
    }

    public boolean isRevealed(){
        return isRevealed;
    }

    public void setRevealed(boolean isRevealed){
        this.isRevealed= isRevealed;
    }
}
