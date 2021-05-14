package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se2.ticktackbumm.core.TickTackBummGame;

public class MenuScreen extends ScreenAdapter {

    // Button constants
    private final float BUTTON_WIDTH = 350f;
    private final float BUTTON_HEIGHT = 80f;

    // TickTackBumm resources
    private final TickTackBummGame game;
    private OrthographicCamera camera;
    private Batch batch;

    // Scene2D UI
    private final Stage stage;
    private final Skin skin;
    private final TextButton playButton;
    private final TextButton rulesButton;
    private final TextButton exitButton;
    private final Table menuButtonTable;


    public MenuScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        batch = game.getBatch();

        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        playButton = new TextButton("PLAY", skin);
        rulesButton = new TextButton("RULES", skin);
        exitButton = new TextButton("EXIT", skin);

        playButton.getLabel().setFontScale(3);
        rulesButton.getLabel().setFontScale(3);
        exitButton.getLabel().setFontScale(3);

        menuButtonTable = new Table();
        menuButtonTable.setWidth(stage.getWidth());
        menuButtonTable.setHeight(stage.getHeight());
        menuButtonTable.align(Align.center);

        // TODO: player login, waiting for other player? (WaitingScreen?)
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainGameScreen());
            }
        });
        // TODO: add RulesScreen
        rulesButton.addListener(new ClickListener());
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        menuButtonTable.add(playButton).padBottom(50f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        menuButtonTable.row();
        menuButtonTable.add(rulesButton).padBottom(50f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        menuButtonTable.row();
        menuButtonTable.add(exitButton).padBottom(50f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        stage.addActor(menuButtonTable);
    }

    @Override
    public void render(float delta) {
        // TODO: Add background image
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
