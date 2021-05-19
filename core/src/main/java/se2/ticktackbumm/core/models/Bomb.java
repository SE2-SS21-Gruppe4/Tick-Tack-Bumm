package se2.ticktackbumm.core.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import se2.ticktackbumm.core.TickTackBummGame;

public class Bomb {

    private Stage stage;

    private Texture bombTexture;
    private Image bombImage;

    public Bomb(){
        stage = new Stage();

        bombTexture = new Texture("bomb/bomb.png");
        bombImage = new Image(bombTexture);

    }

    public void drawBomb(){
        bombImage.setBounds(20,40,50,60);
        stage.addActor(bombImage);
        stage.draw();
    }


}
