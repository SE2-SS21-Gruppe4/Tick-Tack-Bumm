package se2.ticktackbumm.core.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import se2.ticktackbumm.core.TickTackBummGame;

public class Explosion {
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private Animation<TextureRegion> explosionAnimation;
    private float animationTime = 0.0f;

    public Explosion() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.assetManager = game.getManager();
        assetManager.load("explosion.atlas",TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get("explosion.atlas", TextureAtlas.class);
        explosionAnimation = new Animation<TextureRegion>(0.1f, atlas.findRegions("explosion"), Animation.PlayMode.LOOP);
    }

    public void render(float delta, SpriteBatch batch) {
        animationTime += delta;
        TextureRegion currentFrame = explosionAnimation.getKeyFrame(animationTime);
        batch.draw(currentFrame, 20, 40, 400, 400);
    }
}
