package com.example.evgeny.githubclient.api.services;

import android.content.Context;

import com.example.evgeny.githubclient.api.services.events.request.OnGetRepositoriesEvent;
import com.example.evgeny.githubclient.api.services.events.request.OnLoginEvent;
import com.example.evgeny.githubclient.api.services.events.request.OnLogoutEvent;
import com.example.evgeny.githubclient.api.services.events.respond.AuthorizationRespondBody;
import com.example.evgeny.githubclient.api.services.events.respond.OnErrorEvent;
import com.example.evgeny.githubclient.api.services.events.respond.OnGetRepositoriesRespondEvent;
import com.example.evgeny.githubclient.api.services.events.respond.OnLoginRespondEvent;
import com.example.evgeny.githubclient.api.services.events.respond.OnLogoutRespondEvent;
import com.example.evgeny.githubclient.model.api.request.AuthorizationRequestBody;
import com.example.evgeny.githubclient.model.api.response.LoginResponse;
import com.example.evgeny.githubclient.model.api.response.LogoutResponse;
import com.example.evgeny.githubclient.model.api.response.RepositoriesResponse;
import com.example.evgeny.githubclient.model.blogic.LoginData;
import com.example.evgeny.githubclient.model.blogic.UserProfileData;
import com.example.evgeny.githubclient.utils.DataFormatUtils;
import com.example.evgeny.githubclient.utils.ExecutorProvider;
import com.example.evgeny.githubclient.utils.LocalStoringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Evgeny on 09.04.18.
 */

public class UserDataService extends ApiDataService {
    private IRetrofitUserService service;
    private static final int STATUS_NO_CONTENT = 204;

    @Override
    public void init(EventBus eventBus, Context context, ServicesManager holder) {
        super.init(eventBus, context, holder);
        service = adapter.create(IRetrofitUserService.class);
    }

    @Subscribe
    public void onLogin(OnLoginEvent onLoginEvent) {
        LoginData loginData = onLoginEvent.getLoginData();
        String encode = DataFormatUtils.handleLoginData(loginData.getLogin(), loginData.getPassword());
        LocalStoringUtils.storeData(LocalStoringUtils.AUTHORIZATION_ENCODED_DATA, encode);
        AuthorizationRequestBody authorizationRequestBody = DataFormatUtils.buildRequestBody();

        service.requestUserToken(APPLICATION_JSON, FORM_URL_ENCODED,BASIC + encode, authorizationRequestBody)
                .flatMap(new Function<AuthorizationRespondBody, Single<LoginResponse>>() {
                    @Override
                    public Single<LoginResponse> apply(AuthorizationRespondBody authorizationRespondBody) throws Exception {
                        LocalStoringUtils.storeData(LocalStoringUtils.AUTHORIZATION_ID, authorizationRespondBody.getId());
                        return service.login(APPLICATION_JSON, FORM_URL_ENCODED,BASIC + encode, authorizationRequestBody);
                    }
                }).subscribeOn(Schedulers.from(ExecutorProvider.defaultHttpExecutor()))
                .observeOn(Schedulers.from(ExecutorProvider.defaultHttpExecutor()))
                .subscribe(new SingleObserver<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(LoginResponse loginResponse) {
                        UserProfileData userProfileData = new UserProfileData();
                        userProfileData.setId(loginResponse.getId());
                        userProfileData.setName(loginResponse.getLogin());
                        userProfileData.setAvatar(loginResponse.getAvatarUrl());
                        userProfileData.setRepositories(loginResponse.getRepositoriesUrl());
                        LocalStoringUtils.storeData(LocalStoringUtils.USER_PROFILE_DATA, userProfileData);
                        OnLoginRespondEvent onLoginRespondEvent = new OnLoginRespondEvent(onLoginEvent);
                        eventBus.post(onLoginRespondEvent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        OnErrorEvent onErrorEvent = new OnErrorEvent(onLoginEvent, e.getMessage());
                        eventBus.post(onErrorEvent);
                    }
                });
    }

    @Subscribe
    public void onLogout(OnLogoutEvent onLogoutEvent) {
        int id = LocalStoringUtils.getData(LocalStoringUtils.AUTHORIZATION_ID, Integer.class);
        String encode = LocalStoringUtils.getData(LocalStoringUtils.AUTHORIZATION_ENCODED_DATA, String.class);
        service.logout(APPLICATION_JSON, FORM_URL_ENCODED, BASIC + encode, id)
                .subscribeOn(Schedulers.from(ExecutorProvider.defaultHttpExecutor()))
                .observeOn(Schedulers.from(ExecutorProvider.defaultHttpExecutor()))
                .subscribe(new SingleObserver<Response<LogoutResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<LogoutResponse> response) {
                        if (response.code() == STATUS_NO_CONTENT) {
                            OnLogoutRespondEvent onLogoutRespondEvent = new OnLogoutRespondEvent(onLogoutEvent);
                            eventBus.post(onLogoutRespondEvent);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        OnErrorEvent onErrorEvent = new OnErrorEvent(onLogoutEvent, e.getMessage());
                        eventBus.post(onErrorEvent);
                    }
                });
    }

    @Subscribe
    public void onGetRepositories(OnGetRepositoriesEvent onGetRepositoriesEvent) {

        String query = onGetRepositoriesEvent.getQuery();
        service.getRepositories(query)
                .subscribeOn(Schedulers.from(ExecutorProvider.defaultHttpExecutor()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RepositoriesResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(RepositoriesResponse response) {
                        OnGetRepositoriesRespondEvent onGetRepositoriesRespondEvent = new OnGetRepositoriesRespondEvent(onGetRepositoriesEvent, response);
                        eventBus.post(onGetRepositoriesRespondEvent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        OnErrorEvent onErrorEvent = new OnErrorEvent(onGetRepositoriesEvent, e.getMessage());
                        eventBus.post(onErrorEvent);
                    }
                });
    }
}
