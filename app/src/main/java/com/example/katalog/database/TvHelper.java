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

import static com.example.katalog.database.DbTvContract.TABLE_TV;

/**
 * Created by root on 31/01/18.
 */

public class TvHelper  {

    private static final String DATABASE_TABLE = TABLE_TV;
    private static MovieDbHelper databaseHelper;
    private static TvHelper INSTANCE;
    private static SQLiteDatabase database;

    public TvHelper(Context context) {
        databaseHelper = new MovieDbHelper(context);
        database = databaseHelper.getWritableDatabase();

    }

    public static TvHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new TvHelper(context);

                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()){
            database.close();
        }
    }
    public ArrayList<Items> getAllTv(){
        ArrayList<Items> movieTvItems = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null, DbTvContract.TvEntry._ID ,null);
        cursor.moveToFirst();
        Items mMovieTvItems;
        if (cursor.getCount() > 0 ){
            do {
                mMovieTvItems = new Items();
                mMovieTvItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry._ID)));
                mMovieTvItems.setTitle_film (cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_JUDUL)));
                mMovieTvItems.setInfo_film(cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_OVERVIEW)));
                mMovieTvItems.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_POSTER)));
                mMovieTvItems.setDesc_film  (cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_RELEASE)));
                mMovieTvItems.setRate       (cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_RATING)));
                mMovieTvItems.setRating_bar(cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_RATINGBAR)));
                Log.d("overview",cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_OVERVIEW)));
                movieTvItems.add(mMovieTvItems);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return movieTvItems;
    }
    public Boolean getOne(String name){
        String querySingleRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " +DbTvContract.TvEntry.COLUMN_JUDUL+ " " + " LIKE " +"'"+name+"'" ;
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
    public long insertTv(Items mMovieTvItems){
        ContentValues args = new ContentValues();
        //args.put(DbTvContract.TvEntry._ID,mMovieTvItems.getId());
        args.put(DbTvContract.TvEntry.COLUMN_JUDUL,mMovieTvItems.getTitle_film());
        args.put(DbTvContract.TvEntry.COLUMN_OVERVIEW,mMovieTvItems.getInfo_film());
        args.put(DbTvContract.TvEntry.COLUMN_POSTER,mMovieTvItems.getPhoto());
        args.put(DbTvContract.TvEntry.COLUMN_RELEASE,mMovieTvItems.getDesc_film());
        args.put(DbTvContract.TvEntry.COLUMN_RATING,mMovieTvItems.getRate());
        args.put(DbTvContract.TvEntry.COLUMN_RATINGBAR,mMovieTvItems.getRating_bar());
        Log.d("helper",mMovieTvItems.getInfo_film());
        return database.insert(DATABASE_TABLE,null,args);
    }

    public int deleteTv(String title){
        return database.delete(TABLE_TV, DbTvContract.TvEntry.COLUMN_JUDUL+ " = '" + title + "'", null);
    }
}

