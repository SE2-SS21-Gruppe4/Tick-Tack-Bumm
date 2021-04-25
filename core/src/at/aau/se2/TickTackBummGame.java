package at.aau.se2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class TickTackBummGame extends Game {
    private static TickTackBummGame tickTackBummGame;

    private TickTackBummGame() {
        super();
    }

    public static TickTackBummGame getTickTackBummGame() {
        if (tickTackBummGame == null) {
            tickTackBummGame = new TickTackBummGame();
        }
        return tickTackBummGame;
    }

    public static void setGameScreen(Screen screen) {
        getTickTackBummGame().setScreen(screen);
    }

    @Override
    public void create() {
        setScreen(new MainGameScreen());
    }
}
