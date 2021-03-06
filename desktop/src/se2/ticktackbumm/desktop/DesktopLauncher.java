package se2.ticktackbumm.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import se2.ticktackbumm.core.TickTackBummGame;

public class DesktopLauncher {
    /**
     * load the atlas files into our file structure to create animations
     * load TickTackBummGame
     */
    public static void main(String[] arg) {
        /*TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxHeight = 4096;
        settings.maxWidth = 4096;
        settings.edgePadding = true;
        settings.duplicatePadding = true;
        settings.silent = true;
        settings.filterMin = Texture.TextureFilter.Linear;
        settings.filterMag = Texture.TextureFilter.Linear;*/
        //TexturePacker.process(settings, "android/assets/explosion", "android/assets", "explosion");
        //TexturePacker.process(settings, "android/assets/flameLoop", "android/assets", "flameLoop");
        //TexturePacker.process(settings, "android/assets/firework", "android/assets", "lamb");

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "TickTackBumm";
        config.width = TickTackBummGame.WIDTH;
        config.height = TickTackBummGame.HEIGHT;

        new LwjglApplication(TickTackBummGame.getTickTackBummGame(), config);
    }
}
