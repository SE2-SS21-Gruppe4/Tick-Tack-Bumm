package se2.ticktackbumm.core.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.Random;

import se2.ticktackbumm.core.TickTackBummGame;

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
        explodeTime = (float)radnomExplosion.nextInt(30)+1;
        timerToExplode = 0;

    }

    public enum BombState{
        NORMAL,
        EXPLODED
    }

    public void drawBomb(){
        bombImage.setBounds(20,40,80,80);
        stage.addActor(bombImage);
        stage.draw();
    }

    public void menageExplode(float delta){

    }

    public void setNewRandomTimer(){
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
