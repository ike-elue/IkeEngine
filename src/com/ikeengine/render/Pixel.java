package com.ikeengine.render;

import java.awt.Color;

/**
 *
 * @author Jonathan Elue
 */
public class Pixel {

    public static float getAlpha(int color) {
        return (0xff & (color >> 24)) / 255f;
    }

    public static float getRed(int color) {
        return (0xff & (color >> 16)) / 255f;
    }

    public static float getGreen(int color) {
        return (0xff & (color >> 8)) / 255f;
    }

    public static float getBlue(int color) {
        return (0xff & (color)) / 255f;
    }

    public static int getColor(int r, int g, int b) {
        return new Color(r, g, b).getRGB();
    }

    public static int getColor(Color color) {
        return color.getRGB();
    }

    public static int getColorf(float a, float r, float g, float b) {
        return ((int) (a * 255f) << 24 | (int) (r * 255f) << 16
                | (int) (g * 255f) << 8 | (int) (b * 255f));
    }
}
