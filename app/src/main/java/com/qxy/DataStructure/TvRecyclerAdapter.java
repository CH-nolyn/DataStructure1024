package com.qxy.DataStructure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qxy.DataStructure.bean.Tv;

import java.util.List;

public class TvRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private RecyclerView recyclerView;
    private Context context;
    private List<Tv.DataDTO.ListDTO> tvList;

    public TvRecyclerAdapter(){

    }
    public TvRecyclerAdapter(RecyclerView recyclerView, Context context, List<Tv.DataDTO.ListDTO> tvList){
        this.recyclerView =recyclerView;
        this.context = context;
        this.tvList = tvList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tv,parent,false);
        view.setOnClickListener(this);
        return new TvViewHolder(view);
    }
    public void setTvList(List<Tv.DataDTO.ListDTO> tvList){
        this.tvList.clear();
        this.tvList.addAll(tvList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Tv.DataDTO.ListDTO tv = tvList.get(position);
        TvViewHolder tvViewHolder = (TvViewHolder) holder;
        tvViewHolder.tvName.setText(tv.getName());
        tvViewHolder.tvEnName.setText(tv.getName_en());
        Glide.with(context).load(tv.getPoster()).into(tvViewHolder.tvImage);
        String area = "";
        if(tv.getAreas() != null){
            for (int i = 0, count = 0; i < tv.getAreas().size(); i++) {
                if (count == 0) {
                    area = tv.getAreas().get(i);
                } else if (count < 3) {
                    area = area + " / " + tv.getAreas().get(i);
                } else {
                    area = area + "等地";
                    break;
                }
                count++;
            }
        }
        tvViewHolder.tvDataArea.setText(tv.getRelease_date() + " " + area + "上映");
        String actor = "";
        if(tv.getActors() != null){
            for (int i = 0, count = 0; i < tv.getActors().size(); i++) {
                if (count == 0) {
                    actor = tv.getActors().get(i);
                } else if (count < 3) {
                    actor = actor + " / " + tv.getActors().get(i);
                } else {
                    break;
                }
                count++;
            }
        }
        tvViewHolder.tvActorName.setText(actor);
        String person = "";
        if(tv.getDirectors()!=null){
            for (int i = 0, count = 0; i < tv.getDirectors().size(); i++) {
                if (count == 0) {
                    person = tv.getDirectors().get(i);
                } else if (count < 3) {
                    person = person + " / " + tv.getDirectors().get(i);
                } else {
                    break;
                }
                count++;
            }
        }
        tvViewHolder.tvPersonName.setText(person);
        String type = "";
        if(tv.getTags()!=null){
            for(int i =0,count = 0;i<tv.getTags().size();i++){
                if(count == 0){
                    type = tv.getTags().get(i);
                }
                else if(count<3){
                    type = type + " / " + tv.getTags().get(i);
                }
                else{
                    break;
                }
                count++;
            }
        }
        tvViewHolder.tvType.setText(type);
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    @Override
    public void onClick(View view) {

    }

    class TvViewHolder extends RecyclerView.ViewHolder{
        ImageView tvImage;
        TextView tvName,tvEnName,tvPersonName,tvActorName,tvType,tvDataArea;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            tvImage = itemView.findViewById(R.id.TvImage);
            tvName = itemView.findViewById(R.id.TvName);
            tvEnName = itemView.findViewById(R.id.TvEnName);
            tvPersonName = itemView.findViewById(R.id.TvPersonName);
            tvActorName = itemView.findViewById(R.id.TvActorName);
            tvType = itemView.findViewById(R.id.TvType);
            tvDataArea =itemView.findViewById(R.id.TvDateArea);
        }
    }
}
