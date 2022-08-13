package com.qxy.DataStructure;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.ContentView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import com.qxy.DataStructure.base.BaseFragment;
import com.qxy.DataStructure.bean.Variety;
import com.qxy.DataStructure.network.RetrofitClient;
import com.qxy.DataStructure.network.Service.RankService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class VarietyFragment extends BaseFragment {

    RecyclerView recyclerView;
    List<Variety.DataDTO.ListDTO> varietyList;
    VarietyRecyclerAdapter varietyRecyclerAdapter;

    @Override
    protected void initViews() {
        varietyList = new ArrayList<>();
        recyclerView = contentView.findViewById(R.id.recyclerVariety);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        varietyRecyclerAdapter = new VarietyRecyclerAdapter(recyclerView,getActivity(),varietyList);
        recyclerView.setAdapter(varietyRecyclerAdapter);
        getVarietyData();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_variety;
    }

    public void getVarietyData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Client", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("clientToken","");
        Log.i("test",token);
        RetrofitClient.getInstance().getService(RankService.class)
                .getVarietyRank(token,3,null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Variety>() {
                    @Override
                    public void accept(Variety variety) throws Throwable {
                        varietyList = variety.getData().getList();
                        varietyRecyclerAdapter.setVarietyList(varietyList);
                        Log.i("test",varietyList.get(0).toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.e("test",throwable.toString());
                    }
                });
    }
}