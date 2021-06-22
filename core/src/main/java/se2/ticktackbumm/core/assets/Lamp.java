package se2.ticktackbumm.core.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se2.ticktackbumm.core.TickTackBummGame;

/**
 * Flame is for creating the explosion animation
 *
 * @author Daniel Fabian Frankl
 * @version 1.0
 */
public class Lamp {
    /**
     * game constants
     */
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    /**
     * animation variables
     */
    private Animation<TextureRegion> lampAnimation;
    private float animationTime = 0.0f;

    /**
     * init game constants and get the atlas file from the assetmanager
     * create the animation
     */
    public Lamp() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.assetManager = game.getManager();

        TextureAtlas atlas = assetManager.get("lamb.atlas", TextureAtlas.class);
        //create the animation and give it a framerate
        lampAnimation = new Animation<TextureRegion>(0.2f, atlas.findRegions("lamb"), Animation.PlayMode.LOOP);
    }

    public void render(float delta, SpriteBatch spriteBatch) {
        animationTime += delta;
        TextureRegion currentFrame = lampAnimation.getKeyFrame(animationTime);
        spriteBatch.draw(currentFrame, 265, 600, 200, 200);
        spriteBatch.draw(currentFrame, 515, 600, 200, 200);
        spriteBatch.draw(currentFrame, 765, 600, 200, 200);
    }
}
