package se2.ticktackbumm.server.data;

import com.esotericsoftware.kryonet.Server;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.player.Player;

public class ServerData {
    private static final int MAX_PLAYERS = 4;
    private static final int MIN_PLAYERS = 1;

    private final Server kryoServer;
    private final GameData gameData;
    private final GameState gameState;

    private Player winner;

    public ServerData(Server kryoServer) {
        this.kryoServer = kryoServer;

        this.gameData = new GameData();
        this.gameState = GameState.WAITING_FOR_PLAYERS; // which initial state?
    }

    public boolean connectPlayer(int connectionId) {
        if (gameData.getPlayers().size() < MAX_PLAYERS) {
            Player newPlayer = new Player(connectionId, gameData.getPlayers().size());
            gameData.getPlayers().add(newPlayer);

            return true;
        }

        return false;
    }

    public static int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    public static int getMinPlayers() {
        return MIN_PLAYERS;
    }

    public Server getKryoServer() {
        return kryoServer;
    }

    public GameData getGameData() {
        return gameData;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
