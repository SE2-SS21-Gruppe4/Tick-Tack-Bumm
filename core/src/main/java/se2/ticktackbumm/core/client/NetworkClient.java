package se2.ticktackbumm.core.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.network.KryoRegisterer;
import se2.ticktackbumm.core.network.NetworkConstants;

import java.io.IOException;

/**
 * The network client of each player.
 */
public class NetworkClient {
    private final String LOG_TAG = "NetworkClient";
    /**
     * Kryonet-Client to connect to server, send and receive messages.
     */
    private final Client client;
    /**
     * MessageHandler to handle all received messages.
     */
    private final ClientMessageHandler clientMessageHandler;

    /**
     * MessageSender to handle all message sending.
     */
    private final ClientMessageSender clientMessageSender;

    /**
     * Class constructor.
     * Create the Kryonet-Client, register all message classes and start the Kryonet-Client.
     */
    public NetworkClient() {
        client = new Client();
        KryoRegisterer.registerMessages(this.client.getKryo());

        clientMessageHandler = new ClientMessageHandler(this.client);
        clientMessageSender = new ClientMessageSender(this.client);

        this.client.start();
        this.client.addListener(new NetworkClientListener(clientMessageHandler));
    }

    /**
     * Try to connect client to server with the parameters specified in
     * {@link NetworkConstants}.
     */
    public void tryConnectClient() {
        try {
            client.connect(NetworkConstants.TIMEOUT, NetworkConstants.HOST_IP, NetworkConstants.TCP_PORT);
        } catch (IOException e) {
            Log.error(LOG_TAG, "Failed to connect client: " + e, e);
        }
    }

    /**
     * Gets the MessageHandler from the NetworkClient. Use this reference to globally handle
     * incoming messages to client.
     *
     * @return reference to the NetworkClients instance of MessageHandler
     */
    public ClientMessageHandler getClientMessageHandler() {
        return clientMessageHandler;
    }

    /**
     * Gets the MessageSender from the NetworkClient. Use this reference to globally send
     * messages from client to server.
     *
     * @return reference to the NetworkClients instance of MessageSender
     */
    public ClientMessageSender getClientMessageSender() {
        return clientMessageSender;
    }
}
