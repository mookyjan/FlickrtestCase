package com.example.mudassirkhan.flickrtestcase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/**
 * Created by Mudassir Khan on 8/4/2017.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //this is timer third which hold the splash screen for 3 seconds for display
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    //sleep for 3 second
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    //go to the mainActivity from Splash Screen
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}