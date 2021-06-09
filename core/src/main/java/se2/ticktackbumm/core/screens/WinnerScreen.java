package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.minlog.Log;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.assets.Explosion;
import se2.ticktackbumm.core.assets.Flame;
import se2.ticktackbumm.core.assets.Lamp;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.player.Player;


public class WinnerScreen extends ScreenAdapter {
    private static final String LOG_TAG = "WINNER_SCREEN";
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Skin skin;
    private final GameData gameData;
    private final Texture podium;
    private final Image podiumImage;
    private final Lamp lamp;
    private final Explosion explosion;
    private final Flame flame;

    private final Sprite sprite;

    private final Texture background;

    private final float BUTTON_WIDTH = 450f;
    private final float BUTTON_HEIGHT = 120f;

    private final TextButton menuButton;
    private final Table menuButtonTable;

    private final Player[] placedPlayers;
    private final Table[] winnerTables;


    public WinnerScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        this.assetManager = game.getManager();
        camera = TickTackBummGame.getGameCamera();
        gameData = game.getGameData();

        placedPlayers = gameData.getPlacedPlayers();

        winnerTables = new Table[3];

        lamp = new Lamp();
        explosion = new Explosion();
        flame = new Flame();

        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH + 150f, TickTackBummGame.HEIGHT + 150f));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);
        skin.getFont("default-font").getData().setScale(3);

        menuButton = new TextButton("Menue", skin);

        menuButton.getLabel().setFontScale(4);

        menuButtonTable = new Table();
        menuButtonTable.setWidth(stage.getWidth());
        menuButtonTable.setHeight(stage.getHeight());
        menuButtonTable.setPosition(TickTackBummGame.WIDTH / 2f - 540f, -1000f);

        podium = assetManager.get("winnerScreen/podium.png", Texture.class);
        podiumImage = new Image(podium);
        podiumImage.setPosition(TickTackBummGame.WIDTH / 2.0f - 315f, TickTackBummGame.HEIGHT / 2f - 400f);

        initTables(winnerTables);

        winnerTables[0].setPosition(TickTackBummGame.WIDTH / 2f - 25f, TickTackBummGame.HEIGHT / 2f);
        winnerTables[1].setPosition(TickTackBummGame.WIDTH / 2f - 275f, TickTackBummGame.HEIGHT / 2f - 200f);
        //winnerTables[2].setPosition(TickTackBummGame.WIDTH / 2f + 225f, TickTackBummGame.HEIGHT / 2f - 200f);

        menuButtonTable.add(menuButton).padBottom(50f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        stage.addActor(winnerTables[0]);
        stage.addActor(winnerTables[1]);
        //stage.addActor(winnerTables[2]);
        stage.addActor(podiumImage);
        stage.addActor(menuButtonTable);

        background = assetManager.get("winnerScreen/background.png", Texture.class);

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.postRunnable(() -> game.setScreen(new MenuScreen()));

                game.getNetworkClient().disconnectClient();
                game.setLocalPlayer(null);

                Log.info(LOG_TAG, "Disconnected player from server and deleted local player from game; " +
                        "switching to MenuScreen");
            }
        });
        sprite = new Sprite(background);
        sprite.setRegionWidth(TickTackBummGame.WIDTH);
        sprite.setRegionHeight(TickTackBummGame.HEIGHT);
    }

    public void initTables(Table[] winnerTables) {
        for (int i = 0; i < placedPlayers.length; i++) {
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

        game.getBatch().begin();
        sprite.draw(game.getBatch());
        game.getBatch().end();
        game.getBatch().begin();
        stage.draw();
        flame.render(delta, game.getBatch());
        lamp.render(delta, game.getBatch());
        game.getBatch().end();
    }
}
