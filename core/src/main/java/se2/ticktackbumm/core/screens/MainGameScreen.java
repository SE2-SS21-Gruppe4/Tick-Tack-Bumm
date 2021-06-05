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

    //tables for the playerscores and avatars
    private final Table score1Table;
    private final Table score2Table;
    private final Table score3Table;
    private final Table score4Table;

    private final Card card;

    //Bomb and explosion
    private Bomb bomb;

    private final BitmapFont textMaxScore;
    private static final int MAX_SCORE = 10;
    private static final String MAX_SCORE_TEXT = "Max Score: " + MAX_SCORE;

    private final Label player1;
    private final Label player2;
    private final Label player3;
    private final Label player4;

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
        assetManager.load("bombexplosion.png", Texture.class);
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
        //init tables for scores
        score1Table = new Table();
        score1Table.setWidth(200);
        score1Table.setHeight(400);
        score1Table.align(Align.center);

        player1 = new Label(gameData.getPlayers().get(0).getPlayerName(), skin);
        player2 = new Label(gameData.getPlayers().get(1).getPlayerName(), skin);
        player3 = new Label(gameData.getPlayers().get(1).getPlayerName(), skin);
        player4 = new Label(gameData.getPlayers().get(1).getPlayerName(), skin);

        score1Table.add(score.getPlayer1());
        score1Table.row();
        score1Table.add(gameData.getUnfocusedAvatarImage(gameData.getPlayers().get(0)));
        score1Table.row();
        score1Table.add(player1);
        score1Table.setPosition(stage.getWidth() / 2 - 400, stage.getHeight() / 2 + 300);

        score2Table = new Table();
        score2Table.setWidth(200);
        score2Table.setHeight(400);
        score2Table.align(Align.center);

        score2Table.add(score.getPlayer2());
        score2Table.row();
        score2Table.add(gameData.getUnfocusedAvatarImage(gameData.getPlayers().get(1)));
        score2Table.row();
        score2Table.add(player2);
        score2Table.setPosition(stage.getWidth() / 2 + 200, stage.getHeight() / 2 + 300);

        score3Table = new Table();
        score3Table.setWidth(200);
        score3Table.setHeight(400);
        score3Table.align(Align.center);

        score3Table.add(player3);
        score3Table.row();
        score3Table.add(gameData.getUnfocusedAvatarImage(gameData.getPlayers().get(1)));
        score3Table.row();
        score3Table.add(score.getPlayer3());
        score3Table.setPosition(stage.getWidth() / 2 + 200, stage.getHeight() / 2 - 350);

        score4Table = new Table();
        score4Table.setWidth(200);
        score4Table.setHeight(400);
        score4Table.align(Align.center);

        score4Table.add(player4);
        score4Table.row();
        score4Table.add(gameData.getUnfocusedAvatarImage(gameData.getPlayers().get(1)));
        score4Table.row();
        score4Table.add(score.getPlayer4());
        score4Table.setPosition(stage.getWidth() / 2 - 400, stage.getHeight() / 2 - 350);

        textFieldTable = setupTextfieldTable();

        stage.addActor(score1Table);
        stage.addActor(score2Table);
        stage.addActor(imageTable);
        stage.addActor(score3Table);
        stage.addActor(score4Table);
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

    public void updateCurrentPlayerMarker() {
        switch(gameData.getPlayers().get(gameData.getCurrentPlayerTurnIndex()).getPlayerId()){
            case 0:
                score2Table.reset();
                score1Table.reset();

                score1Table.add(score.getPlayer1());
                score1Table.row();
                score1Table.add(gameData.getFocusedAvatarImage(gameData.getPlayers().get(gameData.getCurrentPlayerTurnIndex())));
                score1Table.row();
                score1Table.add(player1);
                score1Table.setPosition(stage.getWidth() / 2 - 400, stage.getHeight() / 2 + 300);

                score2Table.add(score.getPlayer2());
                score2Table.row();
                score2Table.add(gameData.getUnfocusedAvatarImage(gameData.getPlayers().get(1)));
                score2Table.row();
                score2Table.add(player2);
                score2Table.setPosition(stage.getWidth() / 2 + 200, stage.getHeight() / 2 + 300);
                break;
            case 1:
                score2Table.reset();
                score1Table.reset();

                score1Table.add(score.getPlayer1());
                score1Table.row();
                score1Table.add(gameData.getUnfocusedAvatarImage(gameData.getPlayers().get(0)));
                score1Table.row();
                score1Table.add(player1);
                score1Table.setPosition(stage.getWidth() / 2 - 400, stage.getHeight() / 2 + 300);

                score2Table.add(score.getPlayer2());
                score2Table.row();
                score2Table.add(gameData.getFocusedAvatarImage(gameData.getPlayers().get(gameData.getCurrentPlayerTurnIndex())));
                score2Table.row();
                score2Table.add(player2);
                score2Table.setPosition(stage.getWidth() / 2 + 200, stage.getHeight() / 2 + 300);
                break;
            case 2:
                break;
            case 3:
                break;
        }
        // TODO: update when 4 player are enabled
    }

    public void resetCard() {
        Log.info(LOG_TAG, "Reset card, show backside, pick random task text");
        card.setRevealed(false);

        String newRandomWord = card.getWordDependOnMode();
        card.setRandomWord(newRandomWord);
        gameData.setCurrentGameModeText(newRandomWord);
        card.setMessageToServer();
    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        bomb.renderBomb(delta, batch);
        stage.draw();
        card.drawCard(batch);

        textMaxScore.draw(batch, MAX_SCORE_TEXT, Gdx.graphics.getWidth() / 2.0f + 95f, Gdx.graphics.getHeight() - 55f);
        batch.end();
    }

    public void updatePlayerScores() {
        score.setPlayerScore(gameData.getPlayerScores());
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public Card getCard(){
        return card;
    }

    public TextField getTextField() {
        return textField;
    }

    public TextButton getCheckButton() {
        return checkButton;
    }

    public Bomb getBomb(){
        return this.bomb;
    }
}
