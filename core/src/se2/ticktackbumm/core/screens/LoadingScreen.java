package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

import se2.ticktackbumm.core.client.NetworkClient;
import se2.ticktackbumm.core.TickTackBummGame;

public class LoadingScreen extends ScreenAdapter {
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private final NetworkClient networkClient;
    private final BitmapFont font;
    private final Batch batch;

    public LoadingScreen() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.networkClient = game.getNetworkClient();
        this.assetManager = game.getManager();

        this.assetManager.load("explosion.atlas", TextureAtlas.class);

        this.batch = new SpriteBatch();
        this.font = new BitmapFont();

        this.networkClient.tryConnectClient();
        this.networkClient.getMessageSender().sendSomeRequest("Here is a request!");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1.0f, 0, 1.0f, 1);

        batch.begin();
        font.draw(
                batch,
                "LOADING ASSETS...",
                (float) (Gdx.graphics.getWidth() / 2) - 100,
                (float) Gdx.graphics.getHeight() / 2
        );
        batch.end();

        if (assetManager.update()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TickTackBummGame.getTickTackBummGame().setScreen(new LaunchScreen());
        }
    }
}
