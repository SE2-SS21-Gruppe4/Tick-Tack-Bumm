package se2.ticktackbumm.server.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se2.ticktackbumm.core.player.Player;
import se2.ticktackbumm.server.network.NetworkServer;

class ServerDataTest {

    private ServerData serverData;

    @BeforeEach
    void setUp() {
        serverData = new ServerData(NetworkServer.getNetworkServer().getKryoServer());
    }

    @AfterEach
    void tearDown() {
        serverData = null;
    }

    @Test
    void connectSinglePlayer() {
        Assertions.assertNull(serverData.getGameData().getPlayerByConnectionId(0));
        Assertions.assertEquals(0, serverData.getGameData().getPlayers().size());

        Assertions.assertNotNull(serverData.connectPlayer(0));

        Assertions.assertNotNull(serverData.getGameData().getPlayerByConnectionId(0));

        Assertions.assertEquals(1, serverData.getGameData().getPlayers().size());
    }

    @Test
    void connectFourPlayers() {
        Assertions.assertNull(serverData.getGameData().getPlayerByConnectionId(0));
        Assertions.assertEquals(0, serverData.getGameData().getPlayers().size());

        Assertions.assertNotNull(serverData.connectPlayer(0));
        Assertions.assertNotNull(serverData.connectPlayer(1));
        Assertions.assertNotNull(serverData.connectPlayer(2));
        Assertions.assertNotNull(serverData.connectPlayer(3));

        Assertions.assertNotNull(serverData.getGameData().getPlayerByConnectionId(0));
        Assertions.assertNotNull(serverData.getGameData().getPlayerByConnectionId(1));
        Assertions.assertNotNull(serverData.getGameData().getPlayerByConnectionId(2));
        Assertions.assertNotNull(serverData.getGameData().getPlayerByConnectionId(3));

        Assertions.assertEquals(4, serverData.getGameData().getPlayers().size());
    }

    @Test
    void connectTooManyPlayers() {
        Assertions.assertNull(serverData.getGameData().getPlayerByConnectionId(0));
        Assertions.assertEquals(0, serverData.getGameData().getPlayers().size());

        Assertions.assertNotNull(serverData.connectPlayer(0));
        Assertions.assertNotNull(serverData.connectPlayer(1));
        Assertions.assertNotNull(serverData.connectPlayer(2));
        Assertions.assertNotNull(serverData.connectPlayer(3));
        Assertions.assertNull(serverData.connectPlayer(4));
        Assertions.assertNull(serverData.connectPlayer(5));

        Assertions.assertNotNull(serverData.getGameData().getPlayerByConnectionId(0));
        Assertions.assertNotNull(serverData.getGameData().getPlayerByConnectionId(1));
        Assertions.assertNotNull(serverData.getGameData().getPlayerByConnectionId(2));
        Assertions.assertNotNull(serverData.getGameData().getPlayerByConnectionId(3));
        Assertions.assertNull(serverData.getGameData().getPlayerByConnectionId(4));
        Assertions.assertNull(serverData.getGameData().getPlayerByConnectionId(5));

        Assertions.assertEquals(4, serverData.getGameData().getPlayers().size());
    }

    @Test
    void updatePlayerId() {
        connectFourPlayers();

        serverData.updatePlayerId(1);
        serverData.updatePlayerId(2);

        Assertions.assertEquals(1,
                serverData.getGameData().getPlayers().get(1).getConnectionId());
        Assertions.assertEquals(2,
                serverData.getGameData().getPlayers().get(2).getConnectionId());
    }

    @Test
    void disconnectSinglePlayer() {
        connectFourPlayers();

        serverData.disconnectPlayer(0);

        Assertions.assertEquals(3, serverData.getGameData().getPlayers().size());
    }

    @Test
    void disconnectFourPlayers() {
        connectFourPlayers();

        serverData.disconnectPlayer(0);
        serverData.disconnectPlayer(1);
        serverData.disconnectPlayer(2);
        serverData.disconnectPlayer(3);

        Assertions.assertEquals(0, serverData.getGameData().getPlayers().size());
    }

