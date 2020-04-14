package com.example.popularmoviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.popularmoviesapp.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.detailScrollView)
    ScrollView scrollView;
    @BindView(R.id.orig_titleTV)
    TextView originalTitleTV;
    @BindView(R.id.titleTV)
    TextView titleTV;
    @BindView(R.id.genreTV)
    TextView genreTV;
    @BindView(R.id.popularityTV)
    TextView popularityTV;
    @BindView(R.id.votesTV)
    TextView votesTV;
    @BindView(R.id.summaryTV)
    TextView summaryTV;
    @BindView(R.id.release_dateTV)
    TextView releaseDateTV;
    @BindView(R.id.posterIV)
    ImageView posterIV;

    Movie shownMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent.hasExtra("clicked movie")) {
            shownMovie = intent.getParcelableExtra("clicked movie");

        }

        titleTV.setText(shownMovie.getTitle());
        originalTitleTV.setText(shownMovie.getOriginalTitle());
        genreTV.setText(shownMovie.getGenres());
        popularityTV.setText(shownMovie.getPopularity());
        Picasso.get().load(shownMovie.getImageURL()).resize(500, 750).into(posterIV);
        summaryTV.setText(shownMovie.getOverview());
        releaseDateTV.setText(shownMovie.getReleaseDate());
        votesTV.setText(shownMovie.getVoteAverage() + "/10 (" + shownMovie.getVoteCount() + " votes)");

    }
}
