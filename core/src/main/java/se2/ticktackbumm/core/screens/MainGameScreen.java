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
import se2.ticktackbumm.core.models.Score;
import se2.ticktackbumm.core.models.bomb.Bomb;
import se2.ticktackbumm.core.models.cards.Card;

public class MainGameScreen extends ScreenAdapter {

    private static final String LOG_TAG = "MAIN_GAME_SCREEN";
    private static final int MAX_SCORE = 4;
    private static final String MAX_SCORE_TEXT = "Maximalpunkte: " + MAX_SCORE;
    private static final String MODE_TAG = "Spielmodus: ";
    private String waitingForWheelText = "Warten auf neuen Spielmodus...";

    private final TickTackBummGame game;
    private final OrthographicCamera camera;
    private final AssetManager assetManager;
    private final NetworkClient networkClient;
    private final BitmapFont font;
    private final SpriteBatch batch;

    // Game Mode & Banner
    private final GameData gameData;
    private final Score score;

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

    // tables for the player scores and avatars
    private final Table score1Table;
    private final Table score2Table;
    private final Table score3Table;
    private final Table score4Table;
    private final Card card;
    private final BitmapFont textMaxScore;
    private final Label player1;
    private final Label player2;
    private final Label player3;
    private final Label player4;
    private Label infoLabel;
    private int[] playerScore;
    private BitmapFont ttfBitmapFont;

    // bomb and explosion
    private Bomb bomb;

    // bomb visibility
    private boolean showBomb;

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

        // init tables for scores
        score1Table = new Table();
        score1Table.setWidth(200);
        score1Table.setHeight(400);
        score1Table.align(Align.center);

        player1 = new Label(gameData.getPlayers().get(0).getPlayerName(), skin);
        player2 = new Label(gameData.getPlayers().get(1).getPlayerName(), skin);
        player3 = new Label(gameData.getPlayers().get(1).getPlayerName(), skin);
        player4 = new Label(gameData.getPlayers().get(1).getPlayerName(), skin);

        score2Table = new Table();
        score2Table.setWidth(200);
        score2Table.setHeight(400);
        score2Table.align(Align.center);

        score3Table = new Table();
        score3Table.setWidth(200);
        score3Table.setHeight(400);
        score3Table.align(Align.center);

        score4Table = new Table();
        score4Table.setWidth(200);
        score4Table.setHeight(400);
        score4Table.align(Align.center);

        initTable1Unfocused();
        initTable2Unfocused();
        initTable3Unfocused();
        initTable4Unfocused();

        textFieldTable = setupTextfieldTable();

        // Game Mode & Banner Init
        infoLabel = new Label(waitingForWheelText, skin);
        infoLabel.setWrap(true);
        infoLabel.setAlignment(Align.center);
        infoLabel.setColor(Color.WHITE);
        infoLabel.setPosition(Gdx.graphics.getWidth() / 2f - infoLabel.getWidth() / 2f, Gdx.graphics.getHeight() - 1400f);
        infoLabel.setFontScale(4f);

