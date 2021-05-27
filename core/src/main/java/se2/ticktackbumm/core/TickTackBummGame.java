package se2.ticktackbumm.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.client.NetworkClient;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.player.Player;
import se2.ticktackbumm.core.screens.LoadingScreen;
import se2.ticktackbumm.core.screens.MainGameScreen;
import se2.ticktackbumm.core.screens.SpinWheelScreen;

public class TickTackBummGame extends Game {

    // TODO: add log tag with local player ID?

    public static final int HEIGHT = 2220;
    public static final int WIDTH = 1080;

    private static TickTackBummGame tickTackBummGame;

    private Player localPlayer; // TODO: add current player from server response at game start?

    private GameData gameData;

    private AssetManager manager;
    private NetworkClient networkClient;
    private SpriteBatch batch;

    private BitmapFont font;

    @Override
    public void create() {
        gameData = new GameData();
        manager = new AssetManager();
        networkClient = new NetworkClient();

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2);

        // display loading-screen on startup
        setScreen(new LoadingScreen());
    }

    public static TickTackBummGame getTickTackBummGame() {
        if (tickTackBummGame == null) {
            tickTackBummGame = new TickTackBummGame();
        }
        return tickTackBummGame;
    }

    public static OrthographicCamera getGameCamera() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT);
        return camera;
    }

    public AssetManager getManager() {
        return manager;
    }

    public NetworkClient getNetworkClient() {
        return networkClient;
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Player getLocalPlayer() {
        return localPlayer;
    }

    public void setLocalPlayer(Player localPlayer) {
        this.localPlayer = localPlayer;
    }

    public GameData getGameData() {
        return gameData;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    boolean isLocalPlayerTurn() {
        return gameData.getCurrentPlayerTurnIndex() == localPlayer.getPlayerId();
    }

    public void startNewGame() {
        if (isLocalPlayerTurn()) {
            switchScreen(new SpinWheelScreen());
        } else {
            switchScreen(new MainGameScreen());
            startNewTurn();
        }
    }

    public void startNextRound() {
        if (isLocalPlayerTurn()) {
            // TODO: fixme, why is this necessary?
            Gdx.app.postRunnable(() -> switchScreen(new SpinWheelScreen()));
        } else {
            startNewTurn();
        }
    }

    public void startNewTurn() {
        MainGameScreen gameScreen = (MainGameScreen) this.getScreen();
        if (isLocalPlayerTurn()) {
            gameScreen.showControls();
            gameScreen.updatePlayerScores();
            gameScreen.updateCurrentPlayerMarker();
            gameScreen.resetCard();
        } else {
            Log.info("NOT LOCAL PLAYER TURN");
            gameScreen.hideControls();
            gameScreen.updatePlayerScores();
            gameScreen.updateCurrentPlayerMarker();
            gameScreen.resetCard();
            // hide waiting for spin wheel message
        }
    }

    public void switchScreen(Screen screen) {
        Screen currentScreen = getScreen();
        setScreen(screen);
        currentScreen.dispose();
    }

    @Override
    public void dispose() {
        manager.dispose();
        batch.dispose();
    }
}
