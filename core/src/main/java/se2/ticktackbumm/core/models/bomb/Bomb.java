package se2.ticktackbumm.core.models.bomb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se2.ticktackbumm.core.TickTackBummGame;

public class Bomb {

    private TickTackBummGame game;
    private AssetManager assetManager;

    private Texture bombTexture;
    private BombState bombState;
    private Texture explosionTexture;
    private BombExplosion bombExplosion;
    private Music bombTick;
    private float explodeTime;
    private float timerToExplode;

    public Bomb() {
        game = TickTackBummGame.getTickTackBummGame();
        assetManager = game.getManager();

        bombTexture = assetManager.get("bomb/bomb.png", Texture.class);
        bombState = BombState.NORMAL;

        // explode time is going to get random explode time via client message handler
        explodeTime = 0;
        timerToExplode = 0;

        explosionTexture = assetManager.get("bombexplosion.png", Texture.class);

        bombExplosion = new BombExplosion(explosionTexture, 0.7f);

        bombTick = Gdx.audio.newMusic(Gdx.files.internal("bomb/bombtick.wav"));
        bombTick.setLooping(true);
        bombTick.setVolume(0.2f);
    }

    public void makeExplosion(SpriteBatch spriteBatch) {
        stopTicking();
        timerToExplode += Gdx.graphics.getDeltaTime();
        if (timerToExplode >= explodeTime) {
            bombExplosion.updateExplosion(Gdx.graphics.getDeltaTime());

            if (!bombExplosion.isFinished()) {
                bombExplosion.renderExplosion(spriteBatch, 0, Gdx.graphics.getHeight() - (float) 380, 400, 400);
            }

            this.bombState = BombState.EXPLODED;
        }
    }

    public void drawBomb(SpriteBatch spriteBatch) {
        if (bombState == BombState.NORMAL) {
            spriteBatch.draw(bombTexture, 0, Gdx.graphics.getHeight() - (float) 270, 290, 290);
        }
    }

    public void startTicking() {
        bombTick.play();
    }

    public void stopTicking() {
        bombTick.stop();
    }

    public void resetBomb() {
        bombState = BombState.NORMAL;
        timerToExplode = 0;
        stopTicking();
    }

    private float getExplodeTime() {
        return this.explodeTime;
    }

    public void setExplodeTime(float explodeTime) {
        this.explodeTime = explodeTime;
    }

    private float getTimerToExplode() {
        return this.timerToExplode;
    }

    public void setTimerToExplode(float timerToExplode) {
        this.timerToExplode = timerToExplode;
    }

    public enum BombState {
        NORMAL,
        EXPLODED
    }

}
