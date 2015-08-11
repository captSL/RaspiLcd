package de.freitag.stefan.lcd.demo;

import org.junit.Test;

/**
 * Test class for {@link RaspiButtonTest}.
 *
 * @author Stefan Freitag
 */
public final class RaspiButtonTestTest {

    @Test(expected = IllegalArgumentException.class)
    public void buttonPressedWithNullThrowsIllegalArgumentException() {
        final RaspiButtonTest test = new RaspiButtonTest();
        test.buttonPressed(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buttonReleasedWithNullThrowsIllegalArgumentException() {
        final RaspiButtonTest test = new RaspiButtonTest();
        test.buttonReleased(null);
    }
}