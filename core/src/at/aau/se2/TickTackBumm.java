package at.aau.se2;

import com.badlogic.gdx.Game;

public class TickTackBumm extends Game {
    private static TickTackBumm tickTackBumm;

    public static TickTackBumm getTickTackBumm() {
        if (tickTackBumm == null) {
            tickTackBumm = new TickTackBumm();
        }
        return tickTackBumm;
    }

    @Override
    public void create() {
        setScreen(new MainMenuScreen());
    }
}
