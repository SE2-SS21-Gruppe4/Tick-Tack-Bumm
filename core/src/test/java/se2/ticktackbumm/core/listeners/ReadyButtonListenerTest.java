package se2.ticktackbumm.core.listeners;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadyButtonListenerTest {
    ReadyButtonListener readyButtonListener;
    @BeforeEach
    void setUp() {
        readyButtonListener = new ReadyButtonListener();
    }

    @AfterEach
    void tearDown() {
        readyButtonListener = null;
    }

    @Test
    void isValidName() {
        Assertions.assertTrue(readyButtonListener.isValidName("Daniel"));
    }

    @Test
    void isNoValidName() {
        Assertions.assertFalse(readyButtonListener.isValidName("Danie%%%%%%%%l"));
    }

    @Test
    void isNull() {
        Assertions.assertFalse(readyButtonListener.isValidName(null));
    }
}