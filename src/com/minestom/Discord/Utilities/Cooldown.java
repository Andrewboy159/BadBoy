package com.minestom.Discord.Utilities;

import java.util.HashMap;
import java.util.Map;

public class Cooldown {

    private static Map<String, Long> cooldown = new HashMap<>();
    private static int time = 5;


    public static boolean onCooldown(String string) {
        return cooldown.containsKey(string) && getSecondsLeft(string) > 0;
    }

    public static void startCooldown(String string, int seconds) {
        cooldown.put(string, System.currentTimeMillis());
        time = seconds;
    }

    public static long getSecondsLeft(String string) {
        return ((cooldown.get(string) / 1000) + time) - (System.currentTimeMillis() / 1000);
    }

}
