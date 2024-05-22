package com.ixbob.thepit;

public class Mth {
    public static String formatDecimalWithFloor(double number, int decimalPlaces) {
        double newNumber = Math.floor(number * decimalPlaces * 10) / decimalPlaces * 10;
        String format = "%." + decimalPlaces + "f";
        return String.format(format, newNumber);
    }
}
