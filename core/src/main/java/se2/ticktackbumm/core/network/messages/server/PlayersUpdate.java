package se2.ticktackbumm.core.network.messages.server;

import se2.ticktackbumm.core.player.Player;

import java.util.List;

public class PlayersUpdate {

    private List<Player> players;

    public PlayersUpdate() {
        // kryonet
    }

    public PlayersUpdate(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
