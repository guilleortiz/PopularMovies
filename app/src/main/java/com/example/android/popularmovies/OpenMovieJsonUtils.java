package com.example.android.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Guille on 11/02/2017.
 */

public final class OpenMovieJsonUtils {


    public static ArrayList<Movie> getSimpleMovieStringsFromJson(String movieJsonStr) {


        ArrayList<Movie> movieArrayList=new ArrayList<>();


        try {

            //String[] parsedMovieData = null;

            JSONObject movieJson = null;

            movieJson = new JSONObject(movieJsonStr);



            JSONArray results = movieJson.getJSONArray("results");

            //parsedMovieData = new String[results.length()];



            for (int i = 0; i < results.length(); i++) {


                JSONObject oneMovieData=results.getJSONObject(i);

                String originalTitle=oneMovieData.getString("original_title");
                String poster_path= "http://image.tmdb.org/t/p/w780"+oneMovieData.getString("poster_path");
                String overview = oneMovieData.getString("overview");
                String userRating = String.valueOf(oneMovieData.getDouble("vote_average"));
                String releaseDate = oneMovieData.getString("release_date");

                movieArrayList.add(new Movie(originalTitle,poster_path,overview,userRating,releaseDate));


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieArrayList;
    }

}