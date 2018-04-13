package com.example.evgeny.githubclient.api.services;

import com.example.evgeny.githubclient.api.services.events.respond.AuthorizationRespondBody;
import com.example.evgeny.githubclient.model.api.request.AuthorizationRequestBody;
import com.example.evgeny.githubclient.model.api.response.LoginResponse;
import com.example.evgeny.githubclient.model.api.response.LogoutResponse;
import com.example.evgeny.githubclient.model.api.response.RepositoriesResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Evgeny on 09.04.18.
 */

public interface IRetrofitUserService {

    @POST("user")
    Single<LoginResponse> login(@Header("Accept") String accept, @Header("Content-Type") String contentType, @Header("Authorization") String loginData, @Body AuthorizationRequestBody requestBody);

    @GET("search/repositories")
    Single<RepositoriesResponse> getRepositories(@Query("q") String query);


    @POST("authorizations")
    Single<AuthorizationRespondBody> requestUserToken(@Header("Accept") String accept, @Header("Content-Type") String contentType, @Header("Authorization") String loginData, @Body AuthorizationRequestBody requestBody);


    @DELETE("authorizations/{id}")
    Single<Response<LogoutResponse>> logout(@Header("Accept") String accept, @Header("Content-Type") String contentType, @Header("Authorization") String loginData, @Path("id") int id);

}
