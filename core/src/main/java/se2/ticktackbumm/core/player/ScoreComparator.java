package se2.ticktackbumm.core.player;

import java.util.Comparator;

/**
 * Helper class for comparing two {@link Player}s based on the game score.
 */
public class ScoreComparator implements Comparator<Player> {
    @Override
    public int compare(Player player1, Player player2) {
        return Integer.compare(player1.getGameScore(), player2.getGameScore());
    }
}
