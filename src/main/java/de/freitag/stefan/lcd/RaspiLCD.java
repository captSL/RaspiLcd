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

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Definition of the methods that a RaspiLCD implementation needs to support.
 * @author Stefan Freitag
 */
public interface RaspiLCD {

    /**
     * Initialize the RaspiLCD hardware.
     */
    void initialize();

    /**
     * Clear the LC display and turn off the backlight.
     */
    void shutdownLCD();

    /**
     * Set the contrast of the display.
     *
     * @param contrast The new contrast. Valid range: 0 - 63.
     * @throws IllegalArgumentException if {@code contrast} is not range.
     */
    void setContrast(int contrast);

    /**
     * Clear the display.
     */
    void clear();

    /**
     * Draw a bitmap, base point is the top left corner.
     *
     * @param topLeft top left point of the image on the display.
     * @param image   buffered image to display.
     * @throws IllegalArgumentException if {@code topLeft} is {@code null}.
     */
    void drawBmp(Point topLeft, BufferedImage image);

    /**
     * Draw a bitmap, base point is the top left corner.
     *
     * @param topLeft top left point of the image on the display.
     * @param pixels  pixel array to display,
     * @throws IllegalArgumentException if {@code topLeft} or {@code pixels} is {@code null}.
     */
    void drawBmp(Point topLeft, byte[][] pixels);

    /**
     * Print a {@link Character} at a specific {@link Point}.
     *
     * @param point     top left point of the {@link Character}
     * @param character The {@link Character} to display.
     * @throws IllegalArgumentException if {@code point} or {@code character} is {@code null}.
     */
    void putCharacter(Point point, Character character);

    /**
     * Print a text string at a specific {@link Point}.
     *
     * @param point  top left point of the string.
     * @param text The text string to print.
     * @throws IllegalArgumentException if {@code point} or {@code text} is {@code null}.
     */
    void putString(Point point, String text);

    /**
     * Put a pixel at the specified point.
     *
     * @param point Point to display/ clear the pixel.
     * @param color Color of the pixel (true: black, false: white)
     * @throws IllegalArgumentException if {@code point} is {@code null}.
     */
    void putPixel(Point point, boolean color);

    /**
     * Enable/ disable the backlight of the display.
     *
     * @param status The new status of the backlight. {@code true} enable backlight, {@code false} disable backlight.
     * @return Status of the backlight.
     */
    PinState setBacklight(boolean status);

    /**
     * Draw a line defined by start and end point.
     *
     * @param start Start point of the line.
     * @param end   End point of the line.
     * @throws IllegalArgumentException if {@code start} or {@code end} is {@code null}.
     */
    void drawLine(Point start, Point end);

    /**
     * Draw a circle defined by the CENTER point and a radius.
     *
     * @param center The CENTER point of the circle.
     * @param radius The radius of the circle. Allowed values greater than 0.
     * @throws IllegalArgumentException if {@code center} is {@code null} or radius negative.
     */
    void drawCircle(Point center, int radius);

    /**
     * @param upperLeft  Upper LEFT point of the rectangle
     * @param lowerRight Lower RIGHT point of the rectangle
     * @param lineWidth  Line width in pixel
     */
    void drawRectangle(Point upperLeft, Point lowerRight, int lineWidth);

    /**
     * Write the framebuffer data to the display.
     */
    void writeFramebuffer();

    /**
     * Set fill color.
     * -1=transparent, 0=white, 1=black
     *
     * @param color Value for the fill color.
     */
    void setFillColor(int color);

    /**
     * Set the pen color.
     *
     * @param color true= black, false= white.
     */
    void setPenColor(boolean color);

    /**
     * Sets the font to use for the LC-display.
     *
     * @param fontName The name of the font.
     */
    void setFont(String fontName);

    /**
     * Returns the names of the supported fonts.
     *
     * @return List of supported font names.
     */
    List<String> getListOfFontNames();


    /**
     * Draw an ellipse.
     *
     * @param center Center point
     * @param a      Length semi-axis 1.
     * @param b      Length semi-axis 2.
     * @throws IllegalArgumentException if {@code center} is {@code null}.
     */
    void drawEllipse(Point center, int a, int b);

    /**
     * Add a {@code ButtonListener} that will be notified about pressed/ released {@code Button}s.
     *
     * @param listener A  {@code ButtonListener}.
     * @return {@code true} if listener was added successfully.
     * @throws IllegalArgumentException if {@code listener} is {@code null}.
     */
    boolean addButtonListener(ButtonListener listener);

    /**
     * Remove a {@link ButtonListener} from the list of registered listeners.
     *
     * @param listener {@link ButtonListener} to delete.
     * @throws IllegalArgumentException if {@code listener} is {@code null}.
     */
    void removeButtonListener(ButtonListener listener);
}
