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
import com.qxy.DataStructure.bean.Movie;

import java.util.List;
import java.util.zip.Inflater;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Context context;
    private List<Movie.DataDTO.ListDTO> movieList;

    public MovieRecyclerAdapter() {

    }

    public MovieRecyclerAdapter(RecyclerView recyclerView, Context context, List<Movie.DataDTO.ListDTO> movieList) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.movieList = movieList;
    }

    public void setMovieList(List<Movie.DataDTO.ListDTO> movieList) {
        this.movieList.clear();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        view.setOnClickListener(this);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie.DataDTO.ListDTO movie = movieList.get(position);
        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
        movieViewHolder.movieName.setText(movie.getName());
        String area = "";
        if(movie.getAreas() != null){
            for (int i = 0, count = 0; i < movie.getAreas().size(); i++) {
                if (count == 0) {
                    area = movie.getAreas().get(i);
                } else if (count < 3) {
                    area = area + " / " + movie.getAreas().get(i);
                } else {
                    area = area + "等地";
                    break;
                }
                count++;
            }
        }
        movieViewHolder.movieDataArea.setText(movie.getRelease_date() + " " + area + "上映");
        String actor = "";
        if(movie.getActors() != null){
            for (int i = 0, count = 0; i < movie.getActors().size(); i++) {
                if (count == 0) {
                    actor = movie.getActors().get(i);
                } else if (count < 3) {
                    actor = actor + " / " + movie.getActors().get(i);
                } else {
                    break;
                }
                count++;
            }
        }
        movieViewHolder.movieActorName.setText(actor);
        String person = "";
        if(movie.getDirectors()!=null){
            for (int i = 0, count = 0; i < movie.getDirectors().size(); i++) {
                if (count == 0) {
                    person = movie.getDirectors().get(i);
                } else if (count < 3) {
                    person = person + " / " + movie.getDirectors().get(i);
                } else {
                    break;
                }
                count++;
            }
        }
        movieViewHolder.moviePersonName.setText(person);
        Glide.with(context).load(movie.getPoster()).into(movieViewHolder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    @Override
    public void onClick(View view) {

    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieName, moviePersonName, movieActorName, movieDataArea;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.MovieImage);
            movieName = itemView.findViewById(R.id.MovieName);
            moviePersonName = itemView.findViewById(R.id.MoviePersonName);
            movieActorName = itemView.findViewById(R.id.MovieActorName);
            movieDataArea = itemView.findViewById(R.id.MovieDateArea);
        }
    }
}
