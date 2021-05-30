package se2.ticktackbumm.core.player;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Player> {
    @Override
    public int compare(Player player1, Player player2) {
        return Integer.compare(player1.getGameScore(), player2.getGameScore());
    }
}
