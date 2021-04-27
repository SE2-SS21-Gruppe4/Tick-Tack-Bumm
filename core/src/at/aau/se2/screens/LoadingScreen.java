package at.aau.se2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import at.aau.se2.TickTackBummGame;

public class LoadingScreen extends ScreenAdapter {
    public LoadingScreen() {
        TickTackBummGame.manager.load("explosion.atlas", TextureAtlas.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (TickTackBummGame.manager.update()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TickTackBummGame.getTickTackBummGame().setScreen(new LaunchScreen());
        }
        ;
    }
}
