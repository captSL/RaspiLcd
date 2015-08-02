package de.freitag.stefan.lcd;

import org.junit.Test;

/**
 * Test class for {@link LCD}.
 *
 * @author Stefan Freitag
 */
public final class LCDTest {

    @Test(expected = IllegalArgumentException.class)
    public void drawBitmapWithNullByteMatrixThrowsIllegalArgumentException() {
        LCD.getInstance().drawBitmap(0, 0, null);
    }


}