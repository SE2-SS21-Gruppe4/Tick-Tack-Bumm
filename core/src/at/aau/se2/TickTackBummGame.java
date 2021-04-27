package at.aau.se2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class TickTackBummGame extends Game {
    private static TickTackBummGame tickTackBummGame;
    private AssetManager manager;

    public static TickTackBummGame getTickTackBummGame() {
        if (tickTackBummGame == null) {
            tickTackBummGame = new TickTackBummGame();
        }
        return tickTackBummGame;
    }

    @Override
    public void create() {
        manager = new AssetManager();
        setScreen(new MainMenuScreen());
    }
}
