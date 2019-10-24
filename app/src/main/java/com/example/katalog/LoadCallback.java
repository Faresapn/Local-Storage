package com.example.katalog;

import com.example.katalog.model.Items;

import java.util.ArrayList;

public interface LoadCallback {
    void preExecute();
    void postExecute(ArrayList<Items> mMovieTvItems);
}
