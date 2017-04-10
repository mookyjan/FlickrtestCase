package com.example.mudassirkhan.flickrtestcase;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mudassir Khan on 8/4/2017.
 */

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    ArrayList<HashMap<String ,String>> imageArrayList;
    //create an Constructor
    public CustomPagerAdapter(Context context,ArrayList<HashMap<String,String>> imageArrayList) {
        super();
        mContext=context;
        this.imageArrayList=imageArrayList;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //inflate the layout
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        ViewGroup layout=(ViewGroup) layoutInflater.inflate(R.layout.single_detail_image,container,false);

        ImageView imageView=(ImageView)layout.findViewById(R.id.image);
        //call the ImageLoadTask()
        new ImageLoadTask(imageArrayList.get(position).get("url"), imageView).execute();
        container.addView(layout);
        //return the layout
        return layout;

    }

    //this method will be called when destory the item
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return imageArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
