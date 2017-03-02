package com.example.android.popularmovies;

/**
 * Created by Guille on 11/02/2017.
 */


    public class Movie {
        String mMovieTitle;
        String mMoviePosterLink;
        String mMovieOverview;
        String mUserRating;
        String mReleaseDate;

        public Movie(String movieTitle, String moviePosterLink, String movieOverview, String userRating, String releaseDate) {
            mMovieTitle = movieTitle;
            mMoviePosterLink = moviePosterLink;
            mMovieOverview = movieOverview;
            mUserRating = userRating;
            mReleaseDate = releaseDate;
        }

        public String getPosterPath() {
            return mMoviePosterLink;
        }
    }

