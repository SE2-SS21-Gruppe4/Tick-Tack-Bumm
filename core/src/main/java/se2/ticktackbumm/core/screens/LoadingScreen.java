package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.esotericsoftware.minlog.Log;

import se2.ticktackbumm.core.TickTackBummGame;

public class LoadingScreen extends ScreenAdapter implements Screen{
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final Texture image;
    private final Sprite sprite;

    private final BitmapFont bf_loadProgress;
    private long progress = 0;
    private long startTime = 0;
    private final ShapeRenderer mShapeRenderer;
    private final int screenWidth = 1080;

    public LoadingScreen() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.bf_loadProgress = new BitmapFont();
        bf_loadProgress.getData().setScale(2, 1);
        mShapeRenderer = new ShapeRenderer();
        startTime = TimeUtils.nanoTime();

        this.camera = TickTackBummGame.getGameCamera();
        initCamera();
        this.assetManager = game.getManager();
        this.font = game.getFont();

        image = new Texture("loadingscreen.jpg");
        sprite = new Sprite(image);
    }

    private void initCamera() {
        int screenHeight = 2220;
        this.camera.setToOrtho(false, screenWidth, screenHeight);
        this.camera.update();
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
        bf_loadProgress.dispose();
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
        bf_loadProgress.draw(game.getBatch(), "Loading " + progress + " / " + 100, 0, 40);
        game.getBatch().end();

        mShapeRenderer.setProjectionMatrix(camera.combined);
        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mShapeRenderer.setColor(Color.YELLOW);
        mShapeRenderer.rect(0, 10, progressBarWidth, 10);
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