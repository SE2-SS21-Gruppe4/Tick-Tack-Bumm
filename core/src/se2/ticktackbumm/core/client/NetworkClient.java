package se2.ticktackbumm.core.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

import se2.ticktackbumm.core.network.KryoRegisterer;
import se2.ticktackbumm.core.network.NetworkConstants;

/**
 * The network client of each player.
 */
public class NetworkClient {
    /**
     * Kryonet-Client to connect to server, send and receive messages.
     */
    private final Client client;
    /**
     * MessageHandler to handle all received messages.
     */
    private final se2.ticktackbumm.core.client.MessageHandler messageHandler;

    /**
     * MessageSender to handle all message sending.
     */
    private final se2.ticktackbumm.core.client.MessageSender messageSender;

    /**
     * Class constructor.
     * Create the Kryonet-Client, register all message classes and start the Kryonet-Client.
     */
    public NetworkClient() {
        client = new Client();
        KryoRegisterer.registerMessages(this.client.getKryo());

        messageHandler = new se2.ticktackbumm.core.client.MessageHandler(this.client);
        messageSender = new se2.ticktackbumm.core.client.MessageSender(this.client);

        this.client.start();
        this.client.addListener(new NetworkClientListener(messageHandler));
    }

    /**
     * Try to connect client to server with the parameters specified in {@link se2.ticktackbumm.core.network.NetworkConstants}.
     */
    public void tryConnectClient() {
        try {
            client.connect(se2.ticktackbumm.core.network.NetworkConstants.TIMEOUT, se2.ticktackbumm.core.network.NetworkConstants.HOST_IP, NetworkConstants.TCP_PORT);
        } catch (IOException e) {
            Log.error("Failed to connect client: ", e);
        }
    }

    /**
     * Gets the MessageHandler from the NetworkClient. Use this reference to globally handle
     * incoming messages to client.
     *
     * @return reference to the NetworkClients instance of MessageHandler
     */
    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    /**
     * Gets the MessageSender from the NetworkClient. Use this reference to globally send
     * messages from client to server.
     *
     * @return reference to the NetworkClients instance of MessageSender
     */
    public MessageSender getMessageSender() {
        return messageSender;
    }
}
