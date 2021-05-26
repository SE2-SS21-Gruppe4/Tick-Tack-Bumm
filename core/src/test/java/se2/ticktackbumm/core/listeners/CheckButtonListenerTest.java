package se2.ticktackbumm.core.listeners;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckButtonListenerTest {
    CheckButtonListener checkButtonListener;

    @BeforeEach
    void setUp() {
        checkButtonListener = new CheckButtonListener();
    }

    @AfterEach
    void tearDown() {
        checkButtonListener = null;
    }

    @Test
    void validInputs() {
        Assertions.assertTrue(checkButtonListener.isValidInput("HalloDri"));
        Assertions.assertTrue(checkButtonListener.isValidInput("ticktack"));
    }

    @Test
    void invalidInputs() {
        Assertions.assertFalse(checkButtonListener.isValidInput("Hallo3"));
        Assertions.assertFalse(checkButtonListener.isValidInput("Hallo Welt"));
        Assertions.assertFalse(checkButtonListener.isValidInput("hallo-"));
        Assertions.assertFalse(checkButtonListener.isValidInput("?"));
    }

    @Test
    void invalidEdgeCaseInputs() {
        Assertions.assertFalse(checkButtonListener.isValidInput(""));
        Assertions.assertFalse(checkButtonListener.isValidInput(null));
    }

    @Test
    void validPrefixes() {
        Assertions.assertTrue(checkButtonListener.hasValidPrefix("gelaufen", "ge"));
        Assertions.assertTrue(checkButtonListener.hasValidPrefix("gelungen", "ge"));
        Assertions.assertTrue(checkButtonListener.hasValidPrefix("Schere", "sch"));
        Assertions.assertTrue(checkButtonListener.hasValidPrefix("Schaufel", "sch"));
        Assertions.assertTrue(checkButtonListener.hasValidPrefix("unangenehm", "un"));
    }

    @Test
    void invalidPrefixes() {
        Assertions.assertFalse(checkButtonListener.hasValidPrefix("", "ge"));
        Assertions.assertFalse(checkButtonListener.hasValidPrefix("klein", "ge"));
        Assertions.assertFalse(checkButtonListener.hasValidPrefix("foobar", "Sch"));
    }

    @Test
    void validInfixes() {
        Assertions.assertTrue(checkButtonListener.hasValidInfix("Anleitung", "lei"));
        Assertions.assertTrue(checkButtonListener.hasValidInfix("Wasserschlauch", "sch"));
    }

    @Test
    void invalidInfixes() {
        Assertions.assertFalse(checkButtonListener.hasValidInfix("", "ge"));
        Assertions.assertFalse(checkButtonListener.hasValidInfix("hallo", "ge"));
        Assertions.assertFalse(checkButtonListener.hasValidInfix("foobar", "Sch"));

    }

    @Test
    void validPostfixes() {
        Assertions.assertTrue(checkButtonListener.hasValidPostfix("Umleitung", "ung"));
        Assertions.assertTrue(checkButtonListener.hasValidPostfix("Schulung", "ung"));
        Assertions.assertTrue(checkButtonListener.hasValidPostfix("Wunsch", "sch"));
        Assertions.assertTrue(checkButtonListener.hasValidPostfix("kehren", "en"));
        Assertions.assertTrue(checkButtonListener.hasValidPostfix("gehen", "en"));
    }

    @Test
    void invalidPostfixes() {
        Assertions.assertFalse(checkButtonListener.hasValidPostfix("Schulung", "en"));
        Assertions.assertFalse(checkButtonListener.hasValidPostfix("Umleitung", "sch"));
        Assertions.assertFalse(checkButtonListener.hasValidPostfix("Wunsch", "den"));
        Assertions.assertFalse(checkButtonListener.hasValidPostfix("kehren", "sch"));
        Assertions.assertFalse(checkButtonListener.hasValidPostfix("gehen", "be"));
    }

    @Test
    void isInScrambledWord() {
        Assertions.assertTrue(checkButtonListener.isInScrambledWord("Press", "iexospensr"));
        Assertions.assertTrue(checkButtonListener.isInScrambledWord("express", "iexospensr"));
        Assertions.assertTrue(checkButtonListener.isInScrambledWord("expression", "iexospensr"));
        Assertions.assertTrue(checkButtonListener.isInScrambledWord("Java", "iwjoapva"));
    }

    @Test
    void isNotInScrambledWord() {
        Assertions.assertFalse(checkButtonListener.isInScrambledWord("", "iwjoapva"));
        Assertions.assertFalse(checkButtonListener.isInScrambledWord("rep", "iexospensr"));
        Assertions.assertFalse(checkButtonListener.isInScrambledWord("espresso", "iexospensr"));
        Assertions.assertFalse(checkButtonListener.isInScrambledWord("foobar", "iwjoapva"));
        Assertions.assertFalse(checkButtonListener.isInScrambledWord("FooBar", "iwjoapva"));
    }

    @Test
    void umlautWordsInDictionary() {
        Assertions.assertTrue(checkButtonListener.isInDictionary("hätte"));
        Assertions.assertTrue(checkButtonListener.isInDictionary("Österreich"));
        Assertions.assertTrue(checkButtonListener.isInDictionary("könnten"));
        Assertions.assertTrue(checkButtonListener.isInDictionary("Würde"));
    }

    @Test
    void sensitiveWordsInDictionary() {
        Assertions.assertTrue(checkButtonListener.isInDictionary("Welt"));
        Assertions.assertTrue(checkButtonListener.isInDictionary("Baum"));
        Assertions.assertTrue(checkButtonListener.isInDictionary("egoistisch"));
        Assertions.assertTrue(checkButtonListener.isInDictionary("klein"));
    }

    @Test
    void insensitiveWordsInDictionary() {
        Assertions.assertTrue(checkButtonListener.isInDictionary("morgen"));
        Assertions.assertTrue(checkButtonListener.isInDictionary("Morgen"));
    }

    @Test
    void colloquialWordsInDictionary() {
        Assertions.assertTrue(checkButtonListener.isInDictionary("Zirbe"));
        Assertions.assertTrue(checkButtonListener.isInDictionary("Topfen"));
        Assertions.assertTrue(checkButtonListener.isInDictionary("Obmann"));
    }

    @Test
    void wordsNotInDictionary() {
        Assertions.assertFalse(checkButtonListener.isInDictionary("afjl"));
        Assertions.assertFalse(checkButtonListener.isInDictionary("98351"));
        Assertions.assertFalse(checkButtonListener.isInDictionary("#ASDFIWsfa"));
    }

    @Test
    void edgeCaseWordsNotInDictionary() {
        Assertions.assertFalse(checkButtonListener.isInDictionary(null));
        Assertions.assertFalse(checkButtonListener.isInDictionary(""));
    }

// TODO: fix tests with new method
//    @Test
//    void isValidWord() {
//        // TODO: add more cases once more checks are supported by the listener
//        Assertions.assertTrue(textfieldInputListener.isValidWord("Umleitung"));
//        Assertions.assertTrue(textfieldInputListener.isValidWord("schnell"));
//        Assertions.assertTrue(textfieldInputListener.isValidWord("Zopf"));
//        Assertions.assertTrue(textfieldInputListener.isValidWord("gelb"));
//    }
}
