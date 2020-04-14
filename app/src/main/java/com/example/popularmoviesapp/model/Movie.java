package com.example.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Movie implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    HashMap<Integer, String> listOfGenres = new HashMap<>();


    String imageURL;
    String title;
    String originalTitle;
    String voteAverage;
    String overview;
    String releaseDate;
    String popularity;
    String voteCount;
    String id;
    String backdropURL;
    ArrayList<Integer> listOfGenreIds;
    String genres;
    private String TAG = getClass().getCanonicalName();

    public Movie(String imageURL, String title, String originalTitle, ArrayList<Integer> listOfGenreIds, String voteAverage, String overview, String releaseDate, String popularity, String voteCount, String id, String backdropURL) {

        this.imageURL = imageURL;
        this.title = title;
        this.originalTitle = originalTitle;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.id = id;
        this.backdropURL = backdropURL;
        this.listOfGenreIds = listOfGenreIds;

        listOfGenres.put(
                28, "Action");
        listOfGenres.put(
                12, "Adventure");
        listOfGenres.put(
                16, "Animation");
        listOfGenres.put(
                35, "Comedy");
        listOfGenres.put(
                80, "Crime");
        listOfGenres.put(
                99, "Documentary");
        listOfGenres.put(
                18, "Drama");
        listOfGenres.put(
                10751, "Family");
        listOfGenres.put(
                14, "Fantasy");
        listOfGenres.put(
                36, "History");
        listOfGenres.put(
                27, "Horror");
        listOfGenres.put(
                10402, "Music");
        listOfGenres.put(
                9648, "Mystery");
        listOfGenres.put(
                10749, "Romance");
        listOfGenres.put(
                878, "Science Fiction");
        listOfGenres.put(
                10770, "TV Movie");
        listOfGenres.put(
                53, "Thriller");
        listOfGenres.put(
                10752, "War");
        listOfGenres.put(
                37, "Western");

        Log.d(TAG, "Movie:  genre array created");
        genres = getGenresFromArray();
    }

    public Movie(Parcel in) {
        String[] data = new String[12];

        in.readStringArray(data);
        this.imageURL = data[0];
        this.title = data[1];
        this.originalTitle = data[2];
        this.voteAverage = data[3];
        this.overview = data[4];
        this.releaseDate = data[5];
        this.popularity = data[6];
        this.voteCount = data[7];
        this.id = data[8];
        this.backdropURL = data[9];
        this.id = data[10];
        this.genres = data[11];

    }

    public ArrayList<Integer> getListOfGenreIds() {
        return listOfGenreIds;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getGenres() {
               return genres;
    }

    public void setGenres(ArrayList<Integer> listOfGenreIds) {
        this.listOfGenreIds = listOfGenreIds;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackdropURL() {
        return backdropURL;
    }

    public void setBackdropURL(String backdropURL) {
        this.backdropURL = backdropURL;
    }

   private String getGenresFromArray() {


        Log.d(TAG, "getGenresFromArray: " + this.listOfGenreIds);
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < this.listOfGenreIds.size(); i++) {

            int genreId = this.listOfGenreIds.get(i);
            stringBuilder.append(listOfGenres.get(genreId) + " ");
        }
        String tempGenres = stringBuilder.toString();
        Log.d(TAG, "getGenreFromArray: " + tempGenres);
        return tempGenres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.imageURL,
                this.title,
                this.originalTitle,
                this.voteAverage,
                this.overview,
                this.releaseDate,
                this.popularity,
                this.voteCount,
                this.id,
                this.backdropURL,
                this.id,
                this.genres});
    }
}








