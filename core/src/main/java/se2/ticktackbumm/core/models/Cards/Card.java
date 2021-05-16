package se2.ticktackbumm.core.models.Cards;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Card{
    private List<String> carddeck;

    public static Stage stage;

    private Texture backsideTexture;
    private Image backsideImage;

    private String randomWord;
    private Texture frontsideTexture;
    private Image frontsideImage;


    private boolean isRevealed;

    public Card(){
        carddeck = getCardFromEnum();

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

    public void render(){
        /*stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openCard();
            }
        });*/

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            openCard();
        }

            drawBackSide();

    }

    public void drawBackSide(){
        setActorSettings(backsideImage,0,0,170,240);
        stage.addActor(backsideImage);

        stage.act();
        stage.draw();
    }
    public void drawFontSide(){
        Label cardwordLabel = new Label(randomWord,new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        setActorSettings(frontsideImage,0,0,170,240);
        setActorSettings(cardwordLabel,((frontsideImage.getX()+ frontsideImage.getWidth())/2)-(cardwordLabel.getWidth()/2),((frontsideImage.getImageY()+ frontsideImage.getHeight())/2)-(cardwordLabel.getHeight()/2),50,27);

        stage.addActor(frontsideImage);
        stage.addActor(cardwordLabel);

        stage.act();
        stage.draw();

        java.util.Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                frontsideImage.remove();
                cardwordLabel.remove();
            }
        };

        timer.schedule(task,3000);
    }


    public void openCard(){
        this.randomWord = getRandomWord();
        drawFontSide();
    }

    public List<String> getCardFromEnum(){
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

    public List<String> getCardDeck(){
        return this.carddeck;
    }
    public void setCarddeck(List<String> carddeck){
        this.carddeck = carddeck;
    }

    public boolean isRevealed(){
        return isRevealed;
    }

    public void setRevealed(boolean isRevealed){
        this.isRevealed= isRevealed;
    }

    public String getWord(){
        return this.randomWord;
    }



}
