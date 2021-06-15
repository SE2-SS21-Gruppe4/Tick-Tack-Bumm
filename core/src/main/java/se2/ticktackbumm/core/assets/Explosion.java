package se2.ticktackbumm.core.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import se2.ticktackbumm.core.TickTackBummGame;

public class Explosion extends Actor {
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private Animation<TextureRegion> explosionAnimation;
    private float animationTime = 0.0f;

    public Explosion() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.assetManager = game.getManager();

        TextureAtlas atlas = assetManager.get("explosion.atlas", TextureAtlas.class);
        explosionAnimation = new Animation<TextureRegion>(0.1f, atlas.findRegions("explosion"), Animation.PlayMode.LOOP);
    }

    public void render(float delta, SpriteBatch spriteBatch) {
        animationTime += delta;
        TextureRegion currentFrame = explosionAnimation.getKeyFrame(animationTime);
        spriteBatch.draw(currentFrame, 150, 500, 200, 200);
        spriteBatch.draw(currentFrame, 450, 500, 200, 200);
        spriteBatch.draw(currentFrame, 750, 500, 200, 200);
    }
}
