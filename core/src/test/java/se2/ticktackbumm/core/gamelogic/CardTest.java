package se2.ticktackbumm.core.gamelogic;

import com.badlogic.gdx.assets.AssetManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.models.CardLayout.Card;
import se2.ticktackbumm.core.models.CardLayout.CardWords;

public class CardTest {

    private Card card;

    @BeforeEach
    public void setUp(){
        card = new Card(TickTackBummGame.getTickTackBummGame().getBatch());
    }
    @AfterEach
    public void tearDown(){
        card = null;
    }

    @Test
    public void testRandomWordFromEnum(){
        String firstWord = card.getCardDeck().get(0);
        String randomWord = card.getRandomWord();

        Assertions.assertNotEquals(firstWord,randomWord);
    }
}