    @Test
    void disconnectFourPlayersRandom() {
        connectFourPlayers();

        serverData.disconnectPlayer(3);
        serverData.disconnectPlayer(1);
        serverData.disconnectPlayer(0);
        serverData.disconnectPlayer(2);

        Assertions.assertEquals(0, serverData.getGameData().getPlayers().size());
    }

    @Test
    void addReadyPlayerOnce() {
        Player player = new Player();

        serverData.addReadyPlayer(player);

        Assertions.assertEquals(1, serverData.getPlayersReadyCount());
        Assertions.assertFalse(serverData.arePlayersReady());
    }

    @Test
    void addReadyPlayerFourTimes() {
        Player player = new Player();

        for (int i = 0; i < 4; i++) {
            serverData.addReadyPlayer(player);
        }

        Assertions.assertEquals(4, serverData.getPlayersReadyCount());
        Assertions.assertTrue(serverData.arePlayersReady());
    }

    @Test
    void addReadyPlayerTooManyTimes() {
        Player player = new Player();

        for (int i = 0; i < 6; i++) {
            serverData.addReadyPlayer(player);
        }

        Assertions.assertEquals(4, serverData.getPlayersReadyCount());
        Assertions.assertTrue(serverData.arePlayersReady());
    }

    @Test
    void removeReadyPlayerOnce() {
        Assertions.assertEquals(0, serverData.getPlayersReadyCount());

        Player player = new Player();
        serverData.removeReadyPlayer(player);

        Assertions.assertEquals(0, serverData.getPlayersReadyCount());
        Assertions.assertFalse(serverData.arePlayersReady());
    }

    @Test
    void addAndRemoveReadyPlayerOnce() {
        Assertions.assertEquals(0, serverData.getPlayersReadyCount());

        Player player = new Player();
        serverData.addReadyPlayer(player);
        serverData.removeReadyPlayer(player);

        Assertions.assertEquals(0, serverData.getPlayersReadyCount());
        Assertions.assertFalse(serverData.arePlayersReady());
    }

    @Test
    void addAndRemoveReadyPlayerFourTimes() {
        Assertions.assertEquals(0, serverData.getPlayersReadyCount());

        Player player = new Player();

        for (int i = 0; i < 4; i++) {
            serverData.addReadyPlayer(player);
        }

        for (int i = 0; i < 4; i++) {
            serverData.removeReadyPlayer(player);
        }

        Assertions.assertEquals(0, serverData.getPlayersReadyCount());
        Assertions.assertFalse(serverData.arePlayersReady());
    }

    @Test
    void addAndRemoveReadyPlayerTooManyTimes() {
        Assertions.assertEquals(0, serverData.getPlayersReadyCount());

        Player player = new Player();

        for (int i = 0; i < 6; i++) {
            serverData.addReadyPlayer(player);
        }

        for (int i = 0; i < 6; i++) {
            serverData.removeReadyPlayer(player);
        }

        Assertions.assertEquals(0, serverData.getPlayersReadyCount());
        Assertions.assertFalse(serverData.arePlayersReady());
    }

    @Test
    void arePlayersReady() {
        addReadyPlayerFourTimes();
        Assertions.assertTrue(serverData.arePlayersReady());
    }

    @Test
    void gameHasNotFinished() {
        connectFourPlayers();
        Assertions.assertFalse(serverData.hasGameFinished());
    }

    @Test
    void gameHasFinished() {
        connectFourPlayers();
        serverData.getGameData().getPlayerByConnectionId(0)
                .setGameScore(serverData.getGameData().getMaxGameScore());
        Assertions.assertTrue(serverData.hasGameFinished());
    }

    @Test
    void getPlacedPlayers() {
        connectFourPlayers();

        for (int i = 0; i < serverData.getGameData().getPlayers().size(); i++) {
            serverData.getGameData().getPlayerByConnectionId(i)
                    .setGameScore(serverData.getGameData().getPlayers().size() - 1 - i);
        }

        for (int i = 0; i < serverData.getGameData().getPlayers().size(); i++) {
            Assertions.assertEquals(i, serverData.getPlacedPlayers()[i].getGameScore());
        }
    }
}
