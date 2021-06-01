package se2.ticktackbumm.server.data;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.player.Player;
import se2.ticktackbumm.core.player.ScoreComparator;
import se2.ticktackbumm.server.network.NetworkServer;

import java.util.ArrayList;
import java.util.List;

public class ServerData {

    private final String LOG_TAG = "SERVER_DATA";

    private static final int MAX_PLAYERS = 4;
    private static final int MIN_PLAYERS = 2;

    private final Server kryoServer;
    private final GameData gameData;
    private final GameState gameState;

    private Player winner;
    private int playersReady;

    public ServerData(Server kryoServer) {
        this.kryoServer = kryoServer;

        this.gameData = new GameData();
        this.gameState = GameState.WAITING_FOR_PLAYERS; // which initial state?

        this.playersReady = 0;
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

    public Player connectPlayer(int connectionId) {
        if (gameData.getPlayers().size() < MAX_PLAYERS) {
            Player newPlayer = new Player(connectionId, gameData.getPlayers().size());
            gameData.getPlayers().add(newPlayer);

            return newPlayer;
        }

        return null;
    }

    public void disconnectPlayer(int connectionID) {
        Player player = gameData.getPlayerByConnectionId(connectionID);
        if (player == null) return;
        Log.info(LOG_TAG, "Player disconnected from server: " + player.getPlayerId());

        gameData.getPlayers().remove(player.getPlayerId());
        Log.info(LOG_TAG, "Player removed from server data: " + player.getPlayerId());

        decPlayersReady();
        Log.info(LOG_TAG, "Player removed from ready: " + player.getPlayerId() +
                ", " + playersReady + " players ready");

        NetworkServer.getNetworkServer().getServerMessageSender().sendGameUpdate();
    }

    public void incPlayersReady() {
        if (playersReady < MAX_PLAYERS) {
            Log.info(LOG_TAG, "Incrementing players ready count");
            playersReady++;
        }

        Log.info(LOG_TAG, "Players ready count: " + playersReady);
    }

    public void decPlayersReady() {
        if (playersReady > 0) playersReady--;
    }

    public boolean arePlayersReady() {
        return playersReady >= MIN_PLAYERS;
    }

    public boolean hasGameFinished() {
        for (Player player : gameData.getPlayers()) {
            if (player.getGameScore() >= gameData.getMaxGameScore()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get an array of all players sorted in descending order.
     *
     * @return {@link Player}[] sorted in descending order
     */
    public Player[] getPlacedPlayers() {
        List<Player> placedPlayers = new ArrayList<>(gameData.getPlayers());
        placedPlayers.sort(new ScoreComparator());

        return placedPlayers.toArray(new Player[0]);
    }
}
