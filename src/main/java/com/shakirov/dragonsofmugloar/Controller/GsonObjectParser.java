package com.shakirov.dragonsofmugloar.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class needed for serializing/deserializing objects to/from JSON
 * @author vadim.shakirov
 */
public class GsonObjectParser {
    
    public static Object toObject(String json, Class<?> type) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, type);
    }
    
    public static String toJson(Object o) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(o);
    }
}
