package com.qxy.DataStructure.network.Service;

import android.app.DownloadManager;

import com.qxy.DataStructure.bean.AccessToken;
import com.qxy.DataStructure.bean.ClientToken;

import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface UserService {
    @POST("oauth/access_token")
    @FormUrlEncoded
    Flowable<AccessToken> getAccessToken(@Field("client_secret") String client_secret,
                                         @Field("code") String code,
                                         @Field("grant_type") String grant_type,
                                         @Field("client_key") String client_key);

    @Multipart
    @POST("oauth/client_token/")
    Flowable<ClientToken> getClientToken(@PartMap Map<String, RequestBody> requestBodyMap);
}
