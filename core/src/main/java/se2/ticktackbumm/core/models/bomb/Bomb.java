package se2.ticktackbumm.core.models.bomb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.minlog.Log;
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
    private float timeTicking;

    public Bomb() {
        game = TickTackBummGame.getTickTackBummGame();
        assetManager = game.getManager();

        bombTexture = assetManager.get("bomb/bomb.png", Texture.class);
        bombState = BombState.NORMAL;

        // explode time is going to get random explode time via client message handler
        explodeTime = 0;
        timeTicking = 0;

        explosionTexture = assetManager.get("bombexplosion.png", Texture.class);

        bombExplosion = new BombExplosion(explosionTexture, 0.7f);

        bombTick = Gdx.audio.newMusic(Gdx.files.internal("bomb/bombtick.wav"));
        bombTick.setLooping(true);
        bombTick.setVolume(0.2f);
    }

    public boolean checkExplosion() {
        timeTicking += Gdx.graphics.getDeltaTime();
        boolean bombShouldExplode = timeTicking >= explodeTime;

        if (bombShouldExplode && bombState == BombState.NORMAL) {
            explodeBomb();

            game.getNetworkClient().getClientMessageSender().sendBombExploded();
        }

        return bombShouldExplode;
    }

    public void explodeBomb() {
        stopTicking();
        bombExplosion.getExplosionSound().play(0.5f);
        bombState = BombState.EXPLODED;
    }

    public void makeExplosion(SpriteBatch spriteBatch) {
        bombExplosion.updateExplosion(Gdx.graphics.getDeltaTime());

        if (!bombExplosion.isFinished()) {
            bombExplosion.renderExplosion(spriteBatch, 0, Gdx.graphics.getHeight() - (float) 380, 400, 400);
        }
    }

    public void drawBomb(SpriteBatch spriteBatch) {
        if (bombState == BombState.NORMAL) {
            spriteBatch.draw(bombTexture, 0, Gdx.graphics.getHeight() - (float) 270, 290, 290);
        }
    }

    public void startTicking() {
        bombTick.play();
        bombTick.setLooping(true);
    }

    public void stopTicking() {
        Log.info("STOP TICKING: " + game.getLocalPlayer().getPlayerName());
        bombTick.setLooping(false);
        bombTick.stop();
    }

    public void resetBomb() {
        bombState = BombState.NORMAL;
        timeTicking = 0;
    }

    private float getExplodeTime() {
        return this.explodeTime;
    }

    public void setExplodeTime(float explodeTime) {
        this.explodeTime = explodeTime;
    }

    private float getTimeTicking() {
        return this.timeTicking;
    }

    public void setTimeTicking(float timeTicking) {
        this.timeTicking = timeTicking;
    }

    public enum BombState {
        NORMAL,
        EXPLODED
    }

}
