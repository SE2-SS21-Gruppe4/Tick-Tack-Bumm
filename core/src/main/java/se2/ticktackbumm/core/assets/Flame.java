package se2.ticktackbumm.core.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import se2.ticktackbumm.core.TickTackBummGame;

public class Flame {
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private Animation<TextureRegion> lambAnimation;
    private float animationTime = 0.0f;

    public Flame() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.assetManager = game.getManager();

        TextureAtlas atlas = assetManager.get("flameLoop.atlas", TextureAtlas.class);
        lambAnimation = new Animation<TextureRegion>(0.05f, atlas.findRegions("flameLoop"), Animation.PlayMode.LOOP);
    }

    public void render(float delta, SpriteBatch spriteBatch) {
        animationTime += delta;
        TextureRegion currentFrame = lambAnimation.getKeyFrame(animationTime);
        spriteBatch.draw(currentFrame, 80, 650, 250, 900);
        spriteBatch.draw(currentFrame, TickTackBummGame.WIDTH-190f, 650, 250, 900);
    }
}
