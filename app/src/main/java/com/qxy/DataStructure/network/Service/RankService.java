package com.qxy.DataStructure.network.Service;

import com.qxy.DataStructure.bean.Movie;
import com.qxy.DataStructure.bean.Variety;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RankService {

    @GET("discovery/ent/rank/item")
    Flowable<Variety> getVarietyRank(@Header("access-token")String access_token,
                                     @Query("type") int type,
                                     @Query("version") Integer version);

    @GET("discovery/ent/rank/item")
    Flowable<Movie> getMovieRank(@Header("access-token")String access_token,
                                 @Query("type") int type,
                                 @Query("version") Integer version);
}
