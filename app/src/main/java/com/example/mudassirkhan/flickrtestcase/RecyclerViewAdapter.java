package com.example.mudassirkhan.flickrtestcase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mudassir Khan on 8/4/2017.
 */


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private LayoutInflater mLayoutInflater;
    ArrayList<HashMap<String,String>> imageArrayList;
    //create an object of the RecyclerViewClickListener
    private static FlickrFragment.OnFlickrImageSelected mListener;
    Context context;
    //Create an Constructor
    public RecyclerViewAdapter(Context context, ArrayList<HashMap<String,String>> imageArrayList,FlickrFragment.OnFlickrImageSelected mListener) {
        this.context=context;
        mLayoutInflater = LayoutInflater.from(context);
        this.imageArrayList=imageArrayList;
        this.mListener=mListener;
    }

    //constructor for the DetailFragment
    public RecyclerViewAdapter(Context context,ArrayList<HashMap<String,String>> imageArrayList){
        this.context=context;
        mLayoutInflater = LayoutInflater.from(context);
        this.imageArrayList=imageArrayList;
    }

    //Override the onCreateViewHolder method to inflate the layout
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView;
        if (MainActivity.isDetailFragment){
            itemView=mLayoutInflater.inflate(R.layout.single_detail_image,viewGroup,false);
        }else {
            itemView = mLayoutInflater.inflate(R.layout.single_image, viewGroup, false);
        }
       //return the view
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, final int position) {

        //call the ImageLoadTask to load the images
        new ImageLoadTask(imageArrayList.get(viewHolder.getAdapterPosition()).get("url"), viewHolder.imageView).execute();
        if (!MainActivity.isDetailFragment){
            //onclick go to the DetailFragment
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onFlickrImageSelected(viewHolder.getAdapterPosition());
                }
            });
        }
    }

    //this method return the number of items in the List
    @Override
    public int getItemCount() {
        return (null!=imageArrayList?imageArrayList.size():0);
    }
}


