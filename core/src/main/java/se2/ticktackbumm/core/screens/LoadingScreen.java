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
import se2.ticktackbumm.core.data.Avatars;

public class LoadingScreen extends ScreenAdapter implements Screen {
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private final OrthographicCamera camera;
    private final Texture image;
    private final Sprite sprite;

    private long progress = 0;
    private long startTime;
    private final ShapeRenderer mShapeRenderer;

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

    private void loadAssets() {
        // load skins
        assetManager.load("ui/uiskin.json", Skin.class);

        assetManager.load("bombeStart.png", Texture.class);
        assetManager.load("explosion.atlas", TextureAtlas.class);
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

        // load player avatars for WaitingScreen
        assetManager.load("avatars/square_blue.png", Texture.class);
        assetManager.load("avatars/square_green.png", Texture.class);
        assetManager.load("avatars/square_red.png", Texture.class);
        assetManager.load("avatars/square_yellow.png", Texture.class);

        assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);
        showLoadProgress();
    }

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
        if (currentTimeStamp - startTime > TimeUtils.millisToNanos(500)) {
            startTime = currentTimeStamp;
            progress = progress + 10;
        }

        float progressBarWidth = (TickTackBummGame.WIDTH / 100f) * progress;

        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        sprite.draw(game.getBatch());
        game.getBatch().end();

        mShapeRenderer.setProjectionMatrix(camera.combined);
        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mShapeRenderer.setColor(Color.YELLOW);
        mShapeRenderer.rect(0f, 10f, progressBarWidth, 20f);
        mShapeRenderer.end();

        if (progress == 10) { // TODO: testing only -> 10
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
