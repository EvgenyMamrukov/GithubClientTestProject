package com.example.evgeny.githubclient.api.services;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.evgeny.githubclient.BuildConfig;
import com.example.evgeny.githubclient.utils.GsonUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Evgeny on 09.04.18.
 */

public class ApiDataService extends AbstractService {
    protected static final String APPLICATION_JSON = "application/json";
    protected static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    protected static final String BASIC = "Basic ";
    private static final String API_PATH = "api.github.com/";
    private static final String SERVER_PROTOCOL = "https";
    public static final String HTTP_DELIMITER = "://";
    protected Retrofit adapter;

    @Override
    public void init(EventBus eventBus, Context context, ServicesManager holder) {
        super.init(eventBus, context, holder);
        buildRestAdapter();
    }

    protected void buildRestAdapter() {
        OkHttpClient client = initClient();
        Gson gson = GsonUtils.getGson();
        adapter = new Retrofit.Builder().baseUrl(provideBaseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    @NonNull
    private OkHttpClient initClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        initHeadersInterception(builder);
        initLogs(builder);
        return builder.build();
    }

    protected void initHeadersInterception(OkHttpClient.Builder builder) {

    }

    protected void initLogs(OkHttpClient.Builder builder) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(interceptor);
    }

    protected String provideBaseUrl() {
        return composeServerUrl(SERVER_PROTOCOL, API_PATH);
    }

    protected String composeServerUrl(String serverProtocol, String serverAddr) {
        return serverProtocol + HTTP_DELIMITER + serverAddr;
    }

}
