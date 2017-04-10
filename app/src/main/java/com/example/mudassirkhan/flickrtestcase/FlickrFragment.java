package com.example.mudassirkhan.flickrtestcase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Mudassir Khan on 8/4/2017.
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * interface {@link OnFlickrImageSelected }mListener
 * to handle interaction events.
 * Use the {@link FlickrFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlickrFragment extends Fragment {

    //progress Dialog
    ProgressDialog pd;
    ImageView imageView;

    //RecyclerView
    private RecyclerView recyclerView;

    View parentHolder;
    Context context;
    private OnFlickrImageSelected mListener;

    public FlickrFragment() {
        // Required empty public constructor
    }


    //create an instance of the FlickrFragment
    public static FlickrFragment newInstance() {

        return new FlickrFragment();
    }

    //create an Interface OnFlickrImageSelected and declare a method in it
    public interface OnFlickrImageSelected {
        //declare a method to get the position of the image
        void onFlickrImageSelected(int position);
    }
    //Override the onAttach() to check that the interface method is implemted or not
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //check that the context is of the interface
        if (context instanceof OnFlickrImageSelected) {
            mListener = (OnFlickrImageSelected) context;
        } else {
            //this will throw an exception if the interface method is not implemeted
            throw new ClassCastException(context.toString() + " must implement OnFlickrImageSelected.");
        }

    }

    /**
     * this is method we initialize and set the layout of the fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       parentHolder= inflater.inflate(R.layout.fragment_flickr, container, false);
        //get the context
        final Activity activity = getActivity();
        //set the isDetailFragment to false because it is now in Flickrfragment
        MainActivity.isDetailFragment=false;
       //initialize the recyclerView from the layout
        recyclerView = (RecyclerView) parentHolder.findViewById(R.id.recycler_view1);
        //set the layout for the Recyclerview to GridLayoutManager and number of items per row to 2
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));

        //check for the internet connection if connection is available call the Flickrtask
        if (isNetworkAvailable()){
            //this api call with no signIn required
         //   new FlickrTask().execute("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=264e9e6b23f28263d2bf0d0cee47a21c&tags=fashion&format=json&nojsoncallback=1");

            //Api call which require sign In and no Authentication
            //   new FlickrTask().execute("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=9da2d7f5b500d3f829998a98e4fbb2fb&tags=fashion&format=json&nojsoncallback=1&api_sig=255be05ae02fdb4a27860afc0d5c3b26");
           new FlickrTask().execute("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ace70ad638f437514b55595ba82b7126&tags=fashion&format=json&nojsoncallback=1&api_sig=50fa50772e000cb7db9b27b6e1029cb1");
            //api call which require sign In and Authetication
            //  new FlickrTask().execute("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=9da2d7f5b500d3f829998a98e4fbb2fb&tags=fashion&format=json&nojsoncallback=1&auth_token=72157682355762595-01208504a7f554bd&api_sig=6058e7aad171c5ccb73bf1759df1c693");
        }
        else {
            Toast.makeText(getActivity(),"Plz Check for internet Connection",Toast.LENGTH_LONG).show();
        }
        //return the view
        return parentHolder;
    }

    /**
     * Method to populate the Recyclerview form the adapter
     */
    public void populateRecyclerView(){
        //set the adapter to the recyclerView
        recyclerView.setAdapter(new RecyclerViewAdapter(getActivity(),((MainActivity)getActivity()).imageArrayList,mListener));

    }

    /**
     * method to check the internet Connection when want to get data From Api
     * @return
     */
    public boolean isNetworkAvailable() {
        // Get Connectivity Manager class object from Systems Service
        ConnectivityManager cm = (ConnectivityManager)  getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get Network Info from connectivity Manager
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        //return false if not connected with internet
        return false;
    }


    /**
     * We are using the AsyncTask to get data from the Api
     */
    private class FlickrTask extends AsyncTask<String, String, JSONObject> {

        //this method invoked the UI thread before the task is executed
        protected void onPreExecute() {
            super.onPreExecute();
            //set the progress bar
            pd = new ProgressDialog(getActivity());
            //set the title for the progress dialog
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected JSONObject doInBackground(String... params) {
            //create an object of the HttpURLConnection
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                //get the url
                URL url = new URL(params[0]);
                //make connection of the url
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                //get data and store it in input Stream
                InputStream stream = connection.getInputStream();
                //read data from the input stream
                reader = new BufferedReader(new InputStreamReader(stream));
                //create and initialize the object of the StringBuffer
                StringBuffer buffer = new StringBuffer();
                //declare a String varaible
                String line = "";
                //loop on the buffer Reader to get all the data from it
                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    //check the response value in Logcat
                    Log.d("Response: ", "> " + line);

                }
                //return the data in form of the JosnObject
                return new JSONObject(buffer.toString());

            } catch (MalformedURLException e) {
                // check for the url Malformed Exception
                e.printStackTrace();
            } catch (IOException e) {
                //check for the Io Excaption
                e.printStackTrace();
            }
            catch (JSONException ex){
                //check for the JosnException
                ex.printStackTrace();
            }
            finally {
                //if connection is already made then diconnect it
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        //this method is nvoked on the UI thread after the background computation finishes.
        @Override
        protected void onPostExecute(JSONObject response) {
            super.onPostExecute(response);
            //if the progress bar is showing then dismiss it
            if (pd.isShowing()) {
                pd.dismiss();
            }
            //check if the response is not  null
            if (response != null) {
                try {
                    //create an object of the JSONObject
                    JSONObject jsonObject;
                    //declare variable for stat to get its Value
                    String stat, code, message;
                    //get the stat value
                    stat = response.getString("stat");
//                    code = response.getString("code");
                    //                  message = response.getString("message");
                    //check  the stat value if success it then go for further work and get data other wise show error message
                    if (stat.equals("fail")) {
                        Toast.makeText(getActivity(), "Can not access Server "+stat, Toast.LENGTH_LONG).show();
                    } else {
                        //get the jsonObject id photos
                        jsonObject = response.getJSONObject("photos");
                        //show the response in log
                        Log.d("App", "Success: " + jsonObject.toString());
                        //decalre the varaibale for page,pages,perPage,totalpages
                        String jsonResponse, page, pages, perPage, totalPages;
                        //get the values one bt one
                        //these are the extra details when required will be shown
                        page = jsonObject.optString("page");
                        pages = jsonObject.optString("pages");
                        perPage = jsonObject.optString("perpage");
                        totalPages = jsonObject.getString("total");
                        jsonResponse = page + " pages " + pages + " " + perPage + " " + totalPages;
                        //create an object of the JsonArray
                        JSONArray jsonArray;
                        JSONObject jsonObject1;
                        String id = "", url = "";
                        //get the JsonArray
                        jsonArray = jsonObject.getJSONArray("photo");
                        //make the loop on the jsonArray to get the values of the item
                        for (int i = 0; i < jsonArray.length(); i++) {

                            jsonObject1 = jsonArray.getJSONObject(i);
                            id = jsonObject1.optString("id");
                            //make the url of the image
                            url = "http://farm" + jsonObject1.getInt("farm") + ".staticflickr.com/" + jsonObject1
                                    .getInt("server") + "/" + id + "_" + jsonObject1.getString("secret") + ".jpg";
                            //create an HashMap
                            HashMap<String, String> arrayHashMap = new HashMap<String, String>();
                            arrayHashMap.put("id", id);
                            arrayHashMap.put("url", url);
                            //add the hasHMap id to the imageArrayList
                            ((MainActivity)getActivity()).imageArrayList.add(arrayHashMap);

                        }
                        //call the PopulateRecyclerView to populate the recyclerView
                       populateRecyclerView();
                        // recyclerView.setAdapter(new RecyclerViewAdapter(getActivity(),((MainActivity)getActivity()).imageArrayList,this));
                        Log.d("responseId", "" + jsonResponse + " id=" + id + " url " + url + " imagList " + ((MainActivity)getActivity()).imageArrayList);

                    }
                }catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }
}
