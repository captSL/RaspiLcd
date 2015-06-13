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

public class Point {
    private final int xPosition;
    private final int yPosition;

    /**
     * Creates a new {@code Point} identified by its x and y position.
     *
     * @param x x-value of the point.
     * @param y y-value of the point.
     */
    public Point(final int x, final int y) {
        xPosition = x;
        yPosition = y;
    }

    /**
     * Return the x part of the {@code Point}.
     *
     * @return x part of the {@code Point}.
     */
    public final int getXCoordinate() {
        return xPosition;
    }

    /**
     * Return the y part of the {@code Point}.
     *
     * @return y part of the {@code Point}.
     */

    public final int getYCoordinate() {
        return yPosition;
    }

    @Override
    public final String toString() {
        return "Point{" +
                "xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }
}
