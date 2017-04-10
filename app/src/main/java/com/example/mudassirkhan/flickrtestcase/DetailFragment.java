package com.example.mudassirkhan.flickrtestcase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mudassir Khan on 8/4/2017.
 */


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    //declare a variable to get the currentImage selected value
    String currentImage;

    private static final String IMAGE_URL_POSITION = "urlPosition";

    public DetailFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DetailFragment.
     */

    public static DetailFragment newInstance(int position) {

        final Bundle args = new Bundle();
        //get the current Image Clicked Position
        args.putInt(IMAGE_URL_POSITION,position);
        //check the value of position in Logcat
        Log.d("positionUrl",""+position);
        //Create new object DetailFragment
        final DetailFragment fragment = new DetailFragment();
        //pass the arguments to the fragment
        fragment.setArguments(args);
        //return the fragment
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         int currentPosition;
         View parentHolder;
         RecyclerView recyclerView;
        //create the object Viewpager
         ViewPager viewPager;
        //set the isDetailFragment to true because current fragment is DetailFragment
        MainActivity.isDetailFragment=true;
        // Inflate the layout for this fragment
        parentHolder= inflater.inflate(R.layout.fragment_detail, container, false);
        //get the viewpager by Id from Layout
        viewPager=(ViewPager)parentHolder.findViewById(R.id.viewPager);
        //get recyclerView by Id
        recyclerView = (RecyclerView) parentHolder.findViewById(R.id.recycler_view1);
        //set the layout for the recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        //get the Arguments value here
        final Bundle args = getArguments();
        //get the current position value and assign it to the currentPosition
        currentPosition=args.getInt(IMAGE_URL_POSITION);
       // currentImage=((MainActivity)getActivity()).imageArrayList.get(args.getInt(IMAGE_URL_POSITION)).toString();
       // Log.d("currentImage",""+currentImage);
        //set the recyclerView size to fixed
        recyclerView.setHasFixedSize(true);

    //    RecyclerView_Adapter adapter=new RecyclerView_Adapter(getActivity().getApplicationContext(),((MainActivity)getActivity()).imageArrayList);
        //set the adapter to the recyclerView
       // recyclerView.setAdapter(adapter);
        //Create an object of the RecyclerViewAdapter Class and pass the arguments to it.
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(getActivity().getApplicationContext(),((MainActivity)getActivity()).imageArrayList);
       //set the adapter to the recyclerView
        recyclerView.setAdapter(adapter);
        //go to the position the which position image is clicked
        recyclerView.getLayoutManager().scrollToPosition(currentPosition);

        /**
         * when want to show the Images in the View Pager so use this method and uncomment the following two Line
         * and set the viewpager in the layout to visible
         */

        // viewPager.setAdapter(new CustomPagerAdapter(getActivity(),((MainActivity)getActivity()).imageArrayList));
     //   viewPager.setCurrentItem(currentPosition);
        return parentHolder;
    }
}
