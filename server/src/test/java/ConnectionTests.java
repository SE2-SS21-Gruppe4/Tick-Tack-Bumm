import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import at.aau.se2.Network.NetworkConstants;
import at.aau.se2.Network.messages.Messages;
import at.aau.se2.server.ServerMain;

public class ConnectionTests {
    @Test
    public void testClientServerConnection() throws IOException {
        ServerMain server = new ServerMain();
        server.startServer();

        Client client = new Client();

        Kryo kryo = client.getKryo();
        kryo.register(Messages.SomeRequest.class);
        kryo.register(Messages.SomeResponse.class);

        client.start();
        client.connect(NetworkConstants.TIMEOUT, "localhost", NetworkConstants.TCP_PORT);

        Messages.SomeRequest request = new Messages.SomeRequest();
        request.text = "Here is a request";
        client.sendTCP(request);

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Messages.SomeResponse) {
                    Messages.SomeResponse response = (Messages.SomeResponse) object;
                    System.out.println(response.text);
                    Assertions.assertEquals("Thanks", response.text);
                }
            }
        });
    }
}
