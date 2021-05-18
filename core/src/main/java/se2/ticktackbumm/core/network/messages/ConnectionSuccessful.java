package se2.ticktackbumm.core.network.messages;

import se2.ticktackbumm.core.player.Player;

public class ConnectionSuccessful {

    private Player connectedPlayer;

    public ConnectionSuccessful() {
        // kryonet
    }

    public ConnectionSuccessful(Player connectedPlayer) {
        this.connectedPlayer = connectedPlayer;
    }

    public Player getConnectedPlayer() {
        return connectedPlayer;
    }

    public void setConnectedPlayer(Player connectedPlayer) {
        this.connectedPlayer = connectedPlayer;
    }
}
