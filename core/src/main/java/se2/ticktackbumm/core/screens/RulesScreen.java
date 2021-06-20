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
/**
 * RuleScreen displays the rules of the game
 *
 * @author Daniel Frankl
 */
public class RulesScreen extends ScreenAdapter {
    private final AssetManager assetManager;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    /**
     *  Button constants
     */
    private final float BUTTON_WIDTH = 450f;
    private final float BUTTON_HEIGHT = 120f;

    /**
     *  TickTackBumm resources
     */
    private final TickTackBummGame game;

    /**
     *  Scene 2D UI
     */
    private final Stage stage;
    private final Skin skin;
    private final TextButton backToMenuButton;
    private final Table menuButtonTable;
    private final Sprite sprite;
    private final Texture backgroundTexture;

    /**
     * Class constructor
     * init variables, load skin, load images from assetmanager
     * methods call
     */
    public RulesScreen() {
        //init game constants
        this.game = TickTackBummGame.getTickTackBummGame();
        this.camera = TickTackBummGame.getGameCamera();
        this.batch = game.getBatch();
        this.assetManager = game.getManager();

        // scene2d UI
        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH + 145f, TickTackBummGame.HEIGHT + 145f));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        // init menuButton
        backToMenuButton = new TextButton("Zurueck", skin);
        backToMenuButton.getLabel().setFontScale(4);

        menuButtonTable = new Table();
        menuButtonTable.setWidth(stage.getWidth());
        menuButtonTable.setHeight(stage.getHeight());
        menuButtonTable.align(Align.bottom);

        //get background from the assetmanager
        backgroundTexture = assetManager.get("rulescreen.png", Texture.class);

        //add Buttonlistener to get back to MenuScreen
        backToMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen());
            }
        });

        menuButtonTable.add(backToMenuButton).padBottom(75f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        //init sprite and add the image
        sprite = new Sprite(backgroundTexture);
        sprite.setRegionWidth(TickTackBummGame.WIDTH);
        sprite.setRegionHeight(TickTackBummGame.HEIGHT);

        //add the table for the Button on the stage
        stage.addActor(menuButtonTable);
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
        batch.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
