package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import se2.ticktackbumm.core.TickTackBummGame;

/**
 * LoadingScreen is for loading all the UI-components which are used for the game
 *
 * @author Daniel Fabian Frankl
 * @version 2.0
 */
public class LoadingScreen extends ScreenAdapter implements Screen {
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private final OrthographicCamera camera;
    private final Texture image;
    private final Sprite sprite;
    private final ShapeRenderer mShapeRenderer;
    private long progress = 0;
    private long startTime = 0;

    /**
     * Class constructor.
     * init variables, load assets and add img to sprite
     */
    public LoadingScreen() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.assetManager = game.getManager();
        mShapeRenderer = new ShapeRenderer();
        startTime = TimeUtils.nanoTime();

        this.camera = TickTackBummGame.getGameCamera();

        loadAssets();

        image = assetManager.get("loadingscreen.jpg", Texture.class);
        sprite = new Sprite(image);
    }

    /**
     * load all assets
     * loads the assets from the asset folder
     */
    private void loadAssets() {
        // load skins
        assetManager.load("ui/uiskin.json", Skin.class);

        assetManager.load("bombeStart.png", Texture.class);
        assetManager.load("winnerScreen/podium.png", Texture.class);
        assetManager.load("menuScreen/background.png", Texture.class);
        assetManager.load("winnerScreen/background.png", Texture.class);
        assetManager.load("waitingScreen/background.png", Texture.class);
        assetManager.load("mainGameScreen/background.png", Texture.class);
        assetManager.load("waitingScreen/avatar.png", Texture.class);
        assetManager.load("waitingScreen/name.png", Texture.class);
        assetManager.load("waitingScreen/avatarandname.png", Texture.class);
        assetManager.load("explosion.atlas", TextureAtlas.class);
        assetManager.load("flameLoop.atlas", TextureAtlas.class);
        assetManager.load("lamb.atlas", TextureAtlas.class);
        assetManager.load("loadingscreen.jpg", Texture.class);
        assetManager.load("maxScoreBoard.png", Texture.class);
        assetManager.load("menuscreen.png", Texture.class);
        assetManager.load("rulescreen.png", Texture.class);
        assetManager.load("table.jpg", Texture.class);
        assetManager.load("table.png", Texture.class);
        assetManager.load("card/backside.png", Texture.class);
        assetManager.load("card/frontside.png", Texture.class);
        assetManager.load("score/player1.png", Texture.class);
        assetManager.load("score/player2.png", Texture.class);
        assetManager.load("score/player3.png", Texture.class);
        assetManager.load("score/player4.png", Texture.class);
        assetManager.load("spinWheelScreen/background.png", Texture.class);
        assetManager.load("bannerBackground.png", Texture.class);
        assetManager.load("bomb/bomb.png", Texture.class);
        assetManager.load("bombexplosion.png", Texture.class);

        // load player avatars for WaitingScreen
        assetManager.load("avatars/blackhaired_guy.png", Texture.class);
        assetManager.load("avatars/blackhaired_guy_picked.png", Texture.class);
        assetManager.load("avatars/blond_girl.png", Texture.class);
        assetManager.load("avatars/blond_girl_picked.png", Texture.class);
        assetManager.load("avatars/blond_guy.png", Texture.class);
        assetManager.load("avatars/blond_guy_picked.png", Texture.class);
        assetManager.load("avatars/brunette_girl.png", Texture.class);
        assetManager.load("avatars/brunette_girl_picked.png", Texture.class);

        assetManager.finishLoading();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);
        showLoadProgress();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * dispose shapeRenderer
     */
    @Override
    public void dispose() {
        mShapeRenderer.dispose();
    }

    /**
     * Print loadingbar
     * prints the loadingbar with ShapeRenderer
     * every 0,5sec the loadingbar progresses 10%,
     * if it reaches 100%, set the screen to MenuScreen
     */
    private void showLoadProgress() {
        long currentTimeStamp = TimeUtils.nanoTime();
        if (currentTimeStamp - startTime > TimeUtils.millisToNanos(5)) {
            startTime = currentTimeStamp;
            progress = progress + 1;
        }

        float progressBarWidth = ((float) TickTackBummGame.WIDTH / 100f) * progress;

        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        sprite.draw(game.getBatch());
        game.getBatch().end();

        mShapeRenderer.setProjectionMatrix(camera.combined);
        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mShapeRenderer.setColor(Color.YELLOW);
        mShapeRenderer.rect(0f, 10f, progressBarWidth, 20f);
        mShapeRenderer.end();

        if (progress == 100) {
            moveToMenuScreen();
        }
    }

    /**
     * switch Screen
     * switch the Screen from loadingScreen to menuScreen and dispose ShapeRenderer
     */
    private void moveToMenuScreen() {
        game.setScreen(new MenuScreen());
        dispose();
    }
}
