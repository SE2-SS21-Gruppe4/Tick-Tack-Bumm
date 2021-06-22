package se2.ticktackbumm.core.network.messages.server;

import se2.ticktackbumm.core.player.Player;

public class GameFinished {

    private Player[] placedPlayers;

    public GameFinished() {
        // kryonet
    }

    public GameFinished(Player[] placedPlayers) {
        this.placedPlayers = placedPlayers;
    }

    public Player[] getPlacedPlayers() {
        return placedPlayers;
    }
}
