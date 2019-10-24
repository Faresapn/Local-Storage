package com.example.katalog.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.katalog.LoadCallback;
import com.example.katalog.fragment.MovieFragment;
import com.example.katalog.model.Items;
import com.example.katalog.R;
import com.example.katalog.adapter.Adapter;
import com.example.katalog.database.MovieHelper;
import com.example.katalog.database.TvHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity  {


    int id ;
    Items mMovieTvItems;
    MovieHelper movieHelper;
    TvHelper mTvHelper;
    Items movieTvItems;
    ProgressBar progressBar;
    Boolean act = true;
    Boolean insert = true;
    ArrayList<Items> mList = new ArrayList<>();
    Boolean delete = true;
    FloatingActionButton fab;
    public static final String EXTRA_DETAIL="extra_detail";
    TextView title,desc,info,rate;
    ImageView photo;
    String type;
    RatingBar ratingbar;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        fab =  findViewById(R.id.fab);
        title = findViewById(R.id.txt_tittle);
        desc = findViewById(R.id.txt_rilis);
        info = findViewById(R.id.txt_dtl);
        ratingbar = findViewById(R.id.ratingBar);
        rate = findViewById(R.id.txtRate);
        photo = findViewById(R.id.img_film);
        progressBar = findViewById(R.id.loading_film);
        mMovieTvItems = new Items();
        movieTvItems = getIntent().getParcelableExtra(EXTRA_DETAIL);
        progressBar.setVisibility(View.VISIBLE);
        Log.d("iddd", String.valueOf(movieTvItems.getId()));
        type = movieTvItems.getType();
        Log.d("type",type);
        movieHelper = MovieHelper.getInstance(getApplicationContext());
        mTvHelper = TvHelper.getInstance(getApplicationContext());
        String name = movieTvItems.getTitle_film();

        if(type.equals("MOVIE")  && movieHelper.getOne(name) ){
            //delete movie
            Log.d("movie test","test");
            act = false;
            delete = true;
            fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_is_favorite));
        }else if(type.equals("MOVIE") && !movieHelper.getOne(name)){
            // savemovie
            Log.d("movie test","test");
            act = true;
            insert = true;
            fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_not_favorite));
        }
        else if (type.equals("TV") && mTvHelper.getOne(name)){
            //delete tv
            act = false;
            delete = false;
            fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_is_favorite));

        }else if(type.equals("TV") && !mTvHelper.getOne(name)){
            //save tv
            act = true;
            insert = false;
            fab.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_not_favorite));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabclick();
            }
        });
        showLoading(true);
        Show();

    }



    private void Show(){
        progressBar.setVisibility(View.GONE);
        Items items = getIntent().getParcelableExtra(EXTRA_DETAIL);
        title.setText(items.getTitle_film());
        desc.setText(items.getDesc_film());
        info.setText(items.getInfo_film());
        ratingbar.setRating(Float.valueOf(items.getRating_bar()) / 2);
        rate.setText(items.getRating_bar());
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500" + items.getPhoto()).into(photo);
    }

    private void fabclick(){
        if (insert && act && type.equals("MOVIE")){
            mMovieTvItems.setTitle_film(movieTvItems.getTitle_film());
            mMovieTvItems.setDesc_film(movieTvItems.getDesc_film());
            mMovieTvItems.setPhoto(movieTvItems.getPhoto());
            mMovieTvItems.setInfo_film(movieTvItems.getInfo_film());
            mMovieTvItems.setRate(movieTvItems.getRate());
            mMovieTvItems.setRating_bar(movieTvItems.getRating_bar());
            Log.d("savemovie",desc.getText().toString());
            long result = movieHelper.insertMovie(mMovieTvItems);

            if(result > 0){
                Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_is_favorite));
            }else {
                Toast.makeText(DetailActivity.this, R.string.addf, Toast.LENGTH_SHORT).show();
            }
        }else if(!insert&&act && type.equals("TV") ){
            mMovieTvItems.setTitle_film(movieTvItems.getTitle_film());
            mMovieTvItems.setDesc_film(movieTvItems.getDesc_film());
            mMovieTvItems.setPhoto(movieTvItems.getPhoto());
            mMovieTvItems.setInfo_film(movieTvItems.getInfo_film());
            mMovieTvItems.setRate(movieTvItems.getRate());
            mMovieTvItems.setRating_bar(movieTvItems.getRating_bar());
            Log.d("savetv",desc.getText().toString());
            long result = mTvHelper.insertTv(mMovieTvItems);
            if(result > 0){
                Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_is_favorite));

            }else {
                Toast.makeText(DetailActivity.this, R.string.addf, Toast.LENGTH_SHORT).show();
            }
        }else if(delete && !act && type.equals("MOVIE")) {
            Log.d("deletemovie", desc.getText().toString());

            long result = movieHelper.deleteMovie(movieTvItems.getTitle_film());
            if (result > 0) {
                Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailActivity.this, FavoritActivity.class));
        }else {
                Toast.makeText(DetailActivity.this, R.string.addf, Toast.LENGTH_SHORT).show();

            }
        }else if(!delete && !act && type.equals("TV")) {

                Log.d("deletetv", desc.getText().toString());
                long result = mTvHelper.deleteTv(movieTvItems.getTitle_film());
                if (result > 0) {
                    Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, FavoritActivity.class));
                }if(result > 0 ){
                    Toast.makeText(DetailActivity.this, R.string.add, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, MainActivity.class));
            }else {
                Toast.makeText(DetailActivity.this, R.string.addf, Toast.LENGTH_SHORT).show();

            }
        }

    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


}
