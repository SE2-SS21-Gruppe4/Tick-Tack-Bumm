package se2.ticktackbumm.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import se2.ticktackbumm.core.TickTackBummGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "TickTackBumm";
        config.width = TickTackBummGame.WIDTH;
        config.height = TickTackBummGame.HEIGHT;

        new LwjglApplication(TickTackBummGame.getTickTackBummGame(), config);
    }
}
