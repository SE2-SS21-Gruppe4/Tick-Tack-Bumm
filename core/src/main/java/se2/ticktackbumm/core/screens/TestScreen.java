package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se2.ticktackbumm.core.TickTackBummGame;

public class TestScreen implements Screen {

    private final TickTackBummGame game;
    private SpriteBatch batch;

    public TestScreen(){
        game = TickTackBummGame.getTickTackBummGame();
        batch = game.getBatch();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
