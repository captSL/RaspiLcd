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
 * Allows receiving information about released and pressed {@link Button}s.
 */
public interface ButtonListener {
    /**
     * Called when a {@link Button} on the RaspiLCD was pressed.
     *
     * @param button The pressed {@link Button}.
     * @throws IllegalArgumentException if {@code button} is {@code null}.
     */
    void buttonPressed(Button button);

    /**
     * Called when a {@link Button} on the RaspiLCD was released.
     *
     * @param button The released {@link Button}.
     * @throws IllegalArgumentException if {@code button} is {@code null}.
     */
    void buttonReleased(Button button);
}
