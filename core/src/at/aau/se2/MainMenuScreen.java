package at.aau.se2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen extends ScreenAdapter {
    final TickTackBummGame game;
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture img;
    Sprite sprite;
    BitmapFont font;

    public MainMenuScreen() {
        this.game = TickTackBummGame.getTickTackBummGame();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 2000, 1000);

        batch = new SpriteBatch();
        img = new Texture("bombeStart.png");
        sprite = new Sprite(img);
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Welcome to TickTackBumm!", 300, 200);
        font.draw(batch, "Tap anywhere to begin!", 300, 150);
        batch.end();

        batch.begin();
        sprite.draw(batch);
        sprite.setCenterX(1000);
        sprite.setCenterY(550);
        sprite.rotate(1);
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
