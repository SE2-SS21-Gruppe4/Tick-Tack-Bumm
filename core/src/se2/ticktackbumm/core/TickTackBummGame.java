package se2.ticktackbumm.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

import se2.ticktackbumm.core.client.NetworkClient;
import se2.ticktackbumm.core.screens.LoadingScreen;

public class TickTackBummGame extends Game {
    private static TickTackBummGame tickTackBummGame;
    private AssetManager manager;
    private NetworkClient networkClient;

    public static TickTackBummGame getTickTackBummGame() {
        if (tickTackBummGame == null) {
            tickTackBummGame = new TickTackBummGame();
        }
        return tickTackBummGame;
    }

    @Override
    public void create() {
        manager = new AssetManager();
        networkClient = new NetworkClient();

        setScreen(new LoadingScreen());
    }

    public AssetManager getManager() {
        return manager;
    }

    public NetworkClient getNetworkClient() {
        return networkClient;
    }
}
