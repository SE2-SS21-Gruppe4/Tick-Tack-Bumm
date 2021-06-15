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

        serverData.connectPlayer(0);
        Assertions.assertNotNull(serverData.getGameData().getPlayerByConnectionId(0));
        Assertions.assertEquals(1, serverData.getGameData().getPlayers().size());
    }

    @Test
    void disconnectPlayer() {

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
