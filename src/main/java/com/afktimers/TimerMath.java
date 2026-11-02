package com.afktimers;

final class TimerMath {
    static int venomTicks(int poison) { return poison < -38 ? (-poison - 38) * 30 : 0; }
    static int poisonTicks(int poison) { return poison < 0 ? -poison * 30 : 0; }
    static double fraction(double remaining, double maximum) {
        return maximum <= 0 ? 0 : Math.max(0, Math.min(1, remaining / maximum));
    }
    static String time(double ticks) {
        int seconds = (int)Math.ceil(Math.max(0, ticks) * 0.6);
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
    static int stackTop(int bottom, int height) { return bottom - height; }
    private TimerMath() { }
}

