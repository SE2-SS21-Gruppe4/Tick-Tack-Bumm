package at.aau.se2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.esotericsoftware.kryonet.Client;

import at.aau.se2.screens.LoadingScreen;

public class TickTackBummGame extends Game {
    private static TickTackBummGame tickTackBummGame;
    private AssetManager manager;
    private Client client;

    public static TickTackBummGame getTickTackBummGame() {
        if (tickTackBummGame == null) {
            tickTackBummGame = new TickTackBummGame();
        }
        return tickTackBummGame;
    }

    @Override
    public void create() {
        manager = new AssetManager();
        client = new Client();

        setScreen(new LoadingScreen());
    }

    public AssetManager getManager() {
        return manager;
    }

    public Client getClient() {
        return client;
    }
}
