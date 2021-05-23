package se2.ticktackbumm.core.gamelogic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se2.ticktackbumm.core.models.Score;

class ScoreTest {
    Score score;

    @BeforeEach
    void setUp() {
        score = new Score();
    }

    @AfterEach
    void tearDown() {
        score = null;
    }

    @Test
    void playerAddedCorrectly() {
        Assertions.assertEquals(4, score.getPlayer().size());
    }
}
