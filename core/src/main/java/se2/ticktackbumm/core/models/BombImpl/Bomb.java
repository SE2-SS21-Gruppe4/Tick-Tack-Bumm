package se2.ticktackbumm.core.models.BombImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.esotericsoftware.minlog.Log;

import java.security.SecureRandom;
import java.util.Random;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.client.NetworkClient;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.screens.SpinWheelScreen;

public class Bomb {

    private TickTackBummGame tickTackBummGame;
    private AssetManager assetManager;

    private Stage stage;

    private NetworkClient networkClient;


    private Texture bombTexture;
    private Image bombImage;

    private BombState bombState;

    private SecureRandom radnomExplosion;
    private float explodeTime;
    private float timerToExplode;

    private Texture explosionTexture;
    private BombExplosion bombExplosion;

    private Music bombTick;

    private SpinWheelScreen spinWheelScreen;



    public Bomb(){
        tickTackBummGame = TickTackBummGame.getTickTackBummGame();
        assetManager = tickTackBummGame.getManager();
        assetManager.load("bomb/bomb.png",Texture.class);
        assetManager.finishLoading();

        stage = new Stage();

        networkClient = tickTackBummGame.getNetworkClient();

        bombTexture = assetManager.get("bomb/bomb.png",Texture.class);
        bombImage = new Image(bombTexture);

        bombState = BombState.NORMAL;

        radnomExplosion = new SecureRandom();
        explodeTime = (float)radnomExplosion.nextInt(10)+1;
        timerToExplode = 0;
        Log.info(String.valueOf(this.explodeTime));

        assetManager.load("bombexplosion.png",Texture.class);
        assetManager.finishLoading();
        explosionTexture = assetManager.get("bombexplosion.png",Texture.class);

        bombExplosion = new BombExplosion(explosionTexture,0.7f);

        bombTick = Gdx.audio.newMusic(Gdx.files.internal("bomb/bombtick.wav"));
        bombTick.setLooping(true);
        bombTick.setVolume(0.2f);

        spinWheelScreen = new SpinWheelScreen();
    }

    public enum BombState{
        NORMAL,
        EXPLODED
    }

    public void renderBomb(float delta,SpriteBatch spriteBatch){
        if (spinWheelScreen.getStart()){
            makeExplosion(delta,spriteBatch);
            drawBomb(spriteBatch);
        }
    }

    public void makeExplosion(float deltaTime,SpriteBatch spriteBatch) {

            this.timerToExplode += deltaTime;
            if (timerToExplode >= explodeTime) {
                bombExplosion.updateExplosion(deltaTime);
                if (!bombExplosion.isFinished()) {
                    bombExplosion.renderExplosion(spriteBatch, 0, Gdx.graphics.getHeight() + (float)380, 400, 400);
                }
                this.bombState = BombState.EXPLODED;
                networkClient.getClientMessageSender().sendBombExploded();
                //    restartBombSettings();
            }

    }

    public void drawBomb(SpriteBatch spriteBatch){
        if (bombState.equals(BombState.NORMAL)){
            bombTick.play();
        }
        else{
            bombTick.pause();
        }
        spriteBatch.draw(bombTexture,0,((Gdx.graphics.getHeight()))-(float)270,290,290);

    }

    public void restartBombSettings(){
        this.bombState = BombState.NORMAL;
        this.timerToExplode = 0;
        this.explodeTime = (float) radnomExplosion.nextInt(30)+1;
    }

    private float getExplodeTime(){
        return this.explodeTime;
    }
    private float getTimerToExplode(){
        return this.timerToExplode;
    }
    public void setTimerToExplode(float timerToExplode){
        this.timerToExplode = timerToExplode;
    }
    public void setExplodeTime(float explodeTime){
        this.explodeTime = explodeTime;
    }


}
