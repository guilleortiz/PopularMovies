package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    //String url="";

    private RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    String defaultOrder="popular";
    String topRatedOrder="top_rated";

    /*
    *
    * pluging parcelable =Android Parcelable code generator
    *
    *
    *
    *
    * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_Movie);


        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        int mNoOfColumns = calculateNoOfColumns(getApplicationContext());


       GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, mNoOfColumns);

        /*


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                 mRecyclerView.setLayoutManager(layoutManager);

                */

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mRecyclerView.setHasFixedSize(true);


        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);


        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);


        loadMovieData(defaultOrder);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }


    private void loadMovieData(String orderBy) {
        showMovieDataView();


        new FetchMovieTask().execute(orderBy);
    }


    private void showMovieDataView() {

        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);

        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(Movie detailMovieInfo) {
        Context context=this;
        Class destinationClass= MovieDetail.class;

        Intent intentToStartMovieDetail= new Intent(context,destinationClass);

        /*////////Paso mas cosas
        Bundle extras= new Bundle();
        extras.putString("titulo",detailMovieInfo.);
        extras.putString("plot",detailMovieInfo.);

        intentToStartMovieDetail.putExtra(Intent.EXTRA_TEXT,detailMovieInfo);
        startActivity(intentToStartMovieDetail);
        *////////////////////////////////////////////



        /////////////////////////Paso solo un string con el plot
        intentToStartMovieDetail.putExtra("titulo",detailMovieInfo.mMovieTitle);
        intentToStartMovieDetail.putExtra("plot",detailMovieInfo.mMovieOverview);
        intentToStartMovieDetail.putExtra("nota",detailMovieInfo.mUserRating);
        intentToStartMovieDetail.putExtra("fecha",detailMovieInfo.mReleaseDate);
        intentToStartMovieDetail.putExtra("img",detailMovieInfo.getPosterPath());



        //intentToStartMovieDetail.putExtra(Intent.EXTRA_TEXT,detailMovieInfo);
        startActivity(intentToStartMovieDetail);





    }


    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {


            if (params.length == 0) {
                return null;
            }

            String query = params[0];

            String searchOrderBy=null;
            URL movieRequestUrl = null;

            if (query=="top_rated"){
                searchOrderBy="top_rated";

            }else if(query=="popular"){
                searchOrderBy="popular";


            }
            try {
                movieRequestUrl = new URL("http://api.themoviedb.org/3/movie/"+searchOrderBy+"?api_key=7436e9325f7283bdded6ec8c4db0a4a8");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                ArrayList<Movie> simpleJsonMovieData = OpenMovieJsonUtils
                        .getSimpleMovieStringsFromJson(jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMovieDataView();
                mMovieAdapter.setMovieData(movies);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_Popular) {
            mMovieAdapter.setMovieData(null);
            loadMovieData(defaultOrder);
            return true;
        }else if(id==R.id.action_top_rated){
            mMovieAdapter.setMovieData(null);
            loadMovieData(topRatedOrder);

        }

        return super.onOptionsItemSelected(item);
    }
}