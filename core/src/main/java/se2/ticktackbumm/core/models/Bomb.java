package se2.ticktackbumm.core.models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import se2.ticktackbumm.core.TickTackBummGame;

public class Bomb extends Actor {

    private TickTackBummGame game;
    private AssetManager assetManager;
    private Texture bombTexture;
    private Image bombImage;

    public Bomb(){
        game = TickTackBummGame.getTickTackBummGame();

        assetManager = game.getManager();
        assetManager.load("assets/bomb/bomb.png",Texture.class);
        assetManager.finishLoading();

        bombTexture = assetManager.get("assets/bomb/bomb.png",Texture.class);
        bombImage = new Image(bombTexture);
    }


}
