package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.client.NetworkClient;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.listeners.CheckButtonListener;
import se2.ticktackbumm.core.models.BombImpl.Bomb;
import se2.ticktackbumm.core.models.BombImpl.BombExplosion;
import se2.ticktackbumm.core.models.Score;
import se2.ticktackbumm.core.models.cards.Card;

public class MainGameScreen extends ScreenAdapter {
    private static final String LOG_TAG = "MAIN_GAME_SCREEN";

    private final TickTackBummGame game;
    private final OrthographicCamera camera;
    private final AssetManager assetManager;
    private final NetworkClient networkClient;
    private final BitmapFont font;
    private BitmapFont ttfBitmapFont;
    private final SpriteBatch batch;

    private final GameData gameData;

    private final Score score;

    private int[] playerScore;

    // scene2d UI
    private final Stage stage;
    private final Skin skin;
    private final Table textFieldTable;
    private final TextField textField;
    private final TextButton checkButton;
    private final Texture textureTable;
    private final Image imageTable;
    private final Texture textureMaxScoreBoard;
    private final Image imageMaxScoreBoard;

    private final Card card;

    //Bomb and explosion
    private Bomb bomb;

    private final BitmapFont textMaxScore;
    private static final int MAX_SCORE = 10;
    private static final String MAX_SCORE_TEXT = "Max Score: " + MAX_SCORE;

    public MainGameScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        gameData = game.getGameData();
        camera = TickTackBummGame.getGameCamera();
        batch = game.getBatch();
        font = game.getFont();
        assetManager = game.getManager();
        networkClient = game.getNetworkClient();

        // maxScore
        textMaxScore = new BitmapFont();
        textMaxScore.setColor(Color.DARK_GRAY);
        textMaxScore.getData().setScale(4);
        textMaxScore.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // card
        card = new Card();

        // initialize player scores
        playerScore = gameData.getPlayerScores();

        //bomb
        bomb = new Bomb();
        assetManager.load("bombexplosion.png",Texture.class);
        assetManager.finishLoading();

        // scene2d UI
        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        skin.getFont("default-font").getData().setScale(3);
//        skin.getFont("button").getData().setScale(2);
//        skin.getFont("font").getData().setScale(4);

        textField = new TextField("", skin);
        checkButton = new TextButton("CHECK", skin);

        // player controls disabled by default
        hideControls();

        textureTable = assetManager.get("table.png", Texture.class);
        imageTable = new Image(textureTable);
        imageTable.setPosition(stage.getWidth() / 2 - 313, stage.getHeight() / 2 - 200);

        textureMaxScoreBoard = assetManager.get("maxScoreBoard.png", Texture.class);
        imageMaxScoreBoard = new Image(textureMaxScoreBoard);
        imageMaxScoreBoard.setPosition(Gdx.graphics.getWidth() / 2.0f + 25f, Gdx.graphics.getHeight() - 30f);

        score = new Score();
        score.getPlayer().get(0).setPosition(stage.getWidth() / 2 - 350, stage.getHeight() / 2 + 330);
        score.getPlayer().get(1).setPosition(stage.getWidth() / 2 + 100, stage.getHeight() / 2 + 310);
        score.getPlayer().get(2).setPosition(stage.getWidth() / 2 + 140, stage.getHeight() / 2 - 320);
        score.getPlayer().get(3).setPosition(stage.getWidth() / 2 - 350, stage.getHeight() / 2 - 330);

        textFieldTable = setupTextfieldTable();

        stage.addActor(score.getPlayer().get(0));
        stage.addActor(score.getPlayer().get(1));
        stage.addActor(score.getPlayer().get(2));
        stage.addActor(score.getPlayer().get(3));

        stage.addActor(imageTable);
        stage.addActor(imageMaxScoreBoard);
        stage.addActor(textFieldTable);

        // TODO: testing only, next round
        TextButton bombButton = new TextButton("BOMB", skin);
        bombButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                networkClient.getClientMessageSender().sendBombExploded();
            }
        });
        bombButton.setHeight(125);
        bombButton.setWidth(200);
        bombButton.setPosition(100, 200, Align.center);
        stage.addActor(bombButton);
    }

    private Table setupTextfieldTable() {
        final Table textFieldTable;
        textFieldTable = new Table();
        textFieldTable.setWidth(stage.getWidth());
        textFieldTable.align(Align.center | Align.bottom);

        textField.setAlignment(Align.center);
        checkButton.addListener(new CheckButtonListener(this));

        textFieldTable.add(textField).padBottom(20f).width(600f).height(125f);
        textFieldTable.row();
        textFieldTable.add(checkButton).padBottom(20f).width(350f).height(100f);

        return textFieldTable;
    }

    private void createTTF() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/JetBrainsMono-Medium.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 200;

        ttfBitmapFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public void hideControls() {
        textField.setVisible(false);
        textField.setDisabled(true);
        checkButton.setVisible(false);
        checkButton.setDisabled(true);
    }

    public void showControls() {
        textField.setVisible(true);
        textField.setDisabled(false);
        checkButton.setVisible(true);
        checkButton.setDisabled(false);
    }

    public void updatePlayerScores() {
        playerScore = gameData.getPlayerScores();
    }

    public void updateCurrentPlayerMarker() {
        // TODO: update marker for current player turn (red font, icon, ...)?
    }

    public void resetCard() {
        Log.info(LOG_TAG, "Reset card, show backside, pick random task text");
        // TODO: show card backside again
        // TODO: set new random syllable for card frontside
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //for when the multiplayer mode works
        //score.getBitmaps().get(0).draw(batch, Integer.toString(playerScore[0]), stage.getWidth() / 2 - 250, stage.getHeight() / 2 + 600);
        //score.getBitmaps().get(1).draw(batch, Integer.toString(playerScore[1]), stage.getWidth() / 2 + 250, stage.getHeight() / 2 + 600);
        //score.getBitmaps().get(2).draw(batch, Integer.toString(playerScore[2]), stage.getWidth() / 2 + 250, stage.getHeight() / 2 - 330);
        //score.getBitmaps().get(3).draw(batch, Integer.toString(playerScore[3]), stage.getWidth() / 2 - 250, stage.getHeight() / 2 - 375);
        score.getBitmaps().get(0).draw(batch, String.valueOf(playerScore[0]), stage.getWidth() / 2 - 250, stage.getHeight() / 2 + 600);
        score.getBitmaps().get(1).draw(batch, String.valueOf(playerScore[1]), stage.getWidth() / 2 + 250, stage.getHeight() / 2 + 600);
        score.getBitmaps().get(2).draw(batch, "8", stage.getWidth() / 2 + 250, stage.getHeight() / 2 - 330);
        score.getBitmaps().get(3).draw(batch, "1", stage.getWidth() / 2 - 250, stage.getHeight() / 2 - 375);

        bomb.renderBomb(delta,batch);

        stage.draw();
        card.draw();

        textMaxScore.draw(batch, MAX_SCORE_TEXT, Gdx.graphics.getWidth() / 2.0f + 95f, Gdx.graphics.getHeight() - 55f);
        batch.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public TextField getTextField() {
        return textField;
    }

    public TextButton getCheckButton() {
        return checkButton;
    }
}
