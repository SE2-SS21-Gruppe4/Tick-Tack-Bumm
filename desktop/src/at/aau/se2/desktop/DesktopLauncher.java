package at.aau.se2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import at.aau.se2.TickTackBumm;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "TickTackBumm";
        config.width = 2000;
        config.height = 1000;

        new LwjglApplication(TickTackBumm.getTickTackBumm(), config);
    }
}
