package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.assets.Explosion;
import se2.ticktackbumm.core.client.NetworkClient;

public class LaunchScreen extends ScreenAdapter {
    private final TickTackBummGame game;
    private final OrthographicCamera camera;
    private final AssetManager assetManager;
    private final NetworkClient networkClient;
    private final BitmapFont font;
    private final SpriteBatch batch;
    private final Texture img;
    private final Sprite sprite;
    private final Explosion explosion;


    public LaunchScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        batch = game.getBatch();
        font = game.getFont();
        assetManager = game.getManager();
        networkClient = game.getNetworkClient();

        explosion = new Explosion();
        img = new Texture("bombeStart.png");
        sprite = new Sprite(img);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Welcome to TickTackBumm!", 800, 200);
        font.draw(batch, "Tap anywhere to begin!", 800, 150);
        batch.end();

        batch.begin();
        sprite.draw(batch);
        sprite.setCenterX(1000);
        sprite.setCenterY(550);
        sprite.rotate(1);
        explosion.render(delta, batch);
        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new MainGameScreen());
            dispose();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        font.dispose();
    }
}
