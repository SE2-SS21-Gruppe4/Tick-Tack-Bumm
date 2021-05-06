package se2.ticktackbumm.core.screens;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// TODO: Refactor into correct test module
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
    void validWords() {
        Assertions.assertTrue(textfieldInputListener.isValidInput("HalloDri"));
        Assertions.assertTrue(textfieldInputListener.isValidInput("ticktack"));
    }

    @Test
    void invalidWords() {
        Assertions.assertFalse(textfieldInputListener.isValidInput("Hallo3"));
        Assertions.assertFalse(textfieldInputListener.isValidInput("Hallo Welt"));
        Assertions.assertFalse(textfieldInputListener.isValidInput("hallo-"));
        Assertions.assertFalse(textfieldInputListener.isValidInput("?"));
        Assertions.assertFalse(textfieldInputListener.isValidInput(""));
        Assertions.assertFalse(textfieldInputListener.isValidInput(null));
    }
}
