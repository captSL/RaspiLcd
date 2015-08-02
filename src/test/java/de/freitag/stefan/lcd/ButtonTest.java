package de.freitag.stefan.lcd;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Test class for {@link Button}.
 *
 * @author Stefan Freitag
 */
public class ButtonTest {

    @Test
    public void toStringReturnsExpectedValues() throws Exception {
        assertTrue("Center".equalsIgnoreCase(Button.CENTER.toString()));
        assertTrue("Left".equalsIgnoreCase(Button.LEFT.toString()));
        assertTrue("Right".equalsIgnoreCase(Button.RIGHT.toString()));
        assertTrue("Up".equalsIgnoreCase(Button.UP.toString()));
        assertTrue("Down".equalsIgnoreCase(Button.DOWN.toString()));
    }
}