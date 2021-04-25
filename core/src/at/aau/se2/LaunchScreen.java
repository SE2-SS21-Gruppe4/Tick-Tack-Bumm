package at.aau.se2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class LaunchScreen extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Sprite sprite;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("bombeStart.png");
        sprite = new Sprite(img);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(.18f, .21f, .32f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);
        sprite.setCenterX(1000);
        sprite.setCenterY(550);
        sprite.rotate(1);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}
