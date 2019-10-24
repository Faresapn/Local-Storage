package com.example.katalog.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.katalog.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieVM extends AndroidViewModel {

    private MutableLiveData<ArrayList<Items>> items = new MutableLiveData<>();
    public static ArrayList<Items> mitems = new ArrayList<>();
     private    RequestQueue rq;
     private String url;

    public MovieVM(@NonNull Application application) {
        super(application);
        rq = Volley.newRequestQueue(application);
        url = application.getResources().getString(R.string.api_movie);
    }



    public void getAPI() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    int length = jsonArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject result = jsonArray.getJSONObject(i);

                        String title = result.getString    ("title");
                        String photo = result.getString   ("poster_path");
                        String overview = result.getString("overview");
                        String realease = result.getString("release_date");
                        String rating_bar = result.getString("vote_average");
                        String rate = result.getString     ("vote_average");
                        Log.d("title", title);
                        Items items = new Items();
                        items.setTitle_film(title);
                        items.setPhoto(photo);
                        items.setInfo_film(overview);
                        items.setDesc_film(realease);
                        items.setRating_bar(rating_bar);
                        items.setRate(rate);
                        mitems.add(items);
                    //realease,title,overview,photo,rating_bar,rate
                    }

                    items.postValue(mitems);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );
        rq.add(request);
    }

    public LiveData<ArrayList<Items>> getShow() {

        return items;
    }
}
