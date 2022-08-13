package com.qxy.DataStructure;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qxy.DataStructure.bean.Variety;

import java.util.List;

public class VarietyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Context context;
    private List<Variety.DataDTO.ListDTO> varietyList;


    public VarietyRecyclerAdapter(){

    }

    public VarietyRecyclerAdapter(RecyclerView recyclerView, Context context, List<Variety.DataDTO.ListDTO> varietyList){
        this.recyclerView = recyclerView;
        this.context = context;
        this.varietyList = varietyList;
    }

    public void setVarietyList(List<Variety.DataDTO.ListDTO> varietyList){
        this.varietyList.clear();
        this.varietyList.addAll(varietyList);
        Log.i("test","setVarietyListP:"+ this.varietyList.size());
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_variety,parent,false);
        view.setOnClickListener(this);
        return new VarietyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i("test","position:" + position);
        Variety.DataDTO.ListDTO  variety = varietyList.get(position);
        VarietyViewHolder varietyViewHolder = (VarietyViewHolder) holder;
        varietyViewHolder.varietyName.setText(variety.getName());
        varietyViewHolder.varietyEnName.setText(variety.getName_en());
        varietyViewHolder.varietyDate.setText(variety.getRelease_date() + "  播出");
        Glide.with(context).load(variety.getPoster()).into(varietyViewHolder.varietyImage);
        String personName = "";
        for(int i = 0,count = 0;i<variety.getDirectors().size();i++){
            if(count == 0){
                personName = variety.getDirectors().get(i);
            }
            else if(count < 3){
                personName = personName + " / " + variety.getDirectors().get(i);
            }
            else{
                break;
            }
            count++;
        }
        varietyViewHolder.varietyPersonName.setText(personName);
    }

    @Override
    public int getItemCount() {
        Log.i("test","itemcount:"+ varietyList.size());
        return varietyList.size();
    }

    @Override
    public void onClick(View view) {

    }

    class VarietyViewHolder extends RecyclerView.ViewHolder{
        ImageView varietyImage;
        TextView varietyName,varietyPersonName,varietyEnName,varietyDate;

        public VarietyViewHolder(@NonNull View itemView) {
            super(itemView);
            varietyImage = itemView.findViewById(R.id.VarietyImage);
            varietyName = itemView.findViewById(R.id.VarietyName);
            varietyPersonName = itemView.findViewById(R.id.VarietyPersonName);
            varietyEnName = itemView.findViewById(R.id.VarietyEnName);
            varietyDate = itemView.findViewById(R.id.VarietyDate);
        }
    }
}
