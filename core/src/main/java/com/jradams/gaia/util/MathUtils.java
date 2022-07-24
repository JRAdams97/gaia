package com.jradams.gaia.util;

public class MathUtils {

    private MathUtils() {}

    public static boolean isBetween(final float x, final float low, final float high) {
        return low <= x && x <= high;
    }
}
