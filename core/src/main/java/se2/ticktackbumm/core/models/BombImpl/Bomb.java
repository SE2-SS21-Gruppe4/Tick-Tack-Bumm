package se2.ticktackbumm.core.models.BombImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.esotericsoftware.minlog.Log;

import java.util.ListIterator;
import java.util.Random;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.assets.Explosion;

public class Bomb {

    private TickTackBummGame tickTackBummGame;
    private AssetManager assetManager;

    private Stage stage;

    private Texture bombTexture;
    private Image bombImage;

    private BombState bombState;

    private Random radnomExplosion;
    private float explodeTime;
    private float timerToExplode;

    private Texture explosionTexture;
    private BombExplosion bombExplosion;



    public Bomb(){
        tickTackBummGame = TickTackBummGame.getTickTackBummGame();
        assetManager = tickTackBummGame.getManager();
        assetManager.load("bomb/bomb.png",Texture.class);
        assetManager.finishLoading();

        stage = new Stage();

      //  bombTexture = new Texture("bomb/bomb.png");
        bombTexture = assetManager.get("bomb/bomb.png",Texture.class);
        bombImage = new Image(bombTexture);

        bombState = BombState.NORMAL;

        radnomExplosion = new Random();
        explodeTime = (float)radnomExplosion.nextInt(10)+1;
        timerToExplode = 0;
        Log.info(String.valueOf(this.explodeTime));

        assetManager.load("bombexplosion.png",Texture.class);
        assetManager.finishLoading();
        explosionTexture = assetManager.get("bombexplosion.png",Texture.class);

        bombExplosion = new BombExplosion(explosionTexture,0.7f);
    }

    public enum BombState{
        NORMAL,
        EXPLODED
    }

    public void renderBomb(float delta,SpriteBatch spriteBatch){
        makeExplosion(delta,spriteBatch);
        spriteBatch.draw(bombTexture,10,((Gdx.graphics.getHeight()))-270,290,290);
    }

    public void makeExplosion(float deltaTime,SpriteBatch spriteBatch) {
        this.timerToExplode += deltaTime;
        if (timerToExplode >= explodeTime) {
            bombExplosion.updateExplosion(deltaTime);
            if (!bombExplosion.isFinished()) {
                bombExplosion.renderExplosion(spriteBatch,10,Gdx.graphics.getHeight()+390,400,400);
            }
            this.bombState = BombState.EXPLODED;
            restartBombSettings();
        }
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
