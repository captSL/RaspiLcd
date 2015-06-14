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

/**
 * A point consisting of an x and y position.
 *
 * @author Stefan Freitag
 */
public final class Point {
    /**
     * The x position of this {@link Point}.
     */
    private final int xPosition;
    /**
     * The y position of this {@link Point}.
     */
    private final int yPosition;

    /**
     * Creates a new {@code Point} identified by its x and y position.
     *
     * @param xPosition x-value of the point.
     * @param yPosition y-value of the point.
     */
    public Point(final int xPosition, final int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    /**
     * Return the x part of the {@code Point}.
     *
     * @return x part of the {@code Point}.
     */
    public int getXCoordinate() {
        return this.xPosition;
    }

    /**
     * Return the y part of the {@code Point}.
     *
     * @return y part of the {@code Point}.
     */

    public int getYCoordinate() {
        return this.yPosition;
    }

    @Override
    public String toString() {
        return "Point{" +
                "xPosition=" + this.xPosition +
                ", yPosition=" + this.yPosition +
                '}';
    }
}
