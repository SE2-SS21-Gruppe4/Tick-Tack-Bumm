import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import se2.ticktackbumm.core.network.NetworkConstants;
import se2.ticktackbumm.core.network.messages.client.SomeRequest;
import se2.ticktackbumm.core.network.messages.server.SomeResponse;
import se2.ticktackbumm.server.network.NetworkServer;

public class ConnectionTests {
    @Test
    public void testClientServerConnection() throws IOException {
        NetworkServer server = NetworkServer.getNetworkServer();

        Client client = new Client();

        Kryo kryo = client.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);

        client.start();
        client.connect(NetworkConstants.TIMEOUT, "localhost", NetworkConstants.TCP_PORT);

        client.sendTCP(new SomeRequest("Here is a request"));

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof SomeResponse) {
                    SomeResponse response = (SomeResponse) object;
                    System.out.println(response.getText());
                    Assertions.assertEquals("Thanks", response.getText());
                }
            }
        });
    }
}
