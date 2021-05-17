package se2.ticktackbumm.core.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.network.messages.SomeResponse;
import se2.ticktackbumm.core.screens.SpinWheelScreen;

/**
 * Handles all incoming server messages for a client.
 */
public class ClientMessageHandler {

    private final String LOG_TAG = "CLIENT_MESSAGE_HANDLER";

    /**
     * Client instance to handle messages for.
     */
    private final Client client;
    private final ClientMessageSender clientMessageSender;

    /**
     * Class constructor.
     * Creates a message handler, that handles all incoming server messages for a client.
     *
     * @param client the client to handle messages for
     */
    public ClientMessageHandler(Client client, ClientMessageSender clientMessageSender) {
        this.client = client;
        this.clientMessageSender = clientMessageSender;
    }

    // Test method
    public void handleSomeResponse(SomeResponse someResponse) {
        Log.info(LOG_TAG, "Server response to " + someResponse.getClass()
                + ": " + someResponse.text);
    }

    public void handleConnectionSuccessful() {
        Log.info(LOG_TAG, "Player successful connected to server");

        Gdx.app.postRunnable(() -> TickTackBummGame.getTickTackBummGame().setScreen(new SpinWheelScreen()));
    }
}
