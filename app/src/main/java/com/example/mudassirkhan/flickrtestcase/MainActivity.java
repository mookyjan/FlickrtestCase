package com.example.mudassirkhan.flickrtestcase;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mudassir Khan on 8/4/2017.
 */

public class MainActivity extends AppCompatActivity implements FlickrFragment.OnFlickrImageSelected{

    //take an ArrayList for the Images
    ArrayList<HashMap<String,String>> imageArrayList=new ArrayList<HashMap<String, String>>();
    //take an variable to check that which fragment is currently showing.
    //if the DetailFragment is currently showing so display the single_detail_image layout otherwise single_image layout inflate
    public static boolean isDetailFragment=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the screen to Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set the layout of the screen
        setContentView(R.layout.activity_main);

        //when savedInstanState state is null then set FlickrFragment in the layout
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_layout, FlickrFragment.newInstance(), "FlickerFragment")
                    .commit();
        }
    }

    //implement the interface method to send the cliked image position to the detailFragment
    //this is used for the communication between the fragments.
    @Override
    public void onFlickrImageSelected(int position) {
        //create the instance of the and pass the position to it
        final DetailFragment detailsFragment = DetailFragment.newInstance(position);
        //replace the FlickrFragment with DetailFragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, detailsFragment, "DetailFragment")
                .addToBackStack(null)
                .commit();
    }
}
