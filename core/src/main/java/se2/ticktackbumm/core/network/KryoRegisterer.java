package se2.ticktackbumm.core.network;

import com.esotericsoftware.kryo.Kryo;
import se2.ticktackbumm.core.data.Avatars;
import se2.ticktackbumm.core.network.messages.*;
import se2.ticktackbumm.core.player.Player;

/**
 * Kryonet Helper Class.
 */
public class KryoRegisterer {
    /**
     * Register all our message classes on the given kryo instance.
     *
     * @param kryo the Kryo instance to register Messages on
     */
    public static void registerMessages(Kryo kryo) {

        // register messages
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(ConnectionSuccessful.class);
        kryo.register(ConnectionRejected.class);
        kryo.register(PlayerTaskCompleted.class);
        kryo.register(BombExploded.class);
        kryo.register(PlayerReady.class);
        kryo.register(StartGame.class);

        // register classes to send in messages
        kryo.register(Player.class);
        kryo.register(Avatars.class);
    }
}
