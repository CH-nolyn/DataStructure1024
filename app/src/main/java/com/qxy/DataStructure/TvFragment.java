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
import com.qxy.DataStructure.bean.Tv;
import com.qxy.DataStructure.network.RetrofitClient;
import com.qxy.DataStructure.network.Service.RankService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class TvFragment extends BaseFragment {

    RecyclerView recyclerView;
    List<Tv.DataDTO.ListDTO> tvList;
    TvRecyclerAdapter tvRecyclerAdapter;

    @Override
    protected void initViews() {
        tvList = new ArrayList<>();
        recyclerView = contentView.findViewById(R.id.recyclerTv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        tvRecyclerAdapter = new TvRecyclerAdapter(recyclerView,this.getActivity(),tvList);
        recyclerView.setAdapter(tvRecyclerAdapter);
        getTvData();
    }

    public void getTvData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Client", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("clientToken", "");
        Log.i("test", token);
        RetrofitClient.getInstance().getService(RankService.class)
                .getTvRank(token,2,null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Tv>() {
                    @Override
                    public void accept(Tv tv) throws Throwable {
                        tvList = tv.getData().getList();
                        tvRecyclerAdapter.setTvList(tvList);
                        Log.i("test", tvList.get(0).toString());
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
        return R.layout.fragment_tv;
    }
}