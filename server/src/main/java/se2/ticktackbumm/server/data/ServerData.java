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

    /**
     * Maximal player count allowed in game.
     */
    private static final int MAX_PLAYERS = 4;
    /**
     * Minimal player count to start a game. Made for easy testing and presenting.
     */
    private static final int MIN_PLAYERS = 2;

    /**
     * The log tag is used to provide unique logging for the class.
     */
    private final String LOG_TAG = "SERVER_DATA";

    /**
     * Kryonet-Server to receive client connections, send and receive messages from clients.
     */
    private final Server kryoServer;
    /**
     * The game data which is included in the singleton instance of the game class. Provides functionality
     * read and alter the game's general data.
     */
    private final GameData gameData;

    /**
     * List of ready players waiting for game start in the WaitingScreen.
     */
    private final List<Player> playersReady;

    /**
     * Constructs a server data instance for the current game server.
     *
     * @param kryoServer the Kryonet-{@link Server} instance of the game server
     */
    public ServerData(Server kryoServer) {
        this.kryoServer = kryoServer;

        this.gameData = new GameData();

        this.playersReady = new ArrayList<>();
    }

    public static int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    public static int getMinPlayers() {
        return MIN_PLAYERS;
    }

    /**
     * Creates a new {@link Player} for a new incoming connection to the kryo server, if the
     * maximal number of players is not yet reached.
     *
     * @param connectionId the connection ID of the player to connect assigned by the game server
     * @return the constructed {@link Player} object for the connected player
     */
    public Player connectPlayer(int connectionId) {
        if (gameData.getPlayers().size() < MAX_PLAYERS) {
            Player newPlayer = new Player(connectionId, gameData.getPlayers().size());
            gameData.getPlayers().add(newPlayer);

            return newPlayer;
        }

        return null;
    }

    /**
     * Disconnects a player - identified by it's connection ID - from the game server,
     * removes it from the player list, decrements the player ready count and updates
     * all the other players with that information.
     *
     * @param connectionID the player's connection ID
     */
    public void disconnectPlayer(int connectionID) {
        updatePlayerId(connectionID);

        Player player = gameData.getPlayerByConnectionId(connectionID);
        if (player == null) return;
        Log.info(LOG_TAG, "Player disconnected from server: " + player.getPlayerId());

        gameData.getPlayers().remove(player.getPlayerId());
        Log.info(LOG_TAG, "Player removed from server data: " + player.getPlayerId());

        updatePlayerId(connectionID);

        removeReadyPlayer(player);
        Log.info(LOG_TAG, "Player removed from ready: " + player.getPlayerName() +
                ", " + playersReady.size() + " players ready");

        NetworkServer.getNetworkServer().getServerMessageSender().sendGameUpdate();
    }

    /**
     * Update a player's ID based on it's connection ID. Used to correct the player ID, when players
     * disconnect from the game server.
     *
     * @param connectionID the player's connection ID
     */
    public void updatePlayerId(int connectionID) {
        Player player = gameData.getPlayerByConnectionId(connectionID);
        if (player == null) return;
        List<Player> players = gameData.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getConnectionId() == connectionID) {
                player.setPlayerId(i);
            }
        }
    }

    /**
     * Adds a player to the players ready list, if the maximal player count is not reached.
     *
     * @param player player to add to ready players
     */
    public void addReadyPlayer(Player player) {
        if (playersReady.size() < MAX_PLAYERS) {
            Log.info(LOG_TAG, "Adding player to players ready list: " + player.getPlayerName());
            playersReady.add(player);
        }

        Log.info(LOG_TAG, "Players ready count: " + playersReady.size());
    }

    /**
     * Removes a player from the players ready list.
     *
     * @param player player to remove from ready players
     */
    public void removeReadyPlayer(Player player) {
        playersReady.remove(player);
    }

    /**
     * Returns true if there are enough players ready to start a game and false otherwise.
     *
     * @return true if the game can start, false otherwise.
     */
    public boolean arePlayersReady() {
        return playersReady.size() >= MIN_PLAYERS;
    }

    // simple getters & setters

    /**
     * Returns true if there is a player in the game that has reached the maximal game score
     * and false otherwise.
     *
     * @return true if the game finished, false otherwise.
     */
    public boolean hasGameFinished() {
        for (Player player : gameData.getPlayers()) {
            if (player.getGameScore() >= gameData.getMaxGameScore()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return an array of all players sorted by their game scores in ascending order.
     *
     * @return {@link Player}[] sorted in ascending order
     */
    public Player[] getPlacedPlayers() {
        List<Player> placedPlayers = new ArrayList<>(gameData.getPlayers());
        placedPlayers.sort(new ScoreComparator());

        return placedPlayers.toArray(new Player[0]);
    }

    public Server getKryoServer() {
        return kryoServer;
    }

    public GameData getGameData() {
        return gameData;
    }

    public int getPlayersReadyCount() {
        return playersReady.size();
    }

}
