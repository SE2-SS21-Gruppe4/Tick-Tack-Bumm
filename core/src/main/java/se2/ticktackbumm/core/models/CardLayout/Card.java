package se2.ticktackbumm.core.models.CardLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.Random;

public class Card {
    private String[] cardDeck;
    private ArrayList<String> carddeck;

    public Stage stage;

    private Texture backside_texture;
    private Image backside_image;


    private int random_cardindex;
    private Random random;
    private String ranodmWord;
    String aa;

    private Label label;

    private boolean isRevealed = false;

    public Card(SpriteBatch batch){


        cardDeck = getCardDeck();

        //backside
        backside_texture = new Texture("backside.jpg");
        backside_image = new Image(backside_texture);

        //font side
        random = new Random();
        random_cardindex = random.nextInt(19-0+1)+1;
        ranodmWord = cardDeck[random_cardindex];

        aa = "AA";

        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

    }

    public void render(){
        if (!isRevealed){
            drawBackSide();
        }
        else {
            drawFontSide();
        }

    }


    public void drawBackSide(){
        setActorSettings(backside_image,0,0,170,240);
        stage.addActor(backside_image);

        stage.draw();
    }
    public void drawFontSide(){
        Label cardword_label = new Label(ranodmWord,new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        Image background_image = new Image(new Texture("backgoundcolor_font.png"));


        setActorSettings(background_image,0,0,170,240);
        setActorSettings(cardword_label,((background_image.getX()+background_image.getWidth())/2)-(cardword_label.getWidth()/2),((background_image.getImageY()+background_image.getHeight())/2)-(cardword_label.getHeight()/2),50,27);


        stage.addActor(background_image);
        stage.addActor(cardword_label);

        stage.draw();

    }


    public void openCard(){
        if (!isRevealed){
            isRevealed = true;
        }
    }

    public void coverCard(){
        if (isRevealed){
            isRevealed = false;
        }
    }

    public String[] getCardDeck(){
        String[] deck_from_enum = new String[19];
        int index = 0;

        for (CardWords word : CardWords.values()){
            deck_from_enum[index] = word.getWord();
            index++;
        }

        return deck_from_enum;
    }

    public String getCardFromDeck(int randomIndex){
        if (randomIndex >= 0 && randomIndex < this.cardDeck.length){
            return cardDeck[randomIndex];
        }
        return null;
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
