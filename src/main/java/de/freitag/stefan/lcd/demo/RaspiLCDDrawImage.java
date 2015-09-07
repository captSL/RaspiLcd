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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class RaspiLCDDrawImage {
    /**
     * The {@link Logger} for this class.
     */
    private static final Logger LOG = LogManager.getLogger(RaspiLCDDrawImage.class.getCanonicalName());
    /**
     * Transition delay in milliseconds.
     */
    private static final int DELAY_IN_MS = 5000;
    /**
     * The RaspiLCD to use.
     */
    private final RaspiLCD iFace;

    public RaspiLCDDrawImage() {
        this.iFace = new Pi4JRaspiLCD();
    }

    /**
     * Entry point for this application.
     *
     * @param args Arguments passed on command line.
     */
    public static void main(final String args[]) {
        final RaspiLCDDrawImage demo = new RaspiLCDDrawImage();
        demo.doMain();
    }


    public void doMain() {
        LOG.info("Initializing display.");
        iFace.initialize();


        try {

            try {
                final File file = new File("example.bmp");
                if (!file.isFile()) {
                    LOG.error(file.getAbsolutePath() + " is not a file.");
                }
                BufferedImage img = ImageIO.read(file);
                this.iFace.drawBmp(new Point(0, 0), img);
                iFace.writeFramebuffer();

                Thread.sleep(DELAY_IN_MS);
                iFace.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }


    }
}
