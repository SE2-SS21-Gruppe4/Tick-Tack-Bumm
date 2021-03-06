package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.assets.Flame;
import se2.ticktackbumm.core.assets.Lamp;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.player.Player;

/**
 * WinnerScreen displays the best 3 players after the maxscore is reached
 *
 * @author Daniel Frankl
 */
public class WinnerScreen extends ScreenAdapter {
    /**
     * TickTackBumm constants
     */
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final GameData gameData;
    /**
     * Scene 2D UI
     */
    private final Skin skin;
    private final Texture podium;
    private final Image podiumImage;
    private final Lamp lamp;
    private final Flame flame;
    private final Sprite sprite;
    private final Texture background;

    /**
     * Button variables
     */
    private final float BUTTON_WIDTH = 450f;
    private final float BUTTON_HEIGHT = 120f;
    private final TextButton menuButton;
    private final Table menuButtonTable;

    /**
     * Arrays for the placed players and the tables
     */
    private final Player[] placedPlayers;
    private final Table[] winnerTables;

    /**
     * Class constructor
     * init variables, load skin, load images from assetmanager, load animations
     * set tableArray and placedPlayersArray
     * methods call
     */
    public WinnerScreen() {
        //init game constants
        game = TickTackBummGame.getTickTackBummGame();
        this.assetManager = game.getManager();
        camera = TickTackBummGame.getGameCamera();
        gameData = game.getGameData();

        //init the Arrays
        placedPlayers = gameData.getPlacedPlayers();
        winnerTables = new Table[3];

        //init animations
        lamp = new Lamp();
        flame = new Flame();

        //init skin and stage
        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH + 150f, TickTackBummGame.HEIGHT + 150f));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);
        skin.getFont("default-font").getData().setScale(3);

        //init menuButton and set Scale
        menuButton = new TextButton("Menue", skin);
        menuButton.getLabel().setFontScale(4);

        //init menuButtonTable
        menuButtonTable = new Table();
        menuButtonTable.setWidth(stage.getWidth());
        menuButtonTable.setHeight(stage.getHeight());
        menuButtonTable.setPosition(TickTackBummGame.WIDTH / 2f - 540f, -1000f);

        //get the podium picture from the assetmanager
        podium = assetManager.get("winnerScreen/podium.png", Texture.class);
        podiumImage = new Image(podium);
        podiumImage.setPosition(TickTackBummGame.WIDTH / 2.0f - 315f, TickTackBummGame.HEIGHT / 2f - 400f);

        //methodcall
        initTables(winnerTables);

        //position the tables
        winnerTables[0].setPosition(TickTackBummGame.WIDTH / 2f - 25f, TickTackBummGame.HEIGHT / 2f);
        winnerTables[1].setPosition(TickTackBummGame.WIDTH / 2f - 275f, TickTackBummGame.HEIGHT / 2f - 200f);
        winnerTables[2].setPosition(TickTackBummGame.WIDTH / 2f + 225f, TickTackBummGame.HEIGHT / 2f - 200f);

        menuButtonTable.add(menuButton).padBottom(50f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        //add all actors to the stage
        stage.addActor(podiumImage);
        stage.addActor(menuButtonTable);
        stage.addActor(winnerTables[0]);
        stage.addActor(winnerTables[1]);
        stage.addActor(winnerTables[2]);

        //get the background from the assetmanager
        background = assetManager.get("winnerScreen/background.png", Texture.class);

        //add listener for the Button to get back to the MenuScreen
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.postRunnable(() -> game.setScreen(new MenuScreen()));
            }
        });
        //add the background to the sprit
        sprite = new Sprite(background);
        sprite.setRegionWidth(TickTackBummGame.WIDTH);
        sprite.setRegionHeight(TickTackBummGame.HEIGHT);
    }

    /**
     * get the best 3 players from the placedPlayers Array and adding them into the winnerTablesArray
     *
     * @param winnerTables is for the tables for the best 3 players
     */
    public void initTables(Table[] winnerTables) {
        for (int i = 0; i < placedPlayers.length - 1; i++) {
            winnerTables[i] = new Table();
            winnerTables[i].setWidth(200);
            winnerTables[i].setHeight(400);
            winnerTables[i].align(Align.center);

            Label playerScore = new Label(String.valueOf(placedPlayers[i].getGameScore()), skin);
            Label playerName = new Label(placedPlayers[i].getPlayerName(), skin);
            Image playerImage = new Image(gameData.getUnfocusedAvatarImage(gameData.getPlacedPlayers()[i]).getDrawable());

            winnerTables[i].add(playerScore);
            winnerTables[i].row();
            winnerTables[i].add(playerImage);
            winnerTables[i].row();
            winnerTables[i].add(playerName);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);
        game.getBatch().setProjectionMatrix(camera.combined);

        //render background
        game.getBatch().begin();
        sprite.draw(game.getBatch());
        game.getBatch().end();

        //render the stage and assets
        game.getBatch().begin();
        stage.draw();
        flame.render(delta, game.getBatch());
        lamp.render(delta, game.getBatch());
        game.getBatch().end();
    }
}
