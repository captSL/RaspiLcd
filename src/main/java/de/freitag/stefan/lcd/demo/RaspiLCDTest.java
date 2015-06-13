package de.freitag.stefan.lcd.demo;
/*
 * Copyright (C) 2013-2015  Stefan Freitag
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

import de.freitag.stefan.lcd.Pi4JRaspiLCD;
import de.freitag.stefan.lcd.Point;
import de.freitag.stefan.lcd.RaspiLCD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class RaspiLCDTest {
    public static final int DELAY_IN_MS = 1500;
    private static final Logger LOG = LogManager.getLogger(RaspiLCDTest.class.getCanonicalName());
    private final static RaspiLCD iFace = new Pi4JRaspiLCD();

    private static void drawLineTopLeftBottomRight() {
        LOG.info("Draw line from top left to bottom right.");
        final Point start = new Point(1, 1);
        final Point end = new Point(131, 63);
        iFace.drawLine(start, end);
        iFace.writeFramebuffer();
    }

    private static void drawLineBottomLeftTopRight() {
        LOG.info("Draw line from bottom left to top right.");
        final Point start = new Point(1, 63);
        final Point end = new Point(131, 1);
        iFace.drawLine(start, end);
        iFace.writeFramebuffer();
    }

    private static void drawRectangle() {
        LOG.info("Draw rectangle.");
        final Point topLeft = new Point(1, 1);
        final Point bottomRight = new Point(64, 32);
        final int width = 1;
        iFace.drawRectangle(topLeft, bottomRight, width);
        iFace.writeFramebuffer();
    }

    private static void drawCenteredCircle() {
        LOG.info("Draw centered circle.");
        final Point center = new Point(64, 32);
        iFace.drawCircle(center, 30);
        iFace.writeFramebuffer();
    }

    private static void drawCenteredEllipse() {
        LOG.info("Draw centered ellipse.");
        final Point center = new Point(64, 32);
        iFace.drawEllipse(center, 10, 20);
        iFace.writeFramebuffer();
    }

    private static void drawCharacter() {
        LOG.info("Draw character.");
        final Point point = new Point(64, 32);
        iFace.putCharacter(point, 'A');
        iFace.writeFramebuffer();
    }

    private static void drawString() {
        LOG.info("Draw string.");
        final Point point = new Point(1, 1);
        iFace.putString(point, "My RaspiLCD works!");
        iFace.writeFramebuffer();
    }

    public static void main(String args[]) throws InterruptedException {

        LOG.info("Initializing display.");
        iFace.initialize();

        drawLineTopLeftBottomRight();
        Thread.sleep(DELAY_IN_MS);
        iFace.clear();

        drawLineBottomLeftTopRight();
        Thread.sleep(DELAY_IN_MS);
        iFace.clear();

        drawCenteredCircle();
        Thread.sleep(DELAY_IN_MS);
        iFace.clear();

        drawCenteredEllipse();
        Thread.sleep(DELAY_IN_MS);
        iFace.clear();

        drawRectangle();
        Thread.sleep(DELAY_IN_MS);
        iFace.clear();

        drawCharacter();
        Thread.sleep(DELAY_IN_MS);
        iFace.clear();

        drawString();
        Thread.sleep(DELAY_IN_MS);
        iFace.clear();

        LOG.info("Shutdown of display.");
        iFace.shutdownLCD();
    }
}
