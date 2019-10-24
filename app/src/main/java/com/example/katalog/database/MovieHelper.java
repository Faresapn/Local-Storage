package com.example.katalog.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.katalog.model.Items;

import java.util.ArrayList;

import static com.example.katalog.database.DbContract.TABLE_MOVIE;


public class MovieHelper {

    //2 membuat Uri Matcher
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static MovieDbHelper moviedb;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;


    public MovieHelper(Context context) {

        moviedb = new MovieDbHelper(context);
        database = moviedb.getWritableDatabase();
    }

    public static MovieHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new MovieHelper(context);

                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = moviedb.getWritableDatabase();
    }

    public void close() {
        moviedb.close();
        if (database.isOpen()){
            database.close();
        }
    }

    public ArrayList<Items> getAllFilm(){
        ArrayList<Items> movieTvItems = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null, DbContract.MovieEntry._ID ,null);
        cursor.moveToFirst();
        Items mMovieTvItems;
        if (cursor.getCount() > 0 ){
            do {
                mMovieTvItems = new Items();
                mMovieTvItems.setId        (cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.MovieEntry._ID)));
                mMovieTvItems.setTitle_film(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MovieEntry.COLUMN_JUDUL)));
                mMovieTvItems.setDesc_film (cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MovieEntry.COLUMN_RELEASE)));
                mMovieTvItems.setPhoto     (cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MovieEntry.COLUMN_POSTER)));
                mMovieTvItems.setInfo_film (cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MovieEntry.COLUMN_OVERVIEW)));
                mMovieTvItems.setRate      (cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MovieEntry.COLUMN_RATING)));
                mMovieTvItems.setRating_bar(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MovieEntry.COLUMN_RATINGBAR)));
                Log.d("idhelper", String.valueOf(Integer.valueOf(cursor.getInt(0))));
                movieTvItems.add(mMovieTvItems);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return movieTvItems;
    }
    public Boolean getOne(String name){
        String querySingleRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " +DbContract.MovieEntry.COLUMN_JUDUL+ " " + " LIKE " +"'"+name+"'" ;
//        Cursor cursor = database.query(DATABASE_TABLE,null,"'"+name+"'",null,null,null,null ,null);
        Cursor cursor = database.rawQuery(querySingleRecord,null);
        cursor.moveToFirst();
        Log.d("cursor", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0 ){

            return true;
        }else if(cursor.getCount() == 0){
            return false;
        }
//        cursor.close();
        return false;
    }

    public long insertMovie(Items mMovieTvItems){
        ContentValues args = new ContentValues();
       // args.put(DbContract.MovieEntry._ID,mMovieTvItems.getId());
        args.put(DbContract.MovieEntry.COLUMN_JUDUL,mMovieTvItems.getTitle_film());
        args.put(DbContract.MovieEntry.COLUMN_OVERVIEW,mMovieTvItems.getInfo_film());
        args.put(DbContract.MovieEntry.COLUMN_POSTER,mMovieTvItems.getPhoto());
        args.put(DbContract.MovieEntry.COLUMN_RELEASE,mMovieTvItems.getDesc_film());
        args.put(DbContract.MovieEntry.COLUMN_RATING,mMovieTvItems.getRate());
        args.put(DbContract.MovieEntry.COLUMN_RATINGBAR,mMovieTvItems.getRating_bar());
        Log.d("helper",mMovieTvItems.getDesc_film());
        return database.insert(DATABASE_TABLE,null,args);
    }

    public int deleteMovie(String title){
        return database.delete(TABLE_MOVIE, DbContract.MovieEntry.COLUMN_JUDUL+ " = " + "'"+title+"'" , null);
    }

}
