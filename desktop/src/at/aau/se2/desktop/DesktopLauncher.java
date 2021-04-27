package at.aau.se2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import at.aau.se2.TickTackBummGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxHeight = 4096;
        settings.maxWidth = 4096;
        settings.edgePadding = true;
        settings.duplicatePadding = true;
        settings.silent = true;
        settings.filterMin = Texture.TextureFilter.Linear;
        settings.filterMag = Texture.TextureFilter.Linear;
        TexturePacker.process(settings, "android/assets/explosion", "android/assets", "explosion");

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "TickTackBumm";
        config.width = 2000;
        config.height = 1000;

        new LwjglApplication(TickTackBummGame.getTickTackBummGame(), config);
    }
}
