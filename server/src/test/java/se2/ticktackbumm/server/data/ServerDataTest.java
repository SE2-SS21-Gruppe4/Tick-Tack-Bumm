package se2.ticktackbumm.server.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void disconnectPlayer() {
        connectTooManyPlayers();
        serverData.disconnectPlayer(0);
    }

    @Test
    void incPlayersReady() {
    }

    @Test
    void decPlayersReady() {
    }

    @Test
    void arePlayersReady() {
    }

    @Test
    void hasGameFinished() {
    }

    @Test
    void getPlacedPlayers() {
    }
}
