package com.example.popularmoviesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.popularmoviesapp.model.Movie;
import com.example.popularmoviesapp.util.AutofitGridLayoutManager;
import com.example.popularmoviesapp.util.MovieLayoutAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieLayoutAdapter.ListItemClickListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.movie_rv)
    RecyclerView recyclerView;


    String TAG = getClass().getCanonicalName();
    String baseImgUrl;
    String popularUrl;
    String topRatedUrl;

    MovieLayoutAdapter movieLayoutAdapter;
    AutofitGridLayoutManager gridLayoutManager;

    RequestQueue requestQueue;

    ArrayList<String> spinnerArray;
    ArrayList<Movie> listOfMovies;
    boolean sortedByPopular;

    String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseImgUrl = getString(R.string.baseimgurl);
         popularUrl = getString(R.string.popularurl);
         topRatedUrl = getString(R.string.topratedurl);

        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        listOfMovies = new ArrayList<>();
        Context context = this;
        ButterKnife.bind(this);
        spinnerArray = new ArrayList<>();
        spinnerArray.add(getString(R.string.label_sortpopular));
        spinnerArray.add(getString(R.string.label_sorttoprated));
        jsonString = "";
        sortedByPopular = true;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);




            getStringRequest(popularUrl);



        gridLayoutManager = new AutofitGridLayoutManager(context, 450);
        movieLayoutAdapter = new MovieLayoutAdapter(listOfMovies, this);


        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieLayoutAdapter);

        recyclerView.setHasFixedSize(false);


    }

    public void getMovieListFromJson(String s) {

        try {


            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray(getString(R.string.jsonarrayresults));


            for (int i = 0; i < jsonArray.length(); i++) {

                Movie tempMovie;

                JSONObject movieJson = jsonArray.getJSONObject(i);
                String temptitle;
                String temporigtitle;
                String tempid;
                String tempsummary;
                String temppopularity;
                String tempvoteaverage;
                String temptvotecount;
                String tempdate;
                String tempbackurl;
                String tempimgurl;
                ArrayList<Integer> tempints = new ArrayList<>();

                JSONArray genresArray = movieJson.getJSONArray(getString(R.string.jsongenre_ids));
                for (int j = 0; j < genresArray.length(); j++) {
                    tempints.add(genresArray.getInt(j));
                }
                temptitle = movieJson.getString(getString(R.string.jsontitle));
                temporigtitle = movieJson.getString(getString(R.string.jsonoriginaltitle));
                tempdate = movieJson.getString(getString(R.string.jsonreleasedate));
                tempid = String.valueOf(movieJson.getInt(getString(R.string.jsonmovieid)));
                temppopularity = String.valueOf(movieJson.getInt(getString(R.string.jsonpopularity)));
                tempsummary = movieJson.getString(getString(R.string.jsonoverview));
                tempvoteaverage = String.valueOf(movieJson.getDouble(getString(R.string.jsonvoteaverage)));
                temptvotecount = String.valueOf(movieJson.getInt(getString(R.string.jsonvotecount)));
                tempbackurl = baseImgUrl + movieJson.getString(getString(R.string.jsonbackdrop));
                tempimgurl = baseImgUrl + movieJson.getString(getString(R.string.jsonposter));


                tempMovie = new Movie(tempimgurl, temptitle, temporigtitle, tempints, tempvoteaverage, tempsummary, tempdate, temppopularity, temptvotecount, tempid, tempbackurl);
                listOfMovies.add(tempMovie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d(TAG, "onListItemClick:  movie clicked " + listOfMovies.get(clickedItemIndex).getTitle());

        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(getString(R.string.extrakeyclickedmovie), listOfMovies.get(clickedItemIndex));
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (sortedByPopular && parent.getSelectedItem().equals(getString(R.string.label_sorttoprated))) {
            Log.d(TAG, "onItemSelected: case 1");
            listOfMovies.clear();
            getStringRequest(topRatedUrl);
            sortedByPopular = false;
        }

        if (!sortedByPopular && parent.getSelectedItem().equals(getString(R.string.label_sortpopular))) {
            Log.d(TAG, "onItemSelected: case 2");
            listOfMovies.clear();
            getStringRequest(popularUrl);
            sortedByPopular = true;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //override
    }


    private void getStringRequest(String s) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonString = "";
                jsonString = response;

                if (jsonString.equals("")) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "onResponse: " + response);
                getMovieListFromJson(jsonString);
                movieLayoutAdapter.notifyDataSetChanged();

            }
        }, error -> {
            Log.e("Volley", "Error");
            showNoInternetDialog();
        });

        requestQueue.add(stringRequest);
    }


    private void showNoInternetDialog() {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.dialogtitle)
                .setMessage(R.string.dialogmessage)

                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNeutralButton(R.string.dialogbutton, (dialog, which) -> dialog.dismiss())
                .show();

    }

}




