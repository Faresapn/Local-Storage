package com.example.katalog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katalog.model.Items;
import com.example.katalog.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.CategoryViewHolder> {
    private Context context;
    private ArrayList<Items> mList = new ArrayList<>();
    View v;
    private OnItemClickListener mListener;


    public Adapter(Context context) {
        this.context = context;
    }

    public Adapter(Context context, ArrayList<Items> movieTvItems) {
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public  void setmItems(ArrayList<Items> movieTvItems) {
        mList.clear();
        this.mList.addAll(movieTvItems);
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    //ViewHolder
    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Items items = mList.get(position);
        Log.d("adapter", items.getTitle_film());
        holder.mTextView1.setText(items.getTitle_film());
        holder.mTextView2.setText(items.getDesc_film());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + items.getPhoto()).into(holder.mImageView);


    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_show);
            mTextView1 = itemView.findViewById(R.id.tittle_show);
            mTextView2 = itemView.findViewById(R.id.releas_show);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        return new CategoryViewHolder(v);
    }
}