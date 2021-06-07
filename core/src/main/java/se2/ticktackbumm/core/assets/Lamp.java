package se2.ticktackbumm.core.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import se2.ticktackbumm.core.TickTackBummGame;

public class Lamp {
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private Animation<TextureRegion> lampAnimation;
    private float animationTime = 0.0f;

    public Lamp() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.assetManager = game.getManager();

        TextureAtlas atlas = assetManager.get("lamb.atlas", TextureAtlas.class);
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
