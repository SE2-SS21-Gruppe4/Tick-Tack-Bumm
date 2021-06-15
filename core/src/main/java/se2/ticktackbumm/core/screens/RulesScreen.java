package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se2.ticktackbumm.core.TickTackBummGame;

public class RulesScreen extends ScreenAdapter {
    // Button constants
    private final float BUTTON_WIDTH = 500f;
    private final float BUTTON_HEIGHT = 80f;

    // TickTackBumm resources
    private final TickTackBummGame game;
    // Scene2D UI
    private final Stage stage;
    private final Skin skin;
    private final TextButton backToMenuButton;
    private final TextButton quitButton;
    private final Table menuButtonTable;
    private final Image backgroundImage;
    private final Texture textrueImage;
    private final AssetManager assetManager;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public RulesScreen() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.camera = TickTackBummGame.getGameCamera();
        this.batch = game.getBatch();
        this.assetManager = game.getManager();

        //scene2d UI
        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

//MenuButtons
        backToMenuButton = new TextButton("Back to Menu", skin);
        quitButton = new TextButton("QUIT GAME", skin);

        backToMenuButton.getLabel().setFontScale(4);
        quitButton.getLabel().setFontScale(4);

        menuButtonTable = new Table();
        menuButtonTable.setWidth(stage.getWidth());
        menuButtonTable.setHeight(stage.getHeight());
        menuButtonTable.align(Align.bottom);


        textrueImage = assetManager.get("rulescreen.png", Texture.class);
        backgroundImage = new Image(textrueImage);

        backToMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen());
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        menuButtonTable.add(backToMenuButton).padBottom(50f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        menuButtonTable.row();
        menuButtonTable.add(quitButton).padBottom(50f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        stage.addActor(backgroundImage);
        stage.addActor(menuButtonTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);

        batch.setProjectionMatrix(camera.combined);
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
