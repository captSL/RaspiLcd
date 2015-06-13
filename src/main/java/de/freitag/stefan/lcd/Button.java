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
 * Buttons available at the RaspiLCD Version 1.
 */
public enum Button {
    /**
     * The left {@code Button}.
     */
    LEFT("Left"),
    /**
     * The center {@code Button}.
     */
    CENTER("CENTER"),
    /**
     * The right {@code Button}.
     */
    RIGHT("RIGHT"),
    /**
     * The up {@code Button}.
     */
    UP("UP"),
    /**
     * The down {@code Button}.
     */
    DOWN("DOWN");

    private final String label;

    /**
     * Create a new {@code Button}.
     *
     * @param text Text that will be assigned to the new {@code Button}.
     */
    Button(final String text) {
        this.label = text;
    }

    /**
     * Return the text assigned to this {@code Button}.
     *
     * @return Text assigned to this {@code Button}.
     */
    @Override
    public final String toString() {
        return label;
    }
}
