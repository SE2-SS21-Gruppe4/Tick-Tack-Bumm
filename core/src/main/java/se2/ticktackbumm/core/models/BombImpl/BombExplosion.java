package se2.ticktackbumm.core.models.BombImpl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class BombExplosion {

    private Animation<TextureRegion> explosionAnimation;
    private float explosionTime;

    private Rectangle boundsOfExplosion;

    public BombExplosion(Texture texture, Rectangle boundsOfExplosion, float animationTime){
        this.boundsOfExplosion = boundsOfExplosion;

        //split png from MainScreen
        TextureRegion[][] textureRegions2D = TextureRegion.split(texture,62,62);

        //converted in 1D array
        TextureRegion[] textureRegions1D = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i < textureRegions2D.length; i++){
            for(int j = 0; j < textureRegions2D[0].length; j++){
                textureRegions1D[index] = textureRegions2D[i][j];
                index++;
            }
        }

        explosionAnimation = new Animation<TextureRegion>(animationTime/16,textureRegions1D);
        explosionTime = 0;
    }

    public void updateExplosion(float delta){
        explosionTime += delta;
    }

    public void renderExplosion(SpriteBatch spriteBatch){
        spriteBatch.draw(explosionAnimation.getKeyFrame(explosionTime), boundsOfExplosion.x,boundsOfExplosion.y,boundsOfExplosion.width,boundsOfExplosion.height);
    }

    public boolean isFinished(){
        return explosionAnimation.isAnimationFinished(explosionTime);
    }
}
