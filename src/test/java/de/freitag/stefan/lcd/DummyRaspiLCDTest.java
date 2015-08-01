package de.freitag.stefan.lcd;

import org.junit.Test;

import java.awt.image.BufferedImage;

/**
 * Test class for {@link DummyRaspiLCD}.
 *
 * @author Stefan Freitag
 */
public final class DummyRaspiLCDTest {

    @Test(expected = IllegalArgumentException.class)
    public void addButtonListenerWithNullThrowsIllegalArgumentException() {
        final DummyRaspiLCD lcd = new DummyRaspiLCD();
        lcd.addButtonListener(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeButtonListenerWithNullThrowsIllegalArgumentException() {
        final DummyRaspiLCD lcd = new DummyRaspiLCD();
        lcd.removeButtonListener(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void drawBmpWithNullPointThrowsIllegalArgumentException() {
        final DummyRaspiLCD lcd = new DummyRaspiLCD();
        lcd.drawBmp(null, new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB));
    }

    @Test(expected = IllegalArgumentException.class)
    public void drawEllipseWithNullPointThrowsIllegalArgumentException() {
        final DummyRaspiLCD lcd = new DummyRaspiLCD();
        lcd.drawEllipse(null, 2, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void drawCircleWithNullPointThrowsIllegalArgumentException() {
        final DummyRaspiLCD lcd = new DummyRaspiLCD();
        lcd.drawCircle(null, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void drawLineWithNullStartThrowsIllegalArgumentException() {
        final DummyRaspiLCD lcd = new DummyRaspiLCD();
        lcd.drawLine(null, new Point(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void drawLineWithNullEndThrowsIllegalArgumentException() {
        final DummyRaspiLCD lcd = new DummyRaspiLCD();
        lcd.drawLine(new Point(1, 1), null);
    }
}