package se2.ticktackbumm.core.client;

import com.esotericsoftware.kryonet.Client;

import se2.ticktackbumm.core.network.messages.Messages;

public class MessageSender {
    private final Client client;

    public MessageSender(Client client) {
        this.client = client;
    }

    public void sendSomeRequest(String text) {
        se2.ticktackbumm.core.network.messages.Messages.SomeRequest someRequest = new Messages.SomeRequest();
        someRequest.text = text;
        client.sendTCP(someRequest);
    }
}
