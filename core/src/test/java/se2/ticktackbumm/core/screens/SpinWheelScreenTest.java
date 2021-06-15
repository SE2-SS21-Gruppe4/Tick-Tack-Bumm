package se2.ticktackbumm.core.screens;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpinWheelScreenTest {

    SpinWheelScreen wheelScreen;


    @BeforeEach
    void setUp() {
        wheelScreen = new SpinWheelScreen("");

    }

    @AfterEach
    void tearDown() {
        wheelScreen = null;
    }

    @Test
    void setDegreeIfNumberGreaterThan360() {
        float value = 930;
        Assertions.assertEquals(210, wheelScreen.setDegree(value));
    }

    @Test
    void setDegreeIfNumberLowerThan360() {
        float value = 133f;
        Assertions.assertEquals(133, wheelScreen.setDegree(value));
    }

    @Test
    void getSpinSpeedIfNumberGreaterThan360() {
        float value = 1479f;
        Assertions.assertEquals(4.0, Math.round(wheelScreen.getSpinSpeed(value)));
    }

    @Test
    void getSpinSpeedIfNumberLowerThan360() {
        float value = 151f;
        Assertions.assertEquals(1, wheelScreen.getSpinSpeed(value));
    }
}
