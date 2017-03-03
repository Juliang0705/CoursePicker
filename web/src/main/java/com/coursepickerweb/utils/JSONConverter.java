package com.coursepickerweb.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class JSONConverter {

    public static <T> String serialize(T data) {
        Type classType = new TypeToken<T>(){}.getType();
        Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
        return gson.toJson(data, classType);
    }

}