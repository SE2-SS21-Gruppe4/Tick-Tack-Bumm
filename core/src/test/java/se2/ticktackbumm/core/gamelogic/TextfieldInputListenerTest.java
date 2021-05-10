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
    void validPostfixes() {
        Assertions.assertTrue(textfieldInputListener.validPostfix("Umleitung", "ung"));
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
    void validUmlautWords() {
        Assertions.assertTrue(textfieldInputListener.isValidWord("hätte"));
        Assertions.assertTrue(textfieldInputListener.isValidWord("Österreich"));
        Assertions.assertTrue(textfieldInputListener.isValidWord("könnten"));
        Assertions.assertTrue(textfieldInputListener.isValidWord("Würde"));
    }

    @Test
    void validSensitiveWords() {
        Assertions.assertTrue(textfieldInputListener.isValidWord("Welt"));
        Assertions.assertTrue(textfieldInputListener.isValidWord("Baum"));
        Assertions.assertTrue(textfieldInputListener.isValidWord("egoistisch"));
        Assertions.assertTrue(textfieldInputListener.isValidWord("klein"));
    }

    @Test
    void validInsensitiveWords() {
        Assertions.assertTrue(textfieldInputListener.isValidWord("morgen"));
        Assertions.assertTrue(textfieldInputListener.isValidWord("Morgen"));
    }

    @Test
    void validColloquialWords() {
        Assertions.assertTrue(textfieldInputListener.isValidWord("Zirbe"));
        Assertions.assertTrue(textfieldInputListener.isValidWord("Topfen"));
        Assertions.assertTrue(textfieldInputListener.isValidWord("Obmann"));
    }

    @Test
    void invalidWords() {
        Assertions.assertFalse(textfieldInputListener.isValidWord("afjl"));
        Assertions.assertFalse(textfieldInputListener.isValidWord("98351"));
        Assertions.assertFalse(textfieldInputListener.isValidWord("#ASDFIWsfa"));
    }

    @Test
    void invalidEdgeCaseWords() {
        Assertions.assertFalse(textfieldInputListener.isValidWord(null));
        Assertions.assertFalse(textfieldInputListener.isValidWord(""));
    }
}
