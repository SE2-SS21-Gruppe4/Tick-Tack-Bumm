package at.aau.se2.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import at.aau.se2.network.messages.Messages;

public class MessageHandler {
    private final Client client;

    public MessageHandler(Client client) {
        this.client = client;
    }

    public void handleSomeResponse(Messages.SomeResponse someResponse) {
        Log.info("MessageHandler", "Server response to " + someResponse.getClass()
                + ": " + someResponse.text);
    }
}
