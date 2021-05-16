package se2.ticktackbumm.core.gamelogic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextfieldInputListenerTest {
    TextfieldInputListener textfieldInputListener;

    @BeforeEach
    void setUp() {
        textfieldInputListener = new TextfieldInputListener();
    }

    @AfterEach
    void tearDown() {
        textfieldInputListener = null;
    }

    @Test
    void validInputs() {
        Assertions.assertTrue(textfieldInputListener.isValidInput("HalloDri"));
        Assertions.assertTrue(textfieldInputListener.isValidInput("ticktack"));
    }

    @Test
    void invalidInputs() {
        Assertions.assertFalse(textfieldInputListener.isValidInput("Hallo3"));
        Assertions.assertFalse(textfieldInputListener.isValidInput("Hallo Welt"));
        Assertions.assertFalse(textfieldInputListener.isValidInput("hallo-"));
        Assertions.assertFalse(textfieldInputListener.isValidInput("?"));
    }

    @Test
    void invalidEdgeCaseInputs() {
        Assertions.assertFalse(textfieldInputListener.isValidInput(""));
        Assertions.assertFalse(textfieldInputListener.isValidInput(null));
    }

    @Test
    void validPrefixes() {
        Assertions.assertTrue(textfieldInputListener.hasValidPrefix("gelaufen", "ge"));
        Assertions.assertTrue(textfieldInputListener.hasValidPrefix("gelungen", "ge"));
        Assertions.assertTrue(textfieldInputListener.hasValidPrefix("Schere", "sch"));
        Assertions.assertTrue(textfieldInputListener.hasValidPrefix("Schaufel", "sch"));
        Assertions.assertTrue(textfieldInputListener.hasValidPrefix("unangenehm", "un"));
    }

    @Test
    void invalidPrefixes() {
        Assertions.assertFalse(textfieldInputListener.hasValidPrefix("", "ge"));
        Assertions.assertFalse(textfieldInputListener.hasValidPrefix("klein", "ge"));
        Assertions.assertFalse(textfieldInputListener.hasValidPrefix("foobar", "Sch"));
    }

    @Test
    void validInfixes() {
        Assertions.assertTrue(textfieldInputListener.hasValidInfix("Anleitung", "lei"));
        Assertions.assertTrue(textfieldInputListener.hasValidInfix("Wasserschlauch", "sch"));
    }

    @Test
    void invalidInfixes() {
        Assertions.assertFalse(textfieldInputListener.hasValidInfix("", "ge"));
        Assertions.assertFalse(textfieldInputListener.hasValidInfix("hallo", "ge"));
        Assertions.assertFalse(textfieldInputListener.hasValidInfix("foobar", "Sch"));

    }

    @Test
    void validPostfixes() {
        Assertions.assertTrue(textfieldInputListener.hasValidPostfix("Umleitung", "ung"));
        Assertions.assertTrue(textfieldInputListener.hasValidPostfix("Schulung", "ung"));
        Assertions.assertTrue(textfieldInputListener.hasValidPostfix("Wunsch", "sch"));
        Assertions.assertTrue(textfieldInputListener.hasValidPostfix("kehren", "en"));
        Assertions.assertTrue(textfieldInputListener.hasValidPostfix("gehen", "en"));
    }

    @Test
    void invalidPostfixes() {
        Assertions.assertFalse(textfieldInputListener.hasValidPostfix("Schulung", "en"));
        Assertions.assertFalse(textfieldInputListener.hasValidPostfix("Umleitung", "sch"));
        Assertions.assertFalse(textfieldInputListener.hasValidPostfix("Wunsch", "den"));
        Assertions.assertFalse(textfieldInputListener.hasValidPostfix("kehren", "sch"));
        Assertions.assertFalse(textfieldInputListener.hasValidPostfix("gehen", "be"));
    }

    @Test
    void isInScrambledWord() {
        Assertions.assertTrue(textfieldInputListener.isInScrambledWord("Press", "iexospensr"));
        Assertions.assertTrue(textfieldInputListener.isInScrambledWord("express", "iexospensr"));
        Assertions.assertTrue(textfieldInputListener.isInScrambledWord("expression", "iexospensr"));
        Assertions.assertTrue(textfieldInputListener.isInScrambledWord("Java", "iwjoapva"));
    }

    @Test
    void isNotInScrambledWord() {
        Assertions.assertFalse(textfieldInputListener.isInScrambledWord("", "iwjoapva"));
        Assertions.assertFalse(textfieldInputListener.isInScrambledWord("rep", "iexospensr"));
        Assertions.assertFalse(textfieldInputListener.isInScrambledWord("espresso", "iexospensr"));
        Assertions.assertFalse(textfieldInputListener.isInScrambledWord("foobar", "iwjoapva"));
        Assertions.assertFalse(textfieldInputListener.isInScrambledWord("FooBar", "iwjoapva"));
    }

    @Test
    void umlautWordsInDictionary() {
        Assertions.assertTrue(textfieldInputListener.isInDictionary("hätte"));
        Assertions.assertTrue(textfieldInputListener.isInDictionary("Österreich"));
        Assertions.assertTrue(textfieldInputListener.isInDictionary("könnten"));
        Assertions.assertTrue(textfieldInputListener.isInDictionary("Würde"));
    }

    @Test
    void sensitiveWordsInDictionary() {
        Assertions.assertTrue(textfieldInputListener.isInDictionary("Welt"));
        Assertions.assertTrue(textfieldInputListener.isInDictionary("Baum"));
        Assertions.assertTrue(textfieldInputListener.isInDictionary("egoistisch"));
        Assertions.assertTrue(textfieldInputListener.isInDictionary("klein"));
    }

    @Test
    void insensitiveWordsInDictionary() {
        Assertions.assertTrue(textfieldInputListener.isInDictionary("morgen"));
        Assertions.assertTrue(textfieldInputListener.isInDictionary("Morgen"));
    }

    @Test
    void colloquialWordsInDictionary() {
        Assertions.assertTrue(textfieldInputListener.isInDictionary("Zirbe"));
        Assertions.assertTrue(textfieldInputListener.isInDictionary("Topfen"));
        Assertions.assertTrue(textfieldInputListener.isInDictionary("Obmann"));
    }

    @Test
    void wordsNotInDictionary() {
        Assertions.assertFalse(textfieldInputListener.isInDictionary("afjl"));
        Assertions.assertFalse(textfieldInputListener.isInDictionary("98351"));
        Assertions.assertFalse(textfieldInputListener.isInDictionary("#ASDFIWsfa"));
    }

    @Test
    void edgeCaseWordsNotInDictionary() {
        Assertions.assertFalse(textfieldInputListener.isInDictionary(null));
        Assertions.assertFalse(textfieldInputListener.isInDictionary(""));
    }

    @Test
    void isValidWord() {
        // TODO: add more cases once more checks are supported by the listener
        Assertions.assertTrue(textfieldInputListener.isValidWord("Umleitung"));
        Assertions.assertTrue(textfieldInputListener.isValidWord("schnell"));
        Assertions.assertTrue(textfieldInputListener.isValidWord("Zopf"));
        Assertions.assertTrue(textfieldInputListener.isValidWord("gelb"));
    }
}
