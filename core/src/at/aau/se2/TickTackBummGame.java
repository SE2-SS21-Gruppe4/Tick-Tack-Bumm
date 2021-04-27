package at.aau.se2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

import at.aau.se2.screens.LoadingScreen;

public class TickTackBummGame extends Game {
    private static TickTackBummGame tickTackBummGame;
    public static AssetManager manager;

    public TickTackBummGame() {
        tickTackBummGame = this;
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
        manager = new AssetManager();
        setScreen(new LoadingScreen());
    }
}
