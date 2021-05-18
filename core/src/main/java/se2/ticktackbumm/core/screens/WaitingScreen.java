package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;

public class WaitingScreen extends ScreenAdapter {

    private final String LOG_TAG = "WAITING_SCREEN";

    // Button constants
    private final float BUTTON_WIDTH = 450f;
    private final float BUTTON_HEIGHT = 120f;

    // TickTackBumm resources
    private final TickTackBummGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    // Scene2D UI
    private final Stage stage;
    private final Skin skin;
    private final TextButton readyButton;
    private final TextButton backButton;
    private final TextButton exitButton;
    private final Table waitingButtonTable;

    public WaitingScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        batch = game.getBatch();

        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        readyButton = new TextButton("BEREIT", skin);
        backButton = new TextButton("ZURÜCK", skin);
        exitButton = new TextButton("BEENDEN", skin);

        readyButton.getLabel().setFontScale(4);
        backButton.getLabel().setFontScale(4);
        exitButton.getLabel().setFontScale(4);

        waitingButtonTable = new Table();
        waitingButtonTable.setWidth(stage.getWidth());
        waitingButtonTable.setHeight(stage.getHeight());
        waitingButtonTable.align(Align.center);

        readyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getNetworkClient().getClientMessageSender().sendPlayerReady();

                readyButton.getStyle().disabledFontColor = new Color(0, 1, 0, 1);
                readyButton.setDisabled(true);
                readyButton.clearListeners();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getNetworkClient().disconnectClient();
                game.setLocalPlayer(null);

                Log.info(LOG_TAG, "Disconnected player from server and deleted local player from game; " +
                        "switching to MainMenuScreen");

                Gdx.app.postRunnable(() -> game.setScreen(new MenuScreen()));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        waitingButtonTable.add(readyButton).padBottom(50f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        waitingButtonTable.row();
        waitingButtonTable.add(backButton).padBottom(50f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        waitingButtonTable.row();
        waitingButtonTable.add(exitButton).padBottom(50f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        stage.addActor(waitingButtonTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
