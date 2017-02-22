/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shakirov.dragonsofmugloar.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.shakirov.dragonsofmugloar.entity.Dragon;
import java.lang.reflect.Type;

/**
 *
 * @author vadim.shakirov
 */
public class DragonClassSerializer implements JsonSerializer<Dragon> {

    private final Dragon dragon;
    
    public DragonClassSerializer(Dragon dragon) {
        this.dragon = dragon;
    }
    
    @Override
    public JsonElement serialize(Dragon dragon, Type type, JsonSerializationContext jsc) {
        JsonObject dragonObject = new JsonObject();
        dragonObject.add("scaleThickness", new JsonPrimitive(dragon.getScaleThickness()));
        dragonObject.add("clawSharpness", new JsonPrimitive(dragon.getClawSharpness()));
        dragonObject.add("wingStrength", new JsonPrimitive(dragon.getWingStrength()));
        dragonObject.add("fireBreath", new JsonPrimitive(dragon.getFireBreath()));
        JsonObject result = new JsonObject();
        result.add("dragon", dragonObject);
        return result;
    }
    
    public String toJson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Dragon.class, this);
        Gson gson =  builder.create();
        return gson.toJson(this.dragon);
    }
    
}
