/*
 * Copyright (C) 2013  Stefan Freitag
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.freitag.stefan.lcd;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import de.freitag.stefan.lcd.fonts.Terminal6x8;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Pi4JRaspiLCD implements RaspiLCD {

    private final List<ButtonListener> listeners;
    private GpioPinDigitalInput upPin;
    private GpioPinDigitalInput downPin;
    private GpioPinDigitalInput leftPin;
    private GpioPinDigitalInput rightPin;
    private GpioPinDigitalInput centerPin;


    /**
     * Color of the pen.
     */
    private boolean penColor = true;
    /**
     * Fill color.
     */
    private int fillColor = 0;
    /**
     *
     */
    private int fontNumber = 0;

    /**
     * Constructor for this class.
     */
    public Pi4JRaspiLCD() {
        super();
        listeners = new ArrayList<>();

    }

    /**
     * Return the {@link Logger} for this class.
     *
     * @return the {@link Logger} for this class.
     */
    @SuppressWarnings("unused")
    private static Logger getLogger() {
        return LogManager.getLogger(Pi4JRaspiLCD.class.getCanonicalName());
    }

    @Override
    public PinState setBacklight(final boolean status) {
        if (status) {
            RaspiLCDPin.LED.high();
        } else {
            RaspiLCDPin.LED.low();
        }
        return RaspiLCDPin.LED.getState();
    }

    @Override
    public void initialize() {
        this.initializePins();
        this.initializePinListener();
        LCD.getInstance().init();
    }

    @Override
    public void shutdownLCD() {
        LCD.getInstance().clearScreen();
        setBacklight(false);
    }

    @Override
    public void setContrast(final int contrast) {
        if (contrast < 0 || contrast > 63) {
            throw new IllegalArgumentException("Contrast is out of range. Allowed value [0,63]. Received: " + contrast);
        }
        LCD.getInstance().writeCommand((byte) 0x81);
        LCD.getInstance().writeCommand((byte) contrast);
    }

    @Override
    public void clear() {
        LCD.getInstance().clearScreen();
    }

    @Override
    public void putCharacter(final Point point, final Character character) {
        if (point == null) {
            throw new IllegalArgumentException("Point is null");
        }
        if (character == null) {
            throw new IllegalArgumentException("Character is null");
        }
        LCD.getInstance().printXY(point, character);
    }

    @Override
    public void putString(final Point point, final String text) {
        if (point == null) {
            throw new IllegalArgumentException("Point is null");
        }
        if (text == null) {
            throw new IllegalArgumentException("Text is null");
        }
        Point tmp;
        //Font muss bekannt sein
        for (int i = 0; i < text.length(); i++) {
            tmp = new Point(point.getXCoordinate() + (i * Terminal6x8.BYTES[1]), point.getYCoordinate());
            LCD.getInstance().printXY(tmp, text.charAt(i));
        }
    }

    @Override
    public void putPixel(final Point point, final boolean color) {
        if (point == null) {
            throw new IllegalArgumentException("Point is null");
        }
        LCD.getInstance().putPixel(point, color);
    }

    @Override
    public void drawLine(final Point start, final Point end) {
        if (start == null) {
            throw new IllegalArgumentException("Start point is null");
        }
        if (end == null) {
            throw new IllegalArgumentException("End point is null");
        }
        LCD.getInstance().drawLine(start.getXCoordinate(), start.getYCoordinate(), end.getXCoordinate(), end.getYCoordinate());
    }

    @Override
    public void drawCircle(final Point center, final int radius) {
        if (center == null) {
            throw new IllegalArgumentException("Point is null");
        }
        if (radius < 0) {
            throw new IllegalArgumentException("Radius must be equal to or greater than 0");
        }
        LCD.getInstance().drawCircle(center.getXCoordinate(), center.getYCoordinate(), radius);
    }

    @Override
    public void drawRectangle(final Point upperLeft, final Point lowerRight, final int lineWidth) {
        if (upperLeft == null || lowerRight == null || lineWidth < 0) {
            throw new IllegalArgumentException();
        }

        int y = upperLeft.getYCoordinate();

        while (y <= lowerRight.getYCoordinate()) {
            int x = upperLeft.getXCoordinate();
            while (x <= lowerRight.getXCoordinate()) {

                if (y < upperLeft.getYCoordinate() + lineWidth
                        || y > lowerRight.getYCoordinate() - lineWidth
                        || x < upperLeft.getXCoordinate() + lineWidth
                        || x > lowerRight.getXCoordinate() - lineWidth) {
                    LCD.getInstance().putPixel(x, y, true);
                } else {
                    if (fillColor == 0) {
                        LCD.getInstance().putPixel(x, y, false);
                    } else if (fillColor == 1) {
                        LCD.getInstance().putPixel(x, y, true);
                    }
                }
                x++;
            }
            y++;
        }
    }

    @Override
    public void writeFramebuffer() {
        LCD.getInstance().writeFramebuffer();
    }

    @Override
    public void setFillColor(final int color) {
        if (color < -1 || color > 1) {
            throw new IllegalArgumentException();
        }
        LCD.getInstance().setFillColor(color);
    }

    @Override
    public void setPenColor(final boolean color) {
        LCD.getInstance().setPenColor(color);
    }

    @Override
    public void setFont(String fontName) {
        //TODO
    }

    @Override
    public List<String> getListOfFontNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void drawEllipse(final Point center, final int a, final int b) {
        if (center == null) {
            throw new IllegalArgumentException("Point is null");
        }
        LCD.getInstance().drawEllipse(center.getXCoordinate(), center.getYCoordinate(), a, b);
    }

    @Override
    public void drawBmp(final Point topLeft, final BufferedImage image) {
        if (topLeft == null) {
            throw new IllegalArgumentException("Point is null");
        }
        if (image == null) {
            throw new IllegalArgumentException("Image is null");
        }


        final BufferedImage scaledImage = new BufferedImage(LCD.WIDTH, LCD.HEIGHT, image.getType());
        final Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(image, 0, 0, LCD.WIDTH, LCD.HEIGHT, null);
        g2d.dispose();

        final byte[][] pixels = new byte[scaledImage.getWidth()][];

        for (int x = 0; x < scaledImage.getWidth(); x++) {
            pixels[x] = new byte[scaledImage.getHeight()];

            for (int y = 0; y < scaledImage.getHeight(); y++) {
                pixels[x][y] = (byte) (scaledImage.getRGB(x, y) == 0xFFFFFFFF ? 0 : 1);
            }
        }
        this.drawBmp(topLeft, pixels);
    }

    @Override
    public void drawBmp(final Point topLeft, final byte[][] pixels) {
        if (topLeft == null) {
            throw new IllegalArgumentException("Point is null");
        }
        LCD.getInstance().drawBitmap(topLeft.getXCoordinate(), topLeft.getYCoordinate(), pixels);
    }

    private void initializePins() {
        final GpioController gpio = GpioFactory.getInstance();
        this.upPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);
        this.downPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_UP);
        this.leftPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_UP);
        this.rightPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_UP);
        this.centerPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_UP);
    }

    private void initializePinListener() {
        final GpioPinListenerDigital pinListener = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(final GpioPinDigitalStateChangeEvent event) {
                Pi4JRaspiLCD.this.fireEvent(event);
            }
        };
        upPin.addListener(pinListener);
        downPin.addListener(pinListener);
        leftPin.addListener(pinListener);
        rightPin.addListener(pinListener);
        centerPin.addListener(pinListener);
    }

    @Override
    public void addButtonListener(final ButtonListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("ButtonListener is null");
        }
        if (this.listeners.contains(listener)) {
            getLogger().warn("ButtonListener was already added before. Not adding again");
            return;
        }

        this.listeners.add(listener);

    }

    @Override
    public void removeButtonListener(final ButtonListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("ButtonListener is null");
        }
        if (this.listeners.contains(listener)) {
            this.listeners.remove(listener);
        }

    }

    private void fireEvent(final GpioPinDigitalStateChangeEvent event) {
        final Button button = getButtonFromEvent(event);
        if (event.getState().equals(PinState.LOW)) {
            for (final ButtonListener listener : this.listeners) {
                listener.buttonPressed(button);
            }
        } else if (event.getState().equals(PinState.HIGH)) {
            for (final ButtonListener listener : this.listeners) {
                listener.buttonReleased(button);
            }
        }

    }

    private Button getButtonFromEvent(final GpioPinDigitalStateChangeEvent event) {

        if (event.getPin().getName().equalsIgnoreCase(RaspiPin.GPIO_04.getName())) {
            return Button.RIGHT;
        } else if (event.getPin().getName().equalsIgnoreCase(RaspiPin.GPIO_03.getName())) {
            return Button.CENTER;
        } else if (event.getPin().getName().equalsIgnoreCase(RaspiPin.GPIO_02.getName())) {
            return Button.LEFT;
        } else if (event.getPin().getName().equalsIgnoreCase(RaspiPin.GPIO_00.getName())) {
            return Button.UP;
        } else if (event.getPin().getName().equalsIgnoreCase(RaspiPin.GPIO_05.getName())) {
            return Button.DOWN;
        }
        return null;

    }
}

