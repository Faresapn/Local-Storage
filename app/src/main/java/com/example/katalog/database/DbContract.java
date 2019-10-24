package com.example.katalog.database;

import android.net.Uri;
import android.provider.BaseColumns;


public class DbContract {
    static String TABLE_MOVIE = "MOVIE";


    public static final class MovieEntry implements BaseColumns {

        //Untuk Uri

        //untuk sqlite

        //1 tambah sini

        static  String COLUMN_JUDUL    = "title";
        static  String COLUMN_POSTER   ="poster_path";
        static  String COLUMN_OVERVIEW = "overview";
        static  String COLUMN_RELEASE  ="release_date";
        static  String COLUMN_RATING   ="vote_average";
        static  String COLUMN_RATINGBAR   ="vote";

    }
}
