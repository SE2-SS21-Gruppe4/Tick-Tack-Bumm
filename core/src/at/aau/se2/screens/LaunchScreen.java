package at.aau.se2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import at.aau.se2.assets.Explosion;


public class LaunchScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture img;
    private Sprite sprite;
    private at.aau.se2.assets.Explosion explosion;

    public LaunchScreen(){
        explosion = new Explosion();
        batch = new SpriteBatch();
        img = new Texture("bombeStart.png");
        sprite = new Sprite(img);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(.18f, .21f, .32f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);
        sprite.setCenterX(1000);
        sprite.setCenterY(550);
        sprite.rotate(1);
        explosion.render(delta, batch);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}
