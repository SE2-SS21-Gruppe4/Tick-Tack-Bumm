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

import se2.ticktackbumm.core.TickTackBummGame;

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
    private final int screenWidth = 1080;

    public LoadingScreen() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.assetManager = game.getManager();
        mShapeRenderer = new ShapeRenderer();
        startTime = TimeUtils.nanoTime();

        this.camera = TickTackBummGame.getGameCamera();
        initCamera();
        this.font = game.getFont();

        loadAllAssets();

        image = assetManager.get("loadingscreen.jpg", Texture.class);
        sprite = new Sprite(image);
    }

    private void initCamera() {
        int screenHeight = 2220;
        this.camera.setToOrtho(false, screenWidth, screenHeight);
        this.camera.update();
    }

    private void loadAllAssets() {
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

    @Override
    public void dispose() {
        mShapeRenderer.dispose();
    }

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

    private void moveToMenuScreen() {
        game.setScreen(new MenuScreen());
        dispose();
    }
}