        stage.addActor(imageTable);
        stage.addActor(score1Table);
        stage.addActor(score2Table);
        //stage.addActor(score3Table);
        //stage.addActor(score4Table);
        stage.addActor(imageMaxScoreBoard);
        stage.addActor(textFieldTable);
        stage.addActor(infoLabel);

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
        switch (gameData.getPlayers().get(gameData.getCurrentPlayerTurnIndex()).getPlayerId()) {
            case 0:
                initTable1Focused();
                initTable2Unfocused();
                initTable3Unfocused();
                initTable4Unfocused();
                break;
            case 1:
                initTable1Unfocused();
                initTable2Focused();
                break;
            case 2:
                initTable2Unfocused();
                initTable3Focused();
                break;
            case 3:
                initTable3Unfocused();
                initTable4Focused();
                break;
        }
    }

    public void handleBombDraw(SpriteBatch spriteBatch) {
        if (showBomb) {
            bomb.makeExplosion(spriteBatch);
            bomb.drawBomb(spriteBatch);
        }
    }

    public void resetCard() {
        Log.info(LOG_TAG, "Reset card, show backside, pick random task text");

        card.setRevealed(false);

        String newRandomWord = card.getWordDependOnMode();
        card.setRandomWord(newRandomWord);
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

//        handleBombDraw(batch);
        stage.draw();
        card.drawCard(batch);

        textMaxScore.draw(batch, MAX_SCORE_TEXT, Gdx.graphics.getWidth() / 2.0f + 57f, Gdx.graphics.getHeight() + 70f);

        batch.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public void initTable1Unfocused() {
        score1Table.reset();
        score1Table.add(score.getPlayer1());
        score1Table.row();
        score1Table.add(gameData.getUnfocusedAvatarImage(gameData.getPlayers().get(0)));
        score1Table.row();
        score1Table.add(player1);
        score1Table.setPosition(stage.getWidth() / 2 - 400, stage.getHeight() / 2 + 300);
    }

    public void initTable2Unfocused() {
        score2Table.reset();
        score2Table.add(score.getPlayer2());
        score2Table.row();
        score2Table.add(gameData.getUnfocusedAvatarImage(gameData.getPlayers().get(1)));
        score2Table.row();
        score2Table.add(player2);
        score2Table.setPosition(stage.getWidth() / 2 + 200, stage.getHeight() / 2 + 300);
    }

    public void initTable3Unfocused() {
        score3Table.reset();
        score3Table.add(score.getPlayer3());
        score3Table.row();
        score3Table.add(gameData.getUnfocusedAvatarImage(gameData.getPlayers().get(1)));
        score3Table.row();
        score3Table.add(player3);
        score3Table.setPosition(stage.getWidth() / 2 + 200, stage.getHeight() / 2 - 350);
    }

    public void initTable4Unfocused() {
        score4Table.reset();
        score4Table.add(score.getPlayer4());
        score4Table.row();
        score4Table.add(gameData.getUnfocusedAvatarImage(gameData.getPlayers().get(1)));
        score4Table.row();
        score4Table.add(player4);
        score4Table.setPosition(stage.getWidth() / 2 - 400, stage.getHeight() / 2 - 350);
    }

    public void initTable1Focused() {
        score1Table.reset();
        score1Table.add(score.getPlayer1());
        score1Table.row();
        score1Table.add(gameData.getFocusedAvatarImage(gameData.getPlayers().get(gameData.getCurrentPlayerTurnIndex())));
        score1Table.row();
        score1Table.add(player1);
        score1Table.setPosition(stage.getWidth() / 2 - 400, stage.getHeight() / 2 + 300);
    }

    public void initTable2Focused() {
        score2Table.reset();
        score2Table.add(score.getPlayer2());
        score2Table.row();
        score2Table.add(gameData.getFocusedAvatarImage(gameData.getPlayers().get(gameData.getCurrentPlayerTurnIndex())));
        score2Table.row();
        score2Table.add(player2);
        score2Table.setPosition(stage.getWidth() / 2 + 200, stage.getHeight() / 2 + 300);
    }

    public void initTable3Focused() {
        score3Table.reset();
        score3Table.add(score.getPlayer3());
        score3Table.row();
        score3Table.add(gameData.getFocusedAvatarImage(gameData.getPlayers().get(gameData.getCurrentPlayerTurnIndex())));
        score3Table.row();
        score3Table.add(player3);
        score3Table.setPosition(stage.getWidth() / 2 + 200, stage.getHeight() / 2 + 300);
    }

    public void initTable4Focused() {
        score4Table.reset();
        score4Table.add(score.getPlayer4());
        score4Table.row();
        score4Table.add(gameData.getFocusedAvatarImage(gameData.getPlayers().get(gameData.getCurrentPlayerTurnIndex())));
        score4Table.row();
        score4Table.add(player4);
        score4Table.setPosition(stage.getWidth() / 2 + 200, stage.getHeight() / 2 + 300);
    }

    public void updatePlayerScores() {
        score.setPlayerScore(gameData.getPlayerScores());
    }

    public void setWinnerScreen() {
        updatePlayerScores();
        game.getNetworkClient().disconnectClient();
        game.setLocalPlayer(null);

        Log.info(LOG_TAG, "Disconnected player from server and deleted local player from game; " +
                "switching to MenuScreen");
        Gdx.app.postRunnable(() -> game.setScreen(new WinnerScreen()));
    }

    public void updateInfoLabel() {
        switch (gameData.getCurrentGameMode()) {
            case PREFIX:
                infoLabel.setText(MODE_TAG + "TICK");
                break;

            case INFIX:
                infoLabel.setText(MODE_TAG + "TICK...TACK");
                break;

            case POSTFIX:
                infoLabel.setText(MODE_TAG + "BOMBE");
                break;
        }
    }

    public void setInfoLabelToWaiting() {
        infoLabel.setText(waitingForWheelText);
    }

    public void updateCardWord(String cardWord) {
        card.setRandomWord(cardWord);
    }

    public void updateBombTime(float bombTimer) {
        bomb.setExplodeTime(bombTimer);
    }

    // basic getters & setters
    public TextField getTextField() {
        return textField;
    }

    public TextButton getCheckButton() {
        return checkButton;
    }

    public Bomb getBomb() {
        return this.bomb;
    }

    public Card getCard() {
        return this.card;
    }

    public void setShowBomb(boolean showBomb) {
        this.showBomb = showBomb;
    }
}
