package com.example.mudassirkhan.flickrtestcase;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Mudassir Khan on 8/4/2017.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder  {

    //view holder is for girdview as we used in the listView
    public ImageView imageView;
    CardView cardView;
    public RecyclerViewHolder(View itemView) {
        super(itemView);
        this.imageView=(ImageView)itemView.findViewById(R.id.image);
        //this.cardView=(CardView)itemView.findViewById(R.id.card_view);

    }


}
