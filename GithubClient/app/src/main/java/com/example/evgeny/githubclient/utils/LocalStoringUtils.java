package com.example.evgeny.githubclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.evgeny.githubclient.GithubClientApp;
import java.lang.reflect.Type;

/**
 * Created by Evgeny on 12.04.18.
 */

public class LocalStoringUtils {
    public static final String USER_PROFILE_DATA = "USER_PROFILE_DATA";
    public static final String AUTHORIZATION_ID = "AUTHORIZATION_ID";
    public static final String AUTHORIZATION_ENCODED_DATA = "AUTHORIZATION_ENCODED_DATA";

    public static void storeData(String key, Object value) {
        if (value == null) {
            removeData(key);
            return;
        }
        String jsonRepresentation = GsonUtils.serialize(value);
        getPreferences()
                .edit()
                .putString(key, jsonRepresentation)
                .commit();
    }

    public static <T> T getData(String key, Class<T> type) {
        String jsonRepresentation = getPreferences().getString(key, "");
        if (jsonRepresentation.length() == 0) {
            return null;
        }

        return GsonUtils.deserialize(jsonRepresentation, type);
    }

    public static <T> T getData(String key, Type type) {
        String jsonRepresentation = getPreferences().getString(key, "");
        if (jsonRepresentation.length() == 0) {
            return null;
        }
        try {
            return GsonUtils.deserialize(jsonRepresentation, type);
        } catch (Throwable exc) {
            return null;
        }
    }

    public static void removeData(String key) {
        getPreferences()
                .edit()
                .remove(key)
                .commit();
    }

    private static SharedPreferences getPreferences() {
        Context context = GithubClientApp.getContext();
        String packageName = context.getPackageName();
        return context.getSharedPreferences(packageName, Context.MODE_PRIVATE);
    }
}
