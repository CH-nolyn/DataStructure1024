package com.qxy.DataStructure;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.DataStructure.base.BaseFragment;
import com.qxy.DataStructure.bean.Movie;
import com.qxy.DataStructure.bean.Variety;
import com.qxy.DataStructure.network.RetrofitClient;
import com.qxy.DataStructure.network.Service.RankService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MovieFragment extends BaseFragment {

    RecyclerView recyclerView;
    List<Movie.DataDTO.ListDTO> movieList;
    MovieRecyclerAdapter movieRecyclerAdapter;

    @Override
    protected void initViews() {
        movieList = new ArrayList<>();
        recyclerView = contentView.findViewById(R.id.recyclerMovie);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        movieRecyclerAdapter = new MovieRecyclerAdapter(recyclerView, this.getActivity(), movieList);
        recyclerView.setAdapter(movieRecyclerAdapter);
        getMovieData();
    }

    public void getMovieData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Client", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("clientToken", "");
        Log.i("test", token);
        RetrofitClient.getInstance().getService(RankService.class)
                .getMovieRank(token, 1, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Throwable {
                        movieList = movie.getData().getList();
                        movieRecyclerAdapter.setMovieList(movieList);
                        Log.i("test", movieList.get(0).toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.e("test",throwable.toString());
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie;
    }
}