package de.freitag.stefan.lcd.demo;/*
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


import de.freitag.stefan.lcd.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RaspiButtonTest extends Thread implements Runnable, ButtonListener {
    private static Logger LOG = LogManager.getLogger(RaspiButtonTest.class.getName());
    private static BufferedReader in;
    private static boolean quit = false;
    private final RaspiLCD iFace = new Pi4JRaspiLCD();

    public static void main(String[] args) {


        in = new BufferedReader(new InputStreamReader(System.in));

        Thread t = new Thread(new RaspiButtonTest());
        t.start();
        System.out.println("Press a button on the RaspiLCD.");
        System.out.println("Press Q THEN ENTER to terminate.");
        while (true) {
            try {
                Thread.sleep(2);
                if (quit) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    public void run() {
        LOG.info("Initializing display.");
        iFace.initialize();


        iFace.addButtonListener(this);
        String msg = null;
        while (true) {
            try {
                msg = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ("Q".equals(msg)) {
                quit = true;
                break;
            }
        }
    }

    @Override
    public void buttonPressed(final Button button) {
        LOG.info("Button was pressed:" + button);
        iFace.clear();
        final Point point = new Point(1, 1);
        iFace.putString(point, "Pressed " + button.toString());
        iFace.writeFramebuffer();
    }

    @Override
    public void buttonReleased(final Button button) {
        LOG.info("Button was released:" + button);
        iFace.clear();
        final Point point = new Point(1, 1);
        iFace.putString(point, "Released " + button.toString());
        iFace.writeFramebuffer();
    }
}

