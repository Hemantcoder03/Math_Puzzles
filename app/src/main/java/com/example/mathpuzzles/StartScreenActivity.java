package com.example.mathpuzzles;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //set delay
                try{
                    sleep(2000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally {

                    //set the startActivity
                    startActivity(new Intent(StartScreenActivity.this,StartActivity.class));
                    finish();
                }
            }
        });
        thread.start();
    }
}