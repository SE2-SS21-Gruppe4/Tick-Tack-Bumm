package se2.ticktackbumm.core.network;

import com.esotericsoftware.kryo.Kryo;

import se2.ticktackbumm.core.network.messages.Messages;

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
        kryo.register(Messages.SomeRequest.class);
        kryo.register(Messages.SomeResponse.class);
    }
}
