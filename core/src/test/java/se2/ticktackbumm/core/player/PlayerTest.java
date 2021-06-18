package se2.ticktackbumm.core.player;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @AfterEach
    void tearDown() {
        player = null;
    }

    @Test
    void incPlayerScore(){
        player.incPlayerScore();
        Assertions.assertEquals(1, player.getGameScore());
    }

    @Test
    void incPlayerScoreMultipleTimes(){
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        Assertions.assertEquals(4, player.getGameScore());
    }

    @Test
    void incPlayerScoreOverTen(){
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        player.incPlayerScore();
        Assertions.assertEquals(10, player.getGameScore());
    }
}