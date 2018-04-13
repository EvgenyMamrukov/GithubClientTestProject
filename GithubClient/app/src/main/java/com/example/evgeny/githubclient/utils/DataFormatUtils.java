package com.example.evgeny.githubclient.utils;

import android.content.Context;
import android.util.Base64;

import com.example.evgeny.githubclient.GithubClientApp;
import com.example.evgeny.githubclient.R;
import com.example.evgeny.githubclient.model.api.request.AuthorizationRequestBody;

/**
 * Created by Evgeny on 11.04.18.
 */

public class DataFormatUtils {
    public static AuthorizationRequestBody buildRequestBody() {
        Context context = GithubClientApp.getContext();
        String tokenNote = context.getString(R.string.token_params_note);
        String[] tokenScope = context.getResources().getStringArray(R.array.token_params_scopes);
        return new AuthorizationRequestBody(
                tokenNote,
                tokenScope
        );
    }

    public static String handleLoginData(String login, String password) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(login);
        stringBuilder.append(":");
        stringBuilder.append(password);

        return Base64.encodeToString((stringBuilder.toString()).getBytes(), Base64.DEFAULT).replace("\n", "");
    }
}
