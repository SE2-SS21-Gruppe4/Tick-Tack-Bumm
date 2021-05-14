package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;

public class LoadingScreen extends ScreenAdapter {
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private final BitmapFont font;
    private final Batch batch;
    private final OrthographicCamera camera;

    public LoadingScreen() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.camera = TickTackBummGame.getGameCamera();
        this.assetManager = game.getManager();
        this.batch = game.getBatch();
        this.font = game.getFont();

        this.assetManager.load("explosion.atlas", TextureAtlas.class);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1.0f, 0, 1.0f, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(
                batch,
                "LOADING ASSETS...",
                (TickTackBummGame.WIDTH / 2.0f) - 150,
                TickTackBummGame.HEIGHT / 2.0f
        );
        batch.end();

        if (assetManager.update()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Log.error("Thread got interrupted while sleeping: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
            TickTackBummGame.getTickTackBummGame().setScreen(new MenuScreen());
        }
    }
}
