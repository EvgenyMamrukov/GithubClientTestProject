package com.example.evgeny.githubclient.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Evgeny on 07.04.18.
 */

public class AppNavigationUtils {
    public static void startActivity(Activity startingActivity, Class<?> activityClass) {
        Intent intent = new Intent(startingActivity, activityClass);
        startingActivity.startActivity(intent);
    }

    public static void startSingleTopActivity(Activity startingActivity, Class<?> activityClass, Bundle extras) {
        Intent intent = new Intent(startingActivity, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        if (extras != null)
            intent.putExtras(extras);
        startingActivity.startActivity(intent);
        startingActivity.finish();
    }

    public static void startSingleTopActivity(Activity startingActivity, Class<?> activityClass) {
        startSingleTopActivity(startingActivity, activityClass, null);
    }
}
