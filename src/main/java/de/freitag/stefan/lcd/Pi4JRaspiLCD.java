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
        initializePins();
        initializePinListener();
    }

    @Override
    public PinState setBacklight(final boolean newStatus) {
        if (newStatus) {
            RaspiLCDPin.LED.high();
        } else {
            RaspiLCDPin.LED.low();
        }
        return RaspiLCDPin.LED.getState();
    }

    @Override
    public void initialize() {
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
            throw new IllegalArgumentException();
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
        LCD.getInstance().printXY(point, character);
    }

    @Override
    public void putString(final Point point, final String string) {
        Point tmp;
        //Font muss bekannt sein
        for (int i = 0; i < string.length(); i++) {
            tmp = new Point(point.getXCoordinate() + (i * Terminal6x8.font_terminal_6x8[1]), point.getYCoordinate());
            LCD.getInstance().printXY(tmp, string.charAt(i));
        }
    }

    @Override
    public void putPixel(final Point point, final boolean color) {
        LCD.getInstance().PutPixel(point, color);
    }

    @Override
    public void drawLine(final Point start, final Point end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException();
        }
        LCD.getInstance().drawLine(start.getXCoordinate(), start.getYCoordinate(), end.getXCoordinate(), end.getYCoordinate());
    }

    @Override
    public void drawCircle(final Point center, final int radius) {
        if (center == null || radius < 0) {
            throw new IllegalArgumentException();
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
                    LCD.getInstance().PutPixel(x, y, true);
                } else {
                    if (fillColor == 0) {
                        LCD.getInstance().PutPixel(x, y, false);
                    } else if (fillColor == 1) {
                        LCD.getInstance().PutPixel(x, y, true);
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getListOfFontNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void drawEllipse(final Point center, final int a, final int b) {
        if (center == null) {
            throw new IllegalArgumentException();
        }
        LCD.getInstance().drawEllipse(center.getXCoordinate(), center.getYCoordinate(), a, b);
    }


    /**
     * Draw a bitmap, base point is the top LEFT corner.
     */
    public void drawBmp(final Point topLeft, final BufferedImage image) {

        //BufferedImage image = ImageIO.read(new File("/some.jpg"));
        byte[][] pixels = new byte[image.getWidth()][];

        for (int x = 0; x < image.getWidth(); x++) {
            pixels[x] = new byte[image.getHeight()];

            for (int y = 0; y < image.getHeight(); y++) {
                pixels[x][y] = (byte) (image.getRGB(x, y) == 0xFFFFFFFF ? 0 : 1);
            }
        }
        drawBmp(topLeft, pixels);
    }

    @Override
    public void drawBmp(final Point topLeft, final byte[][] pixels) {
        if (topLeft == null) {
            throw new IllegalArgumentException();
        }
        LCD.getInstance().drawBitmap(topLeft.getXCoordinate(), topLeft.getYCoordinate(), pixels);
    }

    private void initializePins() {
        final GpioController gpio = GpioFactory.getInstance();
        upPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);
        downPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_UP);
        leftPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_UP);
        rightPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_UP);
        centerPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_UP);
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

    /**
     * Add a {@code ButtonListener} that will be notified about pressed/ released {@code Button}s.
     *
     * @param listener A  {@code ButtonListener}.
     */
    @Override
    public void addButtonListener(final ButtonListener listener) {
        if (listener == null) {
            return;
        }
        if (this.listeners.contains(listener)) {
            return;
        }

        this.listeners.add(listener);

    }

    /**
     * Remove a {@code ButtonListener}.
     *
     * @param listener A  {@code ButtonListener}.
     */
    @Override
    public void removeButtonListener(final ButtonListener listener) {
        if (listener == null) {
            return;
        }
        if (this.listeners.contains(listener)) {
            this.listeners.remove(listener);
        }

    }

    private void fireEvent(final GpioPinDigitalStateChangeEvent event) {
        final Button button = getButtonFromEvent(event);
        if (event.getState().equals(PinState.LOW)) {
            for (ButtonListener listener : this.listeners) {
                listener.buttonPressed(button);
            }
        } else if (event.getState().equals(PinState.HIGH)) {
            for (ButtonListener listener : this.listeners) {
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

