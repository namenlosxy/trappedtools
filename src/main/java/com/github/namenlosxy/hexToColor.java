package com.github.namenlosxy;

import java.awt.*;

public class hexToColor {
    public static int convertToColor(String hex) {
        int color;
        Color javaColor = Color.decode(hex);
        color = (65536 * javaColor.getRed()) + (256 * javaColor.getGreen()) + javaColor.getBlue();
        return color;
    }
}