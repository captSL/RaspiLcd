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

import com.pi4j.io.gpio.PinState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DummyRaspiLCD implements RaspiLCD {

    private final List<ButtonListener> listeners;

    /**
     * Create a new {@link DummyRaspiLCD}.
     */
    public DummyRaspiLCD() {
        super();
        this.listeners = new ArrayList<>();
    }

    /**
     * Return the {@link Logger} for this class.
     *
     * @return the {@link Logger} for this class.
     */
    private static Logger getLogger() {
        return LogManager.getLogger(DummyRaspiLCD.class.getCanonicalName());
    }

    @Override
    public PinState setBacklight(final boolean status) {
        getLogger().info("New Backlight status: " + status);
        return PinState.HIGH;
    }

    @Override
    public void initialize() {
        getLogger().info("Initializing LCD");
    }

    @Override
    public void shutdownLCD() {
        getLogger().info("Shutting down LCD");
        this.setBacklight(false);
    }

    @Override
    public void setContrast(final int contrast) {
        if (contrast < 0 || contrast > 63) {
            throw new IllegalArgumentException("Contrast is out of range. Allowed value [0,63]. Received: " + contrast);
        }
        getLogger().info("Setting contrast to " + contrast);
    }

    @Override
    public void clear() {
        getLogger().info("Clear screen");
    }

    @Override
    public void putCharacter(final Point point, final Character character) {
        if (point == null) {
            throw new IllegalArgumentException("Point is null");
        }
        if (character == null) {
            throw new IllegalArgumentException("Character is null");
        }
        getLogger().info("Put char " + character + " at point " + point);
    }

    @Override
    public void putString(final Point point, final String text) {
        if (point == null) {
            throw new IllegalArgumentException("Point is null");
        }
        if (text == null) {
            throw new IllegalArgumentException("Text is null");
        }
        getLogger().info("Put text " + text + " at point " + point);
    }

    @Override
    public void putPixel(final Point point, final boolean color) {
        if (point == null) {
            throw new IllegalArgumentException("Point is null");
        }
        getLogger().info("Put pixel " + color + " at point " + point);
    }

    @Override
    public void drawLine(final Point start, final Point end) {
        if (start == null) {
            throw new IllegalArgumentException("Start point is null");
        }
        if (end == null) {
            throw new IllegalArgumentException("End point is null");
        }
        getLogger().info("Draw line from " + start + " to " + end);
    }

    @Override
    public void drawCircle(final Point center, final int radius) {
        if (center == null) {
            throw new IllegalArgumentException("Point is null");
        }
        if (radius < 0) {
            throw new IllegalArgumentException("Radius must be equal to or greater than 0");
        }
        getLogger().info("Draw circle with center " + center + " and radius " + radius);
    }

    @Override
    public void drawRectangle(final Point upperLeft, final Point lowerRight, final int lineWidth) {
        if (upperLeft == null || lowerRight == null || lineWidth < 0) {
            throw new IllegalArgumentException();
        }
        getLogger().info("Draw rectangle with upper left " + upperLeft + " and lower right " + lowerRight);
    }

    @Override
    public void writeFramebuffer() {
        getLogger().info("Write framebuffer");
    }

    @Override
    public void setFillColor(final int color) {
        if (color < -1 || color > 1) {
            throw new IllegalArgumentException();
        }
        getLogger().info("Set fill color: " + color);
    }

    @Override
    public void setPenColor(final boolean color) {
        getLogger().info("Set pen color: " + color);
    }

    @Override
    public void setFont(final String fontName) {
        getLogger().info("Set font to " + fontName);
    }

    @Override
    public List<String> getListOfFontNames() {
        return Collections.emptyList();
    }

    @Override
    public void drawEllipse(final Point center, final int a, final int b) {
        if (center == null) {
            throw new IllegalArgumentException("Point is null");
        }
        getLogger().info("Draw ellipse with center " + center);
    }

    @Override
    public void drawBmp(final Point topLeft, final BufferedImage image) {
        if (topLeft == null) {
            throw new IllegalArgumentException("Point is null");
        }
        getLogger().info("Draw image at position " + topLeft);
    }

    @Override
    public void drawBmp(final Point topLeft, final byte[][] pixels) {
        if (topLeft == null) {
            throw new IllegalArgumentException("Point is null");
        }
        getLogger().info("Draw image at position " + topLeft);
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
}
