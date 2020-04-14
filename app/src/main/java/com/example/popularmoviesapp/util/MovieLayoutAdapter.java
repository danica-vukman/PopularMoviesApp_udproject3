package com.example.popularmoviesapp.util;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesapp.R;
import com.example.popularmoviesapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieLayoutAdapter extends RecyclerView.Adapter<MovieLayoutAdapter.MovieViewHolder> {

    private final  ListItemClickListener mOnClickListener;
    ArrayList<Movie> listOfMovies;


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieLayoutAdapter(ArrayList<Movie> listOfMovies, ListItemClickListener listener) {
        this.listOfMovies = listOfMovies;
        Log.d(getClass().getCanonicalName(), "MovieLayoutAdapter: movielayoutadapter construstor");
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_grid_view, parent, false);

        return new MovieViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Log.d(getClass().getCanonicalName(), "onBindViewHolder: picasso");
        Log.d("arraylist", "onbind: " + listOfMovies.get(position).getImageURL());
        Picasso.get()
                .load(listOfMovies.get(position).getImageURL())
                .resize(450, 700).centerCrop().into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        Log.d("adapter", "getItemCount: " + listOfMovies.size());
        return listOfMovies.size();
    }




    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardMovie;
        ImageView imageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            cardMovie = itemView.findViewById(R.id.movie_CV);
            imageView = itemView.findViewById(R.id.movieIV);
                itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }
    }
}