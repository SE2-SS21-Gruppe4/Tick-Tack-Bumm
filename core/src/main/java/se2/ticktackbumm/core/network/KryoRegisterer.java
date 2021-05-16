package se2.ticktackbumm.core.network;

import com.esotericsoftware.kryo.Kryo;
import se2.ticktackbumm.core.network.messages.ConnectionRejected;
import se2.ticktackbumm.core.network.messages.ConnectionSuccessful;
import se2.ticktackbumm.core.network.messages.SomeRequest;
import se2.ticktackbumm.core.network.messages.SomeResponse;

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
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(ConnectionSuccessful.class);
        kryo.register(ConnectionRejected.class);
    }
}
