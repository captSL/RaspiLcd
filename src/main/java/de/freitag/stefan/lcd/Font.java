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

import de.freitag.stefan.lcd.fonts.Lucida10x16;

enum Font {
    LUCIDA10x16;

    private String font;

    public char[] getFont(final String font) {
        this.font = font;
        if (font.equalsIgnoreCase("LUCIDA10x16"))
            return Lucida10x16.BYTES;
        return null;
    }
}
