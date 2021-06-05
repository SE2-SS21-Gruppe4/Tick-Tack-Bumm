package se2.ticktackbumm.core.models.cards;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.data.GameMode;

import java.security.SecureRandom;


public class Card {


    private final String[] prefixArray = new String[]{"SCH", "GE", "ANG", "VOR", "SEH", "FRE", "GER", "ACK", "EXP", "ORG"};
    private final String[] infixArray = new String[]{"TER", "UT", "RDI", "LEN", "ULT", "TRA", "AHN", "KEL", "SON", "TEN"};
    private final String[] postfixArray = new String[]{"UNG", "SCH", "SER", "KEN", "CHE", "EIT", "ATZ", "NER", "ICH", "TUR"};

    private final GameData gameData;

    private final Texture backsideTexture;

    private final Texture frontsideTexture;

    private boolean isRevealed;

    private BitmapFont font;
    private String randomWord;


    private AssetManager assetManager;
    private TickTackBummGame game;


    private Sprite fontSprite;
    private Sprite backSprite;


    public Card() {

        game = TickTackBummGame.getTickTackBummGame();
        assetManager = game.getManager();

        gameData = TickTackBummGame.getTickTackBummGame().getGameData();
        //Only for testing
        gameData.setCurrentGameMode(GameMode.PREFIX);


        isRevealed = false;

        font = new BitmapFont();
        font.setColor(Color.CORAL);
        font.getData().setScale(7);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        assetManager.load("card/backside.png", Texture.class);
        assetManager.finishLoading();

        backsideTexture = assetManager.get("card/backside.png", Texture.class);


        backSprite = new Sprite(backsideTexture);

        assetManager.load("card/frontside.png", Texture.class);
        assetManager.finishLoading();

        frontsideTexture = assetManager.get("card/frontside.png", Texture.class);

        randomWord = getWordDependOnMode();

        fontSprite = new Sprite(frontsideTexture);
        fontSprite.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 500, 300);


    }

    public void drawCard(SpriteBatch spriteBatch) {
        handleInputProccesor();

        if (isRevealed) {
            drawFrontSide(spriteBatch);

        } else {
            drawBackSide(spriteBatch);
        }
    }

    public void handleInputProccesor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (fontSprite.getBoundingRectangle().contains(screenX, screenY) || backSprite.getBoundingRectangle().contains(screenX, screenY)) {
                    if (isRevealed) {
                        isRevealed = false;
                    } else {
                        isRevealed = true;
                    }
                }
                return true;
            }
        });
    }

    public void drawBackSide(SpriteBatch spriteBatch) {
        backSprite.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f, 500, 300);
        spriteBatch.begin();
        backSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    public void drawFrontSide(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        fontSprite.draw(spriteBatch);
        font.draw(spriteBatch, randomWord, Gdx.graphics.getWidth() / 2.0f - 28, Gdx.graphics.getHeight() / 2.0f + 203);
        spriteBatch.end();

        this.game.getNetworkClient().getClientMessageSender().sendCardOpened();
    }


    public String getWordDependOnMode() {
        switch (gameData.getCurrentGameMode()) {
            case PREFIX:
                return getRandomWord(prefixArray);

            case INFIX:
                return getRandomWord(infixArray);


            case POSTFIX:
                return getRandomWord(postfixArray);

            default:
                //TODO check if in this case backside should be drawn
                return null;
        }
    }

    public String getRandomWord(String[] words) {
        return words[new SecureRandom().nextInt(words.length)];
    }


    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public String getRandomWord() {
        return this.randomWord;
    }

    public void setRandomWord(String randomWord) {
        this.randomWord = randomWord;
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
