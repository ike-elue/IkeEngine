package com.ikeengine.render;

import java.awt.Color;

/**
 *
 * @author Jonathan Elue
 */
public class Pixel {

    public static int RED = Color.RED.getRGB();
    public static int BLUE = Color.BLUE.getRGB();
    public static int GREEN = Color.GREEN.getRGB();
    public static int ORANGE = Color.ORANGE.getRGB();
    public static int YELLOW = Color.YELLOW.getRGB();
    public static int WHITE = Color.WHITE.getRGB();
    public static int BLACK = Color.BLACK.getRGB();
    
    /**
     * Gets alpha of color
     * @param color
     * @return 
     */
    public static float getAlpha(int color) {
        return (0xff & (color >> 24)) / 255f;
    }

    /**
     * Gets red of color
     * @param color
     * @return 
     */
    public static float getRed(int color) {
        return (0xff & (color >> 16)) / 255f;
    }

    /**
     * Gets green of color
     * @param color
     * @return 
     */
    public static float getGreen(int color) {
        return (0xff & (color >> 8)) / 255f;
    }

    /**
     * Gets blue of color
     * @param color
     * @return 
     */
    public static float getBlue(int color) {
        return (0xff & (color)) / 255f;
    }

    /**
     * Returns integer value of color based on 'r', 'g', and 'b'
     * @param r
     * @param g
     * @param b
     * @return 
     */
    public static int getColor(int r, int g, int b) {
        return new Color(r, g, b).getRGB();
    }

    /**
     * Returns integer value of color based on color object
     * @param color
     * @return 
     */
    public static int getColor(Color color) {
        return color.getRGB();
    }

    /**
     * Returns integer value of color based on 'a', 'r', 'g', and 'b'
     * @param a
     * @param r
     * @param g
     * @param b
     * @return 
     */
    public static int getColor(float a, float r, float g, float b) {
        return ((int) (a * 255f) << 24 | (int) (r * 255f) << 16
                | (int) (g * 255f) << 8 | (int) (b * 255f));
    }
}
