package at.aau.se2.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import at.aau.se2.TickTackBummGame;

public class MainGameScreen extends ScreenAdapter {
    private final TickTackBummGame game;
    private final OrthographicCamera camera;
    SpriteBatch batch;
    Texture img;
    ShapeRenderer shapeRenderer;
    Sprite sprite;

    public MainGameScreen() {
        this.game = TickTackBummGame.getTickTackBummGame();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2000, 1000);

        this.batch = new SpriteBatch();
        this.img = new Texture("badlogic.jpg");
        this.shapeRenderer = new ShapeRenderer();
        this.sprite = new Sprite(img);
        sprite.setX(200);
        sprite.setY(200);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

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
