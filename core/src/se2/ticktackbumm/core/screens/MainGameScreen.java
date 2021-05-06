package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.client.NetworkClient;

public class MainGameScreen extends ScreenAdapter {
    private final TickTackBummGame game;
    private final OrthographicCamera camera;
    private final AssetManager assetManager;
    private final NetworkClient networkClient;
    private final BitmapFont font;
    private final SpriteBatch batch;
    private final Texture img;
    private final ShapeRenderer shapeRenderer;
    private final Sprite sprite;


    public MainGameScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        batch = game.getBatch();
        font = game.getFont();
        assetManager = game.getManager();
        networkClient = game.getNetworkClient();

        img = new Texture("badlogic.jpg");
        shapeRenderer = new ShapeRenderer();
        sprite = new Sprite(img);
        sprite.setX(200);
        sprite.setY(200);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
//        batch.draw(img, 50, 50);
        sprite.draw(batch);
        sprite.rotate(delta * 90);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.rect(500.0f, 500.0f, 200.0f, 200.0f);
        shapeRenderer.setColor(Color.TEAL);
        shapeRenderer.rect(300.0f, 700.0f, 200.0f, 200.0f);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(800.0f, 200.0f, 200.0f, 200.0f);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(200.0f, 500.0f, 200.0f, 200.0f);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        shapeRenderer.dispose();
    }
}
