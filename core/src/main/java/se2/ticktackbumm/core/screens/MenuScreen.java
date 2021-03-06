package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import se2.ticktackbumm.core.TickTackBummGame;

public class MenuScreen extends ScreenAdapter {

    private final AssetManager assetManager;

    // Button constants
    private final float BUTTON_WIDTH = 450f;
    private final float BUTTON_HEIGHT = 120f;

    // TickTackBumm resources
    private final TickTackBummGame game;
    private final Texture backgroundImage;
    private final Sprite sprite;

    // Scene2D UI
    private final Stage stage;
    private final Skin skin;
    private final TextButton playButton;
    private final TextButton rulesButton;
    private final TextButton exitButton;
    private final Table menuButtonTable;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public MenuScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        assetManager = game.getManager();
        camera = TickTackBummGame.getGameCamera();
        batch = game.getBatch();

        backgroundImage = assetManager.get("menuScreen/background.png", Texture.class);

        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH + 145f, TickTackBummGame.HEIGHT + 145f));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        playButton = new TextButton("Spielen", skin);
        rulesButton = new TextButton("Regeln", skin);
        exitButton = new TextButton("Spiel verlassen", skin);

        playButton.getLabel().setFontScale(4);
        rulesButton.getLabel().setFontScale(4);
        exitButton.getLabel().setFontScale(4);

        menuButtonTable = new Table();
        menuButtonTable.setWidth(stage.getWidth());
        menuButtonTable.setHeight(stage.getHeight());
        menuButtonTable.align(Align.center);
        menuButtonTable.setY(-225f);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new SpinWheelScreen()); // testing only
                game.getNetworkClient().tryConnectClient();
            }
        });

        rulesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new RulesScreen());
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        menuButtonTable.add(playButton).padBottom(75f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        menuButtonTable.row();
        menuButtonTable.add(rulesButton).padBottom(75f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        menuButtonTable.row();
        menuButtonTable.add(exitButton).padBottom(75f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        stage.addActor(menuButtonTable);

        sprite = new Sprite(backgroundImage);
        sprite.setRegionWidth(TickTackBummGame.WIDTH);
        sprite.setRegionHeight(TickTackBummGame.HEIGHT);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);

        batch.setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        sprite.draw(game.getBatch());
        game.getBatch().end();
        batch.begin();
        stage.draw();
//        explosion.render(delta, batch); // better UI position/integration
        batch.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
