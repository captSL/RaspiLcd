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

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Definition of the GPIO pins to control the RaspiLCD.
 */
public class RaspiLCDPin {

    public static final GpioPinDigitalOutput LED = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED", PinState.HIGH);
    public static final GpioPinDigitalOutput RST = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_06, "RST", PinState.LOW);
    public static final GpioPinDigitalOutput CS = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_10, "CS", PinState.LOW);
    public static final GpioPinDigitalOutput RS = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_11, "RS", PinState.LOW);
    public static final GpioPinDigitalOutput CLK = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_14, "CLK", PinState.LOW);
    public static final GpioPinDigitalOutput DATA = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_12, "DATA", PinState.LOW);
}
