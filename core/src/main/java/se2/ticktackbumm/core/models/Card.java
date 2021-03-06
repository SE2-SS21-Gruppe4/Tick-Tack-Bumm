package se2.ticktackbumm.core.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.data.GameMode;

import java.security.SecureRandom;

public class Card {
    private final String[] prefixArray = new String[]{"GE", "SCH", "ANG", "FR", "VOR", "SE"};
    private final String[] infixArray = new String[]{"UT","LEN", "TRA", "WEI", "AC", "AU"};
    private final String[] postfixArray = new String[]{"SCH", "UNG", "ER", "ICH", "CHE", "EIT"};
    private final Texture backsideTexture;
    private final Texture frontsideTexture;
    private GameData gameData;
    private GameMode gameMode;
    private boolean isRevealed;

    private BitmapFont font;
    private String randomWord;

    private AssetManager assetManager;
    private TickTackBummGame game;

    private Sprite frontSprite;
    private Sprite backSprite;

    private OrthographicCamera camera;

    private boolean sentToServer;

    public Card(String str) {
        game = null;
        gameData = null;
        gameMode = null;
        backSprite = null;
        backsideTexture = null;
        frontsideTexture = null;
        font = null;
        assetManager = null;
        frontSprite = null;
        camera = null;
    }

    public Card() {
        game = TickTackBummGame.getTickTackBummGame();
        assetManager = game.getManager();

        gameData = TickTackBummGame.getTickTackBummGame().getGameData();
        gameMode = gameData.getCurrentGameMode();

        isRevealed = false;

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(5);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        backsideTexture = assetManager.get("card/backside.png", Texture.class);

        backSprite = new Sprite(backsideTexture);

        frontsideTexture = assetManager.get("card/frontside.png", Texture.class);

        randomWord = getWordDependOnMode();

        frontSprite = new Sprite(frontsideTexture);
        frontSprite.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f + 75f, 400, 200);

        camera = TickTackBummGame.getGameCamera();

        sentToServer = false;
    }

    public void drawCard(SpriteBatch spriteBatch) {
        if (game.isLocalPlayerTurn()) {
            handleCardTouch();
        }
        if (!isRevealed) {
            drawBackSide(spriteBatch);
            sentToServer = false;
        } else {
            drawFrontSide(spriteBatch);
        }
    }

    public void handleCardTouch() {
        Vector3 touchPoint = new Vector3();

        if (Gdx.input.isTouched()) {
            touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPoint);
            if (touchPoint.x >= this.backSprite.getX() && touchPoint.x <= this.backSprite.getX() + this.backSprite.getWidth()) {
                if (touchPoint.y > this.backSprite.getY() && touchPoint.y < this.backSprite.getY() + this.backSprite.getHeight()) {
                    if (!isRevealed) {
                        isRevealed = true;
                        sendMessageToServer();
                    }
                }
            }
        }
    }

    public void drawBackSide(SpriteBatch spriteBatch) {
        backSprite.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f + 75f, 400, 200);
        backSprite.draw(spriteBatch);
    }

    public void drawFrontSide(SpriteBatch spriteBatch) {
        frontSprite.setBounds(Gdx.graphics.getWidth() / 2.0f - 200, Gdx.graphics.getHeight() / 2.0f + 75f, 400, 200);
        frontSprite.draw(spriteBatch);
        font.draw(spriteBatch, randomWord, Gdx.graphics.getWidth() / 2.0f - 125, Gdx.graphics.getHeight() / 2.0f + 225f);
    }

    public void sendMessageToServer() {
        if (!sentToServer) {
            this.game.getNetworkClient().getClientMessageSender().sendCardOpened(randomWord);
            sentToServer = true;
        }
    }

    public String getWordDependOnMode() {
        switch (gameMode) {
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

    public Sprite getFrontSprite() {
        return frontSprite;
    }

    public void setFrontSprite(Sprite frontSprite) {
        this.frontSprite = frontSprite;
    }

    public Sprite getBackSprite() {
        return backSprite;
    }

    public void setBackSprite(Sprite backSprite) {
        this.backSprite = backSprite;
    }

    public String[] getPrefixArray() {
        return this.prefixArray;
    }

    public String[] getInfixArray() {
        return this.infixArray;
    }

    public String[] getPostfixArray() {
        return this.postfixArray;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameData getGameData() {
        return this.gameData;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }
}
