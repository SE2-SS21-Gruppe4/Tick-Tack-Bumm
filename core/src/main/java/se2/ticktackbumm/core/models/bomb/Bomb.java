package se2.ticktackbumm.core.models.bomb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.client.NetworkClient;

public class Bomb {

    private TickTackBummGame tickTackBummGame;
    private AssetManager assetManager;

    private Stage stage;

    private NetworkClient networkClient;


    private Texture bombTexture;
    private Image bombImage;

    private BombState bombState;


    private float explodeTime;
    private float timerToExplode;

    private Texture explosionTexture;
    private BombExplosion bombExplosion;

    private Music bombTick;


    public Bomb() {
        tickTackBummGame = TickTackBummGame.getTickTackBummGame();
        assetManager = tickTackBummGame.getManager();

        stage = new Stage();

        networkClient = tickTackBummGame.getNetworkClient();

        bombTexture = assetManager.get("bomb/bomb.png", Texture.class);
        bombImage = new Image(bombTexture);

        bombState = BombState.NORMAL;

        //explode time is going to get random exploder via client message handler
        explodeTime = 10;
        timerToExplode = 0;
        Log.info(String.valueOf(this.explodeTime));

        explosionTexture = assetManager.get("bombexplosion.png", Texture.class);

        bombExplosion = new BombExplosion(explosionTexture, 0.7f);

        bombTick = Gdx.audio.newMusic(Gdx.files.internal("bomb/bombtick.wav"));
        bombTick.setLooping(true);
        bombTick.setVolume(0.2f);

    }

    public void makeExplosion(SpriteBatch spriteBatch) {
        this.timerToExplode += Gdx.graphics.getDeltaTime();
        if (timerToExplode >= explodeTime) {
            bombExplosion.updateExplosion(Gdx.graphics.getDeltaTime());
            if (!bombExplosion.isFinished()) {
                bombExplosion.renderExplosion(spriteBatch, 0, Gdx.graphics.getHeight() - (float) 380, 400, 400);
            }
            this.bombState = BombState.EXPLODED;

        }

    }

    public void drawBomb(SpriteBatch spriteBatch) {
        if (bombState.equals(BombState.NORMAL)) {
            bombTick.play();
            spriteBatch.draw(bombTexture, 0, ((Gdx.graphics.getHeight())) - (float) 270, 290, 290);
        } else {
            bombTick.pause();
        }
    }

    public void restartBombSettings() {
        this.bombState = BombState.NORMAL;
        this.timerToExplode = 0;
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
