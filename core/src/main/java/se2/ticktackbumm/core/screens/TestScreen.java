package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.models.BombImpl.Bomb;

public class TestScreen implements Screen {
    private TickTackBummGame game;
    private SpriteBatch spriteBatch;

    private Bomb bomb;

    private Texture bombTexture;
    private Image bombImage;


    public TestScreen() {
        bomb = new Bomb();

        game = TickTackBummGame.getTickTackBummGame();
        spriteBatch = game.getBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        bomb.renderBomb(delta, spriteBatch);
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
