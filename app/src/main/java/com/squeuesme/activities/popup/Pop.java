package com.squeuesme.activities.popup;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squeuesme.activities.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by castro on 10/02/18.
 */

public class Pop extends Activity {

    private RatingBar ratingBar;
    private TextView openStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window);

        ratingBar = findViewById(R.id.ratingBar);
        openStatus = findViewById(R.id.openStatus);

        ratingBar.setRating((float) 4);

        setOpenStatus();

        // create a new display metric object
        // to get the dimensions of the screen

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .8), (int)(height * .6));

    }

    public void setOpenStatus(){

        Date currentTime = Calendar.getInstance().getTime();

        String currentString = currentTime.toString();
        String[] dateComponents = currentString.split(" ");
        String[] hhmmss = dateComponents[3].split(":");

        String day = dateComponents[0];
        int hours = Integer.parseInt(hhmmss[0]);
        int mins = Integer.parseInt(hhmmss[1]);

        Log.i("Hours", hours + "");
        Log.i("Time", mins + "");

        for(int i = 0; i < dateComponents.length; i++)
            Log.i("" + i, dateComponents[i]);

        if(day.equals("Sun"))
            if(hours <= 12 && mins <= 30 ||
                    hours >=23 && mins >= 00){
                openStatus.setText("CLOSED");
                openStatus.setTextColor(Color.RED);
            }

        else if(day.equals("Fri") || day.equals("Sat"))
            if(hours <= 10 && mins <= 30){
                openStatus.setText("CLOSED");
                openStatus.setTextColor(Color.RED);
            }

        else
            if(hours <= 10 && mins <= 30 ||
                    hours >= 23 && mins >= 30){
                openStatus.setText("CLOSED");
                openStatus.setTextColor(Color.RED);
            }

    }
}
