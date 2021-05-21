package se2.ticktackbumm.core.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import se2.ticktackbumm.core.TickTackBummGame;

public class Bomb {

    private TickTackBummGame tickTackBummGame;
    private AssetManager assetManager;

    private Stage stage;

    private Texture bombTexture;
    private Image bombImage;

    private BombState bombState;

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


}
