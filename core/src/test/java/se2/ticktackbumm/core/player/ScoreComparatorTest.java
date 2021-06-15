package se2.ticktackbumm.core.player;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreComparatorTest {
    Player player1;
    Player player2;
    ScoreComparator scoreComparator;

    @BeforeEach
    void setUp() {
        scoreComparator = new ScoreComparator();
        player1 = new Player();
        player2 = new Player();
    }

    @AfterEach
    void tearDown() {
        player1 = null;
        player2 = null;
    }

    @Test
    void compareIfResultEquals() {
        player1.setGameScore(5);
        player2.setGameScore(5);
        Assertions.assertEquals(0, scoreComparator.compare(player1, player2));
    }

    @Test
    void compareIfScoreOfPlayer1GreaterThanScoreOfPlayer2() {
        player1.setGameScore(3);
        player2.setGameScore(1);
        Assertions.assertEquals(1, scoreComparator.compare(player1, player2));
    }

    @Test
    void compareIfScoreOfPlayer1LowerThanScoreOfPlayer2() {
        player1.setGameScore(3);
        player2.setGameScore(8);
        Assertions.assertEquals(-1, scoreComparator.compare(player1, player2));
    }
}
