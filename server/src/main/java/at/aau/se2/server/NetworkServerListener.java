package at.aau.se2.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import at.aau.se2.network.messages.Messages;

public class NetworkServerListener extends Listener {
    @Override
    public void connected(Connection connection) {
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Messages.SomeRequest) {
            Messages.SomeRequest request = (Messages.SomeRequest) object;
            System.out.println(request.text);

            Messages.SomeResponse response = new Messages.SomeResponse();
            response.text = "Thanks";
            connection.sendTCP(response);
        }
    }
}
