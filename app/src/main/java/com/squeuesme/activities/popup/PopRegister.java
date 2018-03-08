package com.squeuesme.activities.popup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squeuesme.activities.R;
import com.squeuesme.core.user.Customer;
import com.squeuesme.core.venue.Venue;

import java.util.Calendar;
import java.util.Date;

/**
 * some notes:
 * When you register, that is when you become a customer
 * this should ease how to interact between venues and customers.
 * The venue then that you are registering with can be included in the
 * construction of the customer.
 *
 * Before registering users are only potential customers.
 *
 * Created by castro on 10/02/18.
 */

public class PopRegister extends Activity {

    private RatingBar ratingBar;
    private TextView openStatus;
    private TextView pubName;
    private ImageView pubIcon;

    private Button register;
    private Button unRegister;

    private Customer customer;
    private Venue venue;
    private boolean hasActiveVenue; // need to make sure when the customer
    // leaves a certain distance from the pub that they are unregistered
    // from that venues active customers

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_register);

        ratingBar = findViewById(R.id.ratingBar);
        openStatus = findViewById(R.id.openStatus);
        pubName = findViewById(R.id.pubName);
        pubIcon = findViewById(R.id.pubImage);

        register = findViewById(R.id.btnRegister);

        Intent i = getIntent();
        final String pubName = i.getStringExtra("pubName");

        Log.i("Pub Name", pubName);

        setCorrectPopUpInfo(pubName);

        // create a new display metric object
        // to get the dimensions of the screen

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .8), (int)(height * .6));

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * in here I will create the customer,
                 * connect him to the venue by getting the venue information
                 */

                Venue venue = new Venue(pubName);

                Customer customer = new Customer();
                customer.setActiveVenue(venue);

                changeUserStatus("activeVenue", venue.getName());

            }
        });

//        unRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /**
//                 * here I will remove the user from being a customer
//                 * allowing them to receive notifications on for registration
//                 * with other pubs
//                 */
//            }
//        });

    }

    public void changeUserStatus(String _key, String _value){
        // set the value of the sharedPreferences

        /**
         * These can store small amounts of data:
         * arrays and variables.
         * Usually used for customizations in the app.
         *
         * This is stored permanently and is quite handy.
         * comment out the line that adds the string
         * to prove it works.
         */

        SharedPreferences sharedPreferences =
                this.getSharedPreferences("com.squeuesme.core",
                        Context.MODE_PRIVATE);

        sharedPreferences.edit().putString(_key, _value).apply();

        String username = sharedPreferences.getString("activeVenue", "");

        Log.i(_key, username);
    }

    public void setCorrectPopUpInfo(String _pubName){
        if(_pubName.equals("bradys"))
        {
            pubIcon.setImageResource(R.mipmap.bradys);
            pubName.setText("Brady's Clockhouse");
        }
        else if(_pubName.equals("roost"))
        {
            pubIcon.setImageResource(R.mipmap.the_roost);
            pubName.setText("The Roost");
        }
        else if(_pubName.equals("oneills"))
        {
            pubIcon.setImageResource(R.mipmap.oneils2);
            pubName.setText("O'Neills Bar");
        }
        else if(_pubName.equals("dukemans"))
        {
            pubIcon.setImageResource(R.mipmap.duke);
            pubName.setText("Duke and Coachman's");
        }
        else if(_pubName.equals("myHouse"))
        {
            pubIcon.setImageResource(R.mipmap.home_sweet);
            pubName.setText("Home Sweet Home");
        }
        else if(_pubName.equals("student_u"))
        {
            pubIcon.setImageResource(R.mipmap.student_u);
            pubName.setText("Student Union");
        }
        else if(_pubName.equals("clubEolas"))
        {
            pubIcon.setImageResource(R.mipmap.student_u);
            pubName.setText("Club Eolas");
        }

    }
}
