package se2.ticktackbumm.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se2.ticktackbumm.core.client.NetworkClient;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.data.GameMode;
import se2.ticktackbumm.core.player.Player;
import se2.ticktackbumm.core.screens.LoadingScreen;
import se2.ticktackbumm.core.screens.MainGameScreen;
import se2.ticktackbumm.core.screens.SpinWheelScreen;

public class TickTackBummGame extends Game {

    public static final int HEIGHT = 2220;
    public static final int WIDTH = 1080;

    private static TickTackBummGame tickTackBummGame;

    private Player localPlayer;

    private GameData gameData;

    private AssetManager manager;
    private NetworkClient networkClient;
    private SpriteBatch batch;

    private BitmapFont font;

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

    public boolean isLocalPlayerTurn() {
        return gameData.getCurrentPlayerTurnIndex() == localPlayer.getPlayerId();
    }

    public void startNewGame() {
        if (isLocalPlayerTurn()) {
            setScreen(new SpinWheelScreen());
        } else {
            setScreen(new MainGameScreen());
            startNextTurn();
        }
    }

    public void startNextRound() {
        MainGameScreen gameScreen = (MainGameScreen) this.getScreen();

        if (isLocalPlayerTurn()) {
            Gdx.app.postRunnable(() -> setScreen(new SpinWheelScreen()));
        } else {
            gameScreen.updatePlayerScores();
            gameScreen.resetCard();
            gameScreen.resetBomb();
            gameScreen.setInfoLabelToWaiting();

            startNextTurn();
        }
    }

    public void startNextTurn() {
        MainGameScreen gameScreen = (MainGameScreen) this.getScreen();

        if (isLocalPlayerTurn()) {
            gameScreen.showControls();
        } else {
            gameScreen.hideControls();
        }

        gameScreen.updateCurrentPlayerMarker();
    }

    public void spinWheelFinished() {
        if (this.getScreen() instanceof MainGameScreen) {
            MainGameScreen gameScreen = (MainGameScreen) this.getScreen();

            gameScreen.updateInfoLabel();
        }
    }

    public void showBomb() {
        if (this.getScreen() instanceof MainGameScreen) {
            MainGameScreen gameScreen = (MainGameScreen) this.getScreen();

            gameScreen.setShowBomb(true);
            gameScreen.updateBombTimer(gameData.getBombTimer());
            gameScreen.getBomb().startTicking();
        }
    }

    public void bombExploded() {
        if (this.getScreen() instanceof MainGameScreen) {
            MainGameScreen gameScreen = (MainGameScreen) this.getScreen();

            gameScreen.setBombShouldExplode(true);
            gameScreen.getBomb().explodeBomb();
        }
    }

    public void openCard() {
        if (this.getScreen() instanceof MainGameScreen) {
            MainGameScreen gameScreen = (MainGameScreen) this.getScreen();

            gameScreen.getCard().setRevealed(true);
            gameScreen.updateCardWord(gameData.getCurrentGameModeText());
        }
    }

    /**
     *   after the maxScore is reached the game is finished and switches to WinnerScreen
     */
    public void finishGame() {
        MainGameScreen gameScreen = (MainGameScreen) this.getScreen();

        gameScreen.hideControls();
        gameScreen.updatePlayerScores();
        gameScreen.updateCurrentPlayerMarker();
        gameScreen.setWinnerScreen();
        // TODO: show game finished message, scoreboard screen, ...
    }


    public void setSpinWheelFinished(GameMode gameMode) {
        gameData.setCurrentGameMode(gameMode);
    }

    @Override
    public void dispose() {
        manager.dispose();
        batch.dispose();
    }

}
