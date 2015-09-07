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

import de.freitag.stefan.lcd.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class RaspiButtonTest extends Thread implements Runnable, ButtonListener {

    /**
     * The {@link Logger} for this class.
     */
    private static final Logger LOG = LogManager.getLogger(RaspiButtonTest.class.getName());
    private static BufferedReader in;
    private final RaspiLCD iFace = new Pi4JRaspiLCD();
    private boolean quit;

    /**
     * Entry point for this application.
     * @param args Arguments passed on command line.
     */
    public static void main(final String[] args) {
        final RaspiButtonTest test = new RaspiButtonTest();
        test.doMain();
    }

    private void doMain() {
        try {
            in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        } catch (UnsupportedEncodingException exception) {
            LOG.info("Found unsupported encoding.", exception);
            System.exit(-1);
        }

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
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
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
        if (button==null) {
            throw new IllegalArgumentException("Button is null");
        }
        LOG.info("Button was pressed:" + button);
        iFace.clear();
        final Point point = new Point(1, 1);
        iFace.putString(point, "Pressed " + button.toString());
        iFace.writeFramebuffer();
    }

    @Override
    public void buttonReleased(final Button button) {
        if (button==null) {
            throw new IllegalArgumentException("Button is null");
        }
        LOG.info("Button was released:" + button);
        iFace.clear();
        final Point point = new Point(1, 1);
        iFace.putString(point, "Released " + button.toString());
        iFace.writeFramebuffer();
    }
}

