package com.shakirov.dragonsofmugloar.entity;

/**
 *
 * @author vadim.shakirov
 */
public class Weather {
    
    public static boolean isNormal(String weather) {
        return weather.contains("normal regular weather");
    }
    
    public static boolean isStorm(String weather) {
        return weather.contains("storm");
    }
    
    public static boolean isLongDry(String weather) {
        return weather.contains("The Long Dry");
    }
    
    public static boolean isFog(String weather) {
        return weather.contains("fog");
    }
    
    public static boolean isRain(String weather) {
        return weather.contains("the water is going to fall");
    }
}
