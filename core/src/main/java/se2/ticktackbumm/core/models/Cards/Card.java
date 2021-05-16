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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Card{

    private String[] arraywords;

    public static Stage stage;

    private Texture backsideTexture;
    private Image backsideImage;

    private String randomWord;
    private Texture frontsideTexture;
    private Image frontsideImage;

    private Random random;

    private boolean isRevealed;

    public Card(){
        arraywords = new String[]{"CRO", "AB", "WO", "CHA",
                "ABL", "OR", "SE", "FRE", "UNC", "FL", "NG", "WER",
                "BIR", "GER", "ONS", "ACK", "EXP", "IGN", "IL"};

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

    public void render(){

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


    public String getRandomWord(){
       int randomIndex = random.nextInt(18)+1;

       return arraywords[randomIndex];
    }

    public void setActorSettings(Actor actor, float positionX, float positionY, float width, float height){
        actor.setBounds(positionX,positionY,width,height);
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
