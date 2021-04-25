package at.aau.se2.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import at.aau.se2.TickTackBummGame;

public class Explosion {
    private Animation<TextureRegion> explosionAnimation;
    private float animationeTime = 0.0f;

    public Explosion(){
        TextureAtlas atlas = TickTackBummGame.manager.get("explosion.atlas", TextureAtlas.class);
        explosionAnimation = new Animation<TextureRegion>(0.1f, atlas.findRegions("explosion"), Animation.PlayMode.LOOP);
    }

    public void render(float delta, SpriteBatch batch){
        animationeTime += delta;
        TextureRegion currentFrame = explosionAnimation.getKeyFrame(animationeTime);
        batch.draw(currentFrame, 50, 50, 400, 400);
    }
}
