package se2.ticktackbumm.core.network;

import com.esotericsoftware.kryo.Kryo;
import se2.ticktackbumm.core.data.Avatars;
import se2.ticktackbumm.core.data.GameMode;
import se2.ticktackbumm.core.network.messages.client.*;
import se2.ticktackbumm.core.network.messages.server.*;
import se2.ticktackbumm.core.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Kryonet Helper Class.
 */
public class KryoRegisterer {

    /**
     * Register all message classes and classes contained in these messages on the given kryo instance.
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
        kryo.register(GameUpdate.class);
        kryo.register(NextTurn.class);
        kryo.register(NextRound.class);
        kryo.register(GameFinished.class);
        kryo.register(StartBomb.class);
        kryo.register(SpinWheelStarted.class);
        kryo.register(SpinWheelFinished.class);

        // register owned classes to send in messages
        kryo.register(Player.class);
        kryo.register(Player[].class);
        kryo.register(Avatars.class);
        kryo.register(GameMode.class);

        // register java classes to send in messages
        kryo.register(List.class);
        kryo.register(ArrayList.class);
    }
}
