package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.models.CardLayout.Card;

public class TestScreen implements Screen {

    private final TickTackBummGame game;
    private SpriteBatch batch;

    private Card card;

    public TestScreen(){
        game = TickTackBummGame.getTickTackBummGame();
        batch = game.getBatch();

        card = new Card(batch);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        card.render();
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
        card.stage.dispose();
    }
}
