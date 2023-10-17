package io.grasspow.toolboxwebapi.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
    public static final Gson GSON = initGson();
    private static Gson initGson() {
        return new GsonBuilder().serializeNulls().create();
    }
}
