package com.example.katalog.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.katalog.LoadCallback;
import com.example.katalog.R;
import com.example.katalog.activity.DetailActivity;
import com.example.katalog.adapter.Adapter;
import com.example.katalog.database.MovieHelper;
import com.example.katalog.database.TvHelper;
import com.example.katalog.model.Items;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.katalog.activity.DetailActivity.EXTRA_DETAIL;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavTv extends Fragment implements LoadCallback, Adapter.OnItemClickListener  {

    Adapter adapter;
    ProgressBar mProgressBar;
    TvHelper mTvHelper;
    ArrayList<Items> mListFav = new ArrayList<>();
    public FavTv() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_favtv, container, false);
        mProgressBar = v.findViewById(R.id.loading_show);
        RecyclerView mRecyclerView = v.findViewById(R.id.rvshow);

        mTvHelper = TvHelper.getInstance(getContext());
        mTvHelper.open();


        adapter = new Adapter(getContext());
        adapter.setOnItemClickListener(FavTv.this);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(adapter);

        new FavTv.LoadtvAsync(mTvHelper,this).execute();

        return  v ;
    }

    @Override
    public void preExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void postExecute(ArrayList<Items> items) {
        mProgressBar.setVisibility(View.GONE);
        adapter.setmItems(items);
        mListFav.addAll(items);
    }

    @Override
    public void onItemClick(int position) {
        Items movieTv_items = new Items();
        String type = "TV";
        movieTv_items.setId(mListFav.get(position).getId());
        Log.d("id", String.valueOf(mListFav.get(position).getId()));
        movieTv_items.setPhoto(mListFav.get(position).getPhoto());
        movieTv_items.setTitle_film(mListFav.get(position).getTitle_film());
        movieTv_items.setDesc_film(mListFav.get(position).getInfo_film());
        movieTv_items.setInfo_film(mListFav.get(position).getDesc_film());
        movieTv_items.setRate(mListFav.get(position).getRate());
        movieTv_items.setRating_bar(mListFav.get(position).getRating_bar());
        movieTv_items.setType(type);
        Intent detail = new Intent(getContext(), DetailActivity.class);
        detail.putExtra(EXTRA_DETAIL,movieTv_items);
        startActivity(detail);
    }
    public class LoadtvAsync extends AsyncTask<Void, Void, ArrayList<Items>>{
        WeakReference<TvHelper> tvHelperWeakReference;
        WeakReference<LoadCallback>loadCallbackWeakReference;
        public LoadtvAsync(TvHelper mTvHelper,  LoadCallback context) {
            tvHelperWeakReference = new WeakReference<>(mTvHelper);
            loadCallbackWeakReference = new WeakReference<>(context);
        }

        @Override
        protected ArrayList<Items> doInBackground(Void... voids) {
            return tvHelperWeakReference.get().getAllTv();
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadCallbackWeakReference.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Items> movieTvItems) {
            super.onPostExecute(movieTvItems);
            loadCallbackWeakReference.get().postExecute(movieTvItems);
        }
    }

}
