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

    /**
     * The log tag is used to provide unique logging for the class.
     */
    private final String LOG_TAG = "NETWORK_CLIENT";

    /**
     * Kryonet-Client to connect to server, send and receive messages.
     */
    private final Client kryoClient;
    /**
     * The game's message sender, later contained in the singleton instance of the game class. Provides
     * functionality to send messages from client to server.
     */
    private final ClientMessageHandler clientMessageHandler;
    /**
     * The game's message sender, later contained in the singleton instance of the game class. Provides
     * functionality to send messages from client to server.
     */
    private final ClientMessageSender clientMessageSender;

    /**
     * Class constructor.
     * Create the Kryonet-Client, register all message classes and start the Kryonet-Client.
     */
    public NetworkClient() {
        kryoClient = new Client();
        KryoRegisterer.registerMessages(this.kryoClient.getKryo());

        clientMessageSender = new ClientMessageSender(kryoClient);
        clientMessageHandler = new ClientMessageHandler(clientMessageSender);

        kryoClient.addListener(new NetworkClientListener(clientMessageHandler));

        kryoClient.start();
    }

    /**
     * Try to connect client to server with the parameters specified in {@link NetworkConstants}.
     */
    public void tryConnectClient() {
        try {
            kryoClient.connect(NetworkConstants.TIMEOUT, NetworkConstants.HOST_IP, NetworkConstants.TCP_PORT);
            Log.info(LOG_TAG, "Connected client to server with IP " + NetworkConstants.HOST_IP + " on port " +
                    NetworkConstants.TCP_PORT);
        } catch (IOException e) {
            Log.error(LOG_TAG, "Failed to connect client: " + e, e);
        }
    }

    /**
     * Disconnect the client from the server.
     */
    public void disconnectClient() {
        kryoClient.close();
        Log.info(LOG_TAG, "Disconnected client from server with IP " + NetworkConstants.HOST_IP + " on port " +
                NetworkConstants.TCP_PORT);
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
