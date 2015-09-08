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

    @Test(expected = IllegalArgumentException.class)
    public void putPixelWithInvalidXPositionLeftThrowsIllegalArgumentException() {
        LCD.getInstance().putPixel(-1, 0, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putPixelWithInvalidYPositionLeftThrowsIllegalArgumentException() {
        LCD.getInstance().putPixel(1, -1, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putPixelWithInvalidXPositionRightThrowsIllegalArgumentException() {
        LCD.getInstance().putPixel(200, 0, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putPixelWithInvalidYPositionRightThrowsIllegalArgumentException() {
        LCD.getInstance().putPixel(1, 200, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putPixelWithNullPointThrowsIllegalArgumentException() {
        LCD.getInstance().putPixel(null, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void drawCircleWithNegativeRadiusThrowsIllegalArgumentException() {
        LCD.getInstance().drawCircle(0, 0, -10);
    }
}