package se2.ticktackbumm.core.models.cards;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardTest {

    private Card card;

    private boolean wordFounded;
    private String randomWord;

    private String[] prefixWords;
    private String[] infixWords;
    private String[] postfixWords;


    @BeforeEach
    public void setUp(){
       card = new Card("");

       wordFounded = false;
       randomWord = "";

       prefixWords = card.getPrefixArray();
       infixWords = card.getInfixArray();
       postfixWords = card.getPostfixArray();

    }
    @AfterEach
    void tearDown() {
        card = null;

        randomWord = null;

        prefixWords = null;
        infixWords = null;
        postfixWords = null;
    }

   @Test
    public void testRandomWordInPrefix(){
        randomWord = card.getRandomWord(prefixWords);

        for (int i = 0; i < prefixWords.length; i++){
            if (prefixWords[i].equals(randomWord)){
                wordFounded = true;
            }
        }

       Assertions.assertTrue(wordFounded);
   }

   @Test
    public void testRandomWordInInfix(){
        randomWord = card.getRandomWord(infixWords);

        for (int i = 0; i < infixWords.length; i++){
            if (infixWords[i].equals(randomWord)){
                wordFounded = true;
            }
        }

        Assertions.assertTrue(wordFounded);
   }

   @Test
    public void testRandomWordInPostfix(){
        randomWord = card.getRandomWord(postfixWords);

        for (int i = 0; i < postfixWords.length; i++){
            if (postfixWords[i].equals(randomWord)){
                wordFounded = true;
            }
        }

        Assertions.assertTrue(wordFounded);
   }
}