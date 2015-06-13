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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PointTest {

    @Test
    public final void testToString() {
        Point point = new Point(-1, 2);
        assertTrue(point.toString().contains("-1"));
        assertTrue(point.toString().contains("2"));
    }

    @Test
    public final void testGetXCoordinate() {
        Point point = new Point(-1, 2);
        assertEquals(-1, point.getXCoordinate());
    }

    @Test
    public final void testGetYCoordinate() {
        Point point = new Point(-1, 2);
        assertEquals(2, point.getYCoordinate());
    }
}
