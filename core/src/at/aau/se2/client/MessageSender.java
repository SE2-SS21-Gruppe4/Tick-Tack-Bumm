package at.aau.se2.client;

import com.esotericsoftware.kryonet.Client;

import at.aau.se2.network.messages.Messages;

public class MessageSender {
    private final Client client;

    public MessageSender(Client client) {
        this.client = client;
    }

    public void sendSomeRequest(String text) {
        Messages.SomeRequest someRequest = new Messages.SomeRequest();
        someRequest.text = text;
        client.sendTCP(someRequest);
    }
}
