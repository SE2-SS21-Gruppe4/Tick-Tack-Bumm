package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.esotericsoftware.kryonet.Server;

import se2.ticktackbumm.core.TickTackBummGame;
/**
 * LoadingScreen is for loading all the assets which are used for the game
 * @author  Daniel Fabian Frankl
 * @version 1.0
 */
public class LoadingScreen extends ScreenAdapter implements Screen {
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final Texture image;
    private final Sprite sprite;

    private long progress = 0;
    private long startTime = 0;
    private final ShapeRenderer mShapeRenderer;
    private final int screenWidth = TickTackBummGame.WIDTH;

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
        initCamera();
        this.font = game.getFont();

        loadAssets();

        image = assetManager.get("loadingscreen.jpg", Texture.class);
        sprite = new Sprite(image);
    }

    /**
     * initialize camera
     * set camera height and width to fit to the game and update camera
     */
    private void initCamera() {
        int screenHeight = TickTackBummGame.HEIGHT;
        this.camera.setToOrtho(false, screenWidth, screenHeight);
        this.camera.update();
    }
    /**
     * load all assets
     * loads the assets from the asset folder
     */
    private void loadAssets(){
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
     * every 0,5sec the loadingbar progresses 10%
     if it reaches 100%, set the screen to MenuScreen
     */
    private void showLoadProgress() {
        long currentTimeStamp = TimeUtils.nanoTime();
        if (currentTimeStamp - startTime > TimeUtils.millisToNanos(500)) {
            startTime = currentTimeStamp;
            progress = progress + 10;
        }

        float progressBarWidth = ((float) (screenWidth / 100)) * progress;

        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        sprite.draw(game.getBatch());
        game.getBatch().end();

        mShapeRenderer.setProjectionMatrix(camera.combined);
        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mShapeRenderer.setColor(Color.YELLOW);
        mShapeRenderer.rect(0, 10, progressBarWidth, 20);
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