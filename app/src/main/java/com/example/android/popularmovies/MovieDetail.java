package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Guille on 11/02/2017.
 */

public class MovieDetail extends AppCompatActivity {

    private TextView mTitulo;
    private String titulo;

    private TextView mPlot;
    private String plot;

    private TextView mNota;
    private String nota;

    private TextView mfecha;
    private String fecha;

    private ImageView mPoster;
    private String poster;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mTitulo=(TextView)findViewById(R.id.tv_original_title);
        mPlot=(TextView)findViewById(R.id.tv_plot);
        mNota=(TextView)findViewById(R.id.tv_rating);

        mfecha=(TextView)findViewById(R.id.release_date);

        mPoster=(ImageView) findViewById(R.id.iv_cartel);

        Intent intentThatStartedThisActivity=getIntent();

        if (intentThatStartedThisActivity!=null){
            if (intentThatStartedThisActivity.hasExtra("titulo")){
                titulo=intentThatStartedThisActivity.getStringExtra("titulo");
                mTitulo.setText(titulo);

                plot=intentThatStartedThisActivity.getStringExtra("plot");
                mPlot.setText(plot);


                nota=intentThatStartedThisActivity.getStringExtra("nota");

                mNota.setText("Rating "+nota);

                fecha=intentThatStartedThisActivity.getStringExtra("fecha");
                mfecha.setText("Release date "+fecha);

                poster=intentThatStartedThisActivity.getStringExtra("img");

                Picasso.with(this).load(poster)
                        .into(mPoster);



            }
        }


    }
}
