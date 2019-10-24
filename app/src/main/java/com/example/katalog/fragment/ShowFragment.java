package com.example.katalog.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.katalog.model.Items;
import com.example.katalog.R;
import com.example.katalog.activity.DetailActivity;
import com.example.katalog.adapter.Adapter;
import com.example.katalog.model.ShowVM;

import java.util.ArrayList;

import static com.example.katalog.activity.DetailActivity.EXTRA_DETAIL;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends Fragment implements Adapter.OnItemClickListener {


    Adapter adapter;
    ShowVM showVM;
    ProgressBar mProgressBar;





    public ShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show, container, false);

        mProgressBar = v.findViewById(R.id.loading_show);

        adapter = new Adapter(getContext());
        adapter.setOnItemClickListener(ShowFragment.this);
        adapter.notifyDataSetChanged();

        showVM= ViewModelProviders.of(getActivity()).get(ShowVM.class);
        showVM.getShow().observe(ShowFragment.this,getShow);
        showVM.getAPI();

        RecyclerView mRecyclerView = v.findViewById(R.id.show);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(adapter);

        return v;
    }
    private Observer<ArrayList<Items>> getShow = new Observer<ArrayList<Items>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Items> movieItems) {
            if (movieItems != null) {
                adapter.setmItems(movieItems);
                showLoading(false);
            }
        }
    };
    private void showLoading(Boolean state) {
        if (state) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }



    @Override
    public void onItemClick(int i) {
        Items items = new Items();
        String type = "TV";
        items.setPhoto(ShowVM.mitems.get(i).getPhoto());
        items.setTitle_film(ShowVM.mitems.get(i).getTitle_film());
        items.setDesc_film(ShowVM.mitems.get(i).getDesc_film());
        items.setInfo_film(ShowVM.mitems.get(i).getInfo_film());
        items.setRate(ShowVM.mitems.get(i).getRate());
        items.setRating_bar(ShowVM.mitems.get(i).getRating_bar());
        items.setType(type);

        Intent detail = new Intent(getContext(), DetailActivity.class);

        detail.putExtra(EXTRA_DETAIL,items);
        startActivity(detail);
    }
}
