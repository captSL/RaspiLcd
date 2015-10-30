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
 * @author Stefan Freitag
 */
public enum Button {
    /**
     * The left {@code Button}.
     */
    LEFT("Left"),
    /**
     * The center {@code Button}.
     */
    CENTER("Center"),
    /**
     * The right {@code Button}.
     */
    RIGHT("Right"),
    /**
     * The up {@code Button}.
     */
    UP("Up"),
    /**
     * The down {@code Button}.
     */
    DOWN("Down"),
    /**
     * The corner {@code Button}. Only on revision two of the board.
     */
	CORNER("Corner");	

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
        return this.label;
    }
}
