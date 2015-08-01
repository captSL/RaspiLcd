package de.freitag.stefan.lcd;

import org.junit.Test;

import java.awt.image.BufferedImage;

/**
 * Test class for {@link DummyRaspiLCD}.
 *
 * @author Stefan Freitag
 */
public class Pi4JRaspiLCDTest {

    @Test(expected = IllegalArgumentException.class)
    public void addButtonListenerWithNullThrowsIllegalArgumentException() {
        final Pi4JRaspiLCD lcd = new Pi4JRaspiLCD();
        lcd.addButtonListener(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeButtonListenerWithNullThrowsIllegalArgumentException() {
        final Pi4JRaspiLCD lcd = new Pi4JRaspiLCD();
        lcd.removeButtonListener(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void drawBmpWithNullPointThrowsIllegalArgumentException() {
        final Pi4JRaspiLCD lcd = new Pi4JRaspiLCD();
        lcd.drawBmp(null, new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB));
    }

    @Test(expected = IllegalArgumentException.class)
    public void drawEllipseWithNullPointThrowsIllegalArgumentException() {
        final Pi4JRaspiLCD lcd = new Pi4JRaspiLCD();
        lcd.drawEllipse(null, 2, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void drawCircleWithNullPointThrowsIllegalArgumentException() {
        final Pi4JRaspiLCD lcd = new Pi4JRaspiLCD();
        lcd.drawCircle(null, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void drawLineWithNullStartThrowsIllegalArgumentException() {
        final Pi4JRaspiLCD lcd = new Pi4JRaspiLCD();
        lcd.drawLine(null, new Point(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void drawLineWithNullEndThrowsIllegalArgumentException() {
        final Pi4JRaspiLCD lcd = new Pi4JRaspiLCD();
        lcd.drawLine(new Point(1, 1), null);
    }
}