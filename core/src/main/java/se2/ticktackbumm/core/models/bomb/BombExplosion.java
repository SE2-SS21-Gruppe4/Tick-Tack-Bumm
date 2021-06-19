package se2.ticktackbumm.core.models.bomb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class BombExplosion {

    private Animation<TextureRegion> explosionAnimation;
    private float explosionTime;
    private Sound explosionSound;

    public BombExplosion(Texture texture, float animationTime) {
        // split png from MainScreen
        TextureRegion[][] textureRegions2D = TextureRegion.split(texture, 62, 62);

        // converted in 1D array
        TextureRegion[] textureRegions1D = new TextureRegion[16];
        int index = 0;
        for (TextureRegion[] textureRegions : textureRegions2D) {
            for (int j = 0; j < textureRegions2D[0].length; j++) {
                textureRegions1D[index] = textureRegions[j];
                index++;
            }
        }

        explosionAnimation = new Animation<>(animationTime / 16, textureRegions1D);
        explosionTime = 0;

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("bomb/bombexplosion1.mp3"));
        explosionSound.play(0.1f);
    }

    public void updateExplosion(float delta) {
        explosionTime += delta;
    }

    public void renderExplosion(SpriteBatch spriteBatch, float posX, float posY, float width, float height) {
        spriteBatch.draw(explosionAnimation.getKeyFrame(explosionTime), posX, posY, width, height);
    }

    public boolean isFinished() {
        return explosionAnimation.isAnimationFinished(explosionTime);
    }

}
