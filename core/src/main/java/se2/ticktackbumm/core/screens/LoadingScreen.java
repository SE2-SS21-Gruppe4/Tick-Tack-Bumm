package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.client.NetworkClient;

public class LoadingScreen extends ScreenAdapter {
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private final NetworkClient networkClient;
    private final BitmapFont font;
    private final Batch batch;
    private final OrthographicCamera camera;
    private final Texture image;
    private final Sprite sprite;

    public LoadingScreen() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.camera = TickTackBummGame.getGameCamera();
        this.networkClient = game.getNetworkClient();
        this.assetManager = game.getManager();
        this.batch = game.getBatch();
        this.font = game.getFont();

        image = new Texture("loadingscreen.jpg");
        sprite = new Sprite(image);

        this.assetManager.load("explosion.atlas", TextureAtlas.class);

        this.networkClient.tryConnectClient();
        this.networkClient.getClientMessageSender().sendSomeRequest("Here is a request!");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        sprite.draw(batch);
        sprite.setCenterX(540);
        sprite.setCenterY(1110);
        batch.end();

        if (assetManager.update()) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                Log.error("Thread got interrupted while sleeping: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
            TickTackBummGame.getTickTackBummGame().setScreen(new LaunchScreen());
        }
    }
}
