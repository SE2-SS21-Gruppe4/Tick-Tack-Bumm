package se2.ticktackbumm.core.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ScoreTest {
    Score score;
    int[] playerscore = {1, 2, 3, 4};

    @BeforeEach
    void setUp() {
        score = new Score(playerscore);
    }

    @AfterEach
    void tearDown() {
        score = null;
    }

    @Test
    void getPlayer1() {
        Assertions.assertEquals(1, score.getPlayerScore(0));
    }

    @Test
    void getPlayer2() {
        Assertions.assertEquals(2, score.getPlayerScore(1));
    }

    @Test
    void getPlayer3() {
        Assertions.assertEquals(3, score.getPlayerScore(2));
    }

    @Test
    void getPlayer4() {
        Assertions.assertEquals(4, score.getPlayerScore(3));
    }
}