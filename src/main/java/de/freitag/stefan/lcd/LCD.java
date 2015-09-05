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

import de.freitag.stefan.lcd.fonts.Terminal6x8;


/**
 * This class encapsulates the RaspiLCD C-code functions (translated to Java).
 */
final class LCD {

    /**
     * Width of the LC display (132 pixel)-
     */
    public static final int WIDTH = 132;
    /**
     * Height of the LC display (64 pixel).
     */
    public static final int HEIGHT = 64;
    /**
     * Offset on the x-axis.
     */
    private static final int LCD_X_OFFSET = 4;
    /**
     * Framebuffer that is written to.
     */
    private static final byte[][] framebuffer = new byte[WIDTH][HEIGHT / 8];
    private static LCD lcd;
    /**
     * Color of the pen.
     */
    private static boolean PenColor = true;
    /**
     * Fill color.
     */
    private static int FillColor = 0;

    private Font font;

    private LCD() {
    }

    public static LCD getInstance() {
        if (lcd == null)
            lcd = new LCD();
        return lcd;
    }


    /**
     * Initialize the display controller.
     */
    public void init() {

        try {
            RaspiLCDPin.RST.low();        // Reset LCD-Display
            Thread.sleep(50);
            RaspiLCDPin.RST.high();
            Thread.sleep(200);
            writeCommand((byte) 0xE2);    // Reset Internal Functions  NT7532.pdf p32
            writeCommand((byte) 0x40);    // Set Display Start address
            writeCommand((byte) 0xA1);    // ADC Select NT7532.pdf p30
            //   Changes the relationship between RAM column address and segment driver
            //   driver output pads can be reversed by software. This allows flexible IC layout during LCD module
            //   assembly. For details, refer to the column address section of Figure4. When display data is written
            //   or read, the column address is incremented by 1 as shown in Figure4.
            //                E   R/W
            //           A0   /RD /WR    D7 D6 D5 D4 D3 D2 D1 D0
            //           0    1    0     1  0  1  0  0  0  0  D
            //   When D is low, rotation is to the RIGHT (normal direction)
            //   When D is high, rotation is to the LEFT (reverse direction)
            writeCommand((byte) 0xC0);    // Output Status Select Register NT7532.pdf p33
            //   0: Normal (COM0  COM63/53/47/31)
            writeCommand((byte) 0xA4);    // Entire Display off NT7532.pdf p31
            writeCommand((byte) 0xA6);    // Normal/ Reverse Display NT7532.pdf p31
            //   When D0 is low, the RAM data is high, being LCD ON potential (normal display)
            writeCommand((byte) 0xA2);    // Set the LCD Bias
            //   if duty1(padNo 59) and duty0(padNo 57) equal 1  then LCD
            //   driving duty is 1/65 and bias is 1/9
            //   more Infomation NT7532.pdf p31 Bias, p11 Duty padNo 57,59
            writeCommand((byte) 0x2F);    // Set Power Control NT7532.pdf p33
            //   voltage follower on
            //   voltage regulator on
            //   voltage booster on
            writeCommand((byte) 0x27);    // V0 Voltage Regulator Internal Resistor Ratio Set NT7532.pdf p33

            writeCommand((byte) 0x81);    // The Electronic Volume Mode Set NT7532.pdf p33
            writeCommand((byte) 0);       // Electronic Volume Register Set

            writeCommand((byte) 0xFA);  // ??? Test Command  (Steffen) brauchen wir nicht geht auch ohne
            writeCommand((byte) 0x90);  // ??
            writeCommand((byte) 0xAF);    // Display On

            clearScreen();
            writeFramebuffer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Write a command to the display controller.
     *
     * @param command The command byte.
     */
    public void writeCommand(final byte command) {
        RaspiLCDPin.CS.low();
        RaspiLCDPin.RS.low();
        putCharacter(command);
        RaspiLCDPin.CS.high();
    }

    /**
     * @param data The character to transmit.
     */
    void putCharacter(byte data) {
        for (int i = 0; i < 8; i++) {
            if ((data & 0x80) == 0x80) {
                RaspiLCDPin.DATA.high();
            } else {
                RaspiLCDPin.DATA.low();
            }
            data = (byte) (data << 1);

            for (int n = 0; n < 2; n++) {
                RaspiLCDPin.CLK.low();
            }
            // CLK = 0
            for (int n = 0; n < 2; n++) {
                RaspiLCDPin.CLK.high();
            }
            // CLK = 1
        }
    }

    /**
     * Write data to the display controller.
     *
     * @param data Data byte to write.
     */
    void writeData(final byte data) {
        RaspiLCDPin.CS.low();
        // Data Mode
        RaspiLCDPin.RS.high();
        putCharacter(data);
        RaspiLCDPin.CS.high();
    }

    /**
     * Clear the LC display by emptying the framebuffer and writing
     * it to the display.
     */
    public void clearScreen() {
        for (int y = 0; y < (HEIGHT / 8); y++) {
            for (int x = 0; x < WIDTH; x++) {
                framebuffer[x][y] = 0;
            }
        }
        writeFramebuffer();
    }

    public void writeFramebuffer() {
        int x;
        setXY((byte) 0, (byte) 0);
        for (x = 0; x < WIDTH; x++) writeData(framebuffer[x][0]);
        setXY((byte) 0, (byte) 1);
        for (x = 0; x < WIDTH; x++) writeData(framebuffer[x][1]);
        setXY((byte) 0, (byte) 2);
        for (x = 0; x < WIDTH; x++) writeData(framebuffer[x][2]);
        setXY((byte) 0, (byte) 3);
        for (x = 0; x < WIDTH; x++) writeData(framebuffer[x][3]);
        setXY((byte) 0, (byte) 4);
        for (x = 0; x < WIDTH; x++) writeData(framebuffer[x][4]);
        setXY((byte) 0, (byte) 5);
        for (x = 0; x < WIDTH; x++) writeData(framebuffer[x][5]);
        setXY((byte) 0, (byte) 6);
        for (x = 0; x < WIDTH; x++) writeData(framebuffer[x][6]);
        setXY((byte) 0, (byte) 7);
        for (x = 0; x < WIDTH; x++) writeData(framebuffer[x][7]);
    }

    void setXY(byte x, final byte ypage) {
        x += LCD_X_OFFSET;
        writeCommand((byte) (0x00 + (x & 0x0F)));    // set lower four bits of column
        writeCommand((byte) (0x10 + ((x >> 4) & 0x0F)));  // set higher four bits of column
        writeCommand((byte) (0xB0 + (ypage & 0x07)));    // set page address
    }

    public void PutPixel(final Point point, final boolean color) {
        if ((point.getXCoordinate() < WIDTH) && (point.getYCoordinate() < HEIGHT)) {
            if (color) {
                framebuffer[point.getXCoordinate()][point.getYCoordinate() >> 3] |= (1 << (point.getYCoordinate() & 7));
            } else {
                framebuffer[point.getXCoordinate()][point.getYCoordinate() >> 3] &= ~(1 << (point.getYCoordinate() & 7));
            }
        }

    }

    /**
     * Draw an ellipse
     *
     * @param xm Center of the ellipse, x-coordinate.
     * @param ym Center of the ellipse, y-coordinate.
     * @param a  Length semi-axis 1.
     * @param b  Length semi-axis 2.
     */
    public void drawEllipse(final int xm, final int ym, final int a, final int b) {
        int dx = 0, dy = b;
        long a2 = a * a, b2 = b * b;
        long err = b2 - (2 * b - 1) * a2, e2;

        do {
            PutPixel(xm + dx, ym + dy, PenColor);
            PutPixel(xm - dx, ym + dy, PenColor);
            PutPixel(xm - dx, ym - dy, PenColor);
            PutPixel(xm + dx, ym - dy, PenColor);

            e2 = 2 * err;
            if (e2 < (2 * dx + 1) * b2) {
                dx++;
                err += (2 * dx + 1) * b2;
            }
            if (e2 > -(2 * dy - 1) * a2) {
                dy--;
                err -= (2 * dy - 1) * a2;
            }
        }
        while (dy >= 0);

        while (dx++ < a) {
            PutPixel(xm + dx, ym, PenColor);
            PutPixel(xm - dx, ym, PenColor);
        }
    }

    public void setPenColor(final boolean color) {
        PenColor = color;
    }

    public void setFillColor(final int color) {
        FillColor = color;
    }

    public void PutPixel(final int x, final int y, final boolean color) {
        if ((x < WIDTH) && (y < HEIGHT)) {
            if (color) {
                framebuffer[x][y >> 3] |= (1 << (y & 7));
            } else {
                framebuffer[x][y >> 3] &= ~(1 << (y & 7));
            }
        }
    }

    public void drawLine(int x0, int y0, int x1, int y1) {
        int dx, sx, dy, sy, err, e2;  // for Bresenham
        int i;

        if (y0 == y1)            // horizontale Linie
        {
            if (x0 > x1) {
                i = x0;
                x0 = x1;
                x1 = i;
            }     // swap direction
            while (x0 <= x1) PutPixel(x0++, y0, PenColor);
        } else if (x0 == x1)        // vertikale Linie
        {
            if (y0 > y1) {
                i = y0;
                y0 = x1;
                y1 = i;
            }     // swap direction
            while (y0 <= y1) PutPixel(x0, y0++, PenColor);
        } else        // Bresenham Algorithmus
        {
            dx = Math.abs(x1 - x0);
            sx = x0 < x1 ? 1 : -1;
            dy = -Math.abs(y1 - y0);
            sy = y0 < y1 ? 1 : -1;
            err = dx + dy;
            for (; ; ) {
                PutPixel(x0, y0, true);
                if (x0 == x1 && y0 == y1) break;
                e2 = 2 * err;
                if (e2 > dy) {
                    err += dy;
                    x0 += sx;
                } /* e_xy+e_x > 0 */
                if (e2 < dx) {
                    err += dx;
                    y0 += sy;
                } /* e_xy+e_y < 0 */
            }
        }
    }

    public void drawCircle(final int x0, final int y0, final int radius) {
        int f = 1 - radius;
        int ddF_x = 0;
        int ddF_y = -2 * radius;
        int x = 0;
        int y = radius;

        PutPixel(x0, y0 + radius, PenColor);
        PutPixel(x0, y0 - radius, PenColor);
        PutPixel(x0 + radius, y0, PenColor);
        PutPixel(x0 - radius, y0, PenColor);

        while (x < y) {
            if (f >= 0) {
                y--;
                ddF_y += 2;
                f += ddF_y;
            }
            x++;
            ddF_x += 2;
            f += ddF_x + 1;

            PutPixel(x0 + x, y0 + y, PenColor);
            PutPixel(x0 - x, y0 + y, PenColor);
            PutPixel(x0 + x, y0 - y, PenColor);
            PutPixel(x0 - x, y0 - y, PenColor);
            PutPixel(x0 + y, y0 + x, PenColor);
            PutPixel(x0 - y, y0 + x, PenColor);
            PutPixel(x0 + y, y0 - x, PenColor);
            PutPixel(x0 - y, y0 - x, PenColor);
        }
    }

    public void printXY(final Point point, final Character charToPrint) {
        if (point == null) {
            throw new IllegalArgumentException("Point is null");
        }
        if (charToPrint == null) {
            throw new IllegalArgumentException("Character is null");
        }
        int ix, iy, y;
        char[] font;
        int pt;
        int d;
        char c;
        int char_width, char_height, char_size;
        int i_char_height;


        /**if(FontNumber == 1)   font = font_fixedsys_8x15;
         else if(FontNumber == 2)  font= font_lucida_10x16;
         else if(FontNumber == 3)  font = font_terminal_12x16;
         else					   font = font_terminal_6x8;
         */
        font = Terminal6x8.font_terminal_6x8;
        char_size = font[0];
        char_width = font[1];
        char_height = font[2];

        c = charToPrint;

        //TODO if(*s > 31) c = (*s) - 32;

        pt = 3 + (char_size * (c - 32));

        i_char_height = char_height;

        y = point.getYCoordinate();

        for (ix = 0; ix < char_width; ix++) {
            d = pt++;
            char tmp = font[d];
            for (iy = 0; iy < 8; iy++) {

                byte b = (byte) ((byte) (1 << iy) & (byte) tmp);
                if (b != 0) {
                    PutPixel(point.getXCoordinate() + ix, y + iy, true);
                } else {
                    PutPixel(point.getXCoordinate() + ix, y + iy, false);
                }
            }
        }

        /**         while(i_char_height)
         {
         i_char_height = (i_char_height >= 8) ? i_char_height - 8 : 0;
         y+=8;		// n�chste "Page"
         }*/
    }

    /**
     * Draw a bitmap.
     *
     * @param x0  x-coordinate of the top LEFT corner.
     * @param y0  y-coordinate of the top LEFT corner.
     * @param bmp The two-dimensional byte array to draw.
     * @throws IllegalArgumentException if {@code bmp} is {@code null}.
     */
    public void drawBitmap(final int x0, final int y0, final byte[][] bmp) {
        if (bmp == null) {
            throw new IllegalArgumentException("Byte matrix is null");
        }

        final int width = bmp.length;
        final int height = bmp[0].length;

        for (int iy = 0; iy < height; iy++) {
            for (int ix = 0; ix < width; ix++) {
                final byte b = (byte) (1 << (iy & 7));
                if (bmp[ix][iy] == 1) {
                    PutPixel(x0 + ix, y0 + iy, true);
                } else {
                    PutPixel(x0 + ix, y0 + iy, false);
                }
            }
        }
    }
}
