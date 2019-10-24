package com.example.katalog.database;

import android.net.Uri;
import android.provider.BaseColumns;


public class DbTvContract {
    static String TABLE_TV = "TV";

    public static final class TvEntry implements BaseColumns {

        //Untuk Uri


        //untuk sqlite

         static  String COLUMN_JUDUL =   "original_name";
         static  String COLUMN_POSTER =    "poster_path"  ;
         static  String COLUMN_OVERVIEW = "overview";
         static  String COLUMN_RELEASE =  "first_air_date";
         static  String COLUMN_RATING =  "vote_average";
         static  String COLUMN_RATINGBAR =  "vote";
        //1 tambah sini

    }
}
