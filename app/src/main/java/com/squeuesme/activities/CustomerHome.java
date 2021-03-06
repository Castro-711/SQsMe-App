package com.squeuesme.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.squeuesme.activities.map.MapsActivity;
import com.squeuesme.activities.order.OrderBuilderActivity;
import com.squeuesme.activities.popup.PopRegister;
import com.squeuesme.core.Customer;
import com.squeuesme.core.Order;
import com.squeuesme.core.Venue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CustomerHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    private NavigationView navigationView;
    private LocationManager locationManager;
    private String provider;
    private boolean hasPromptedCustomer;
    private boolean customerHasVenue;
    private HashMap<String, LatLng> pubCoordinates;

    private Customer customer;
    private Venue venue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.BLACK);
        setSupportActionBar(toolbar);

//        findViewById(R.layout.activity_customer_home.

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        TextView tv  = findViewById(R.id.btn_horizontal_ntb);
        setupPhoneUI();
//        tv.setText(getOrder());

        if(customerHasVenue) {
            @SuppressLint("ResourceType") View view = findViewById(R.layout.activity_customer_home);
            view.findViewById(R.id.leaveVenue).setBackgroundColor(Color.WHITE);

        }

//        getOrder();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundColor(Color.BLACK);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        navigationView.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));

        setUpMenuItems();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        setUpHashMap();
        onLocationChanged(location); // call to get it to act fast if user present at venue

    }

    public void setupObjects(){
//        if(customer == null)
//        customer = null;
//        venue = null;
    }

    public void setUpHashMap(){

        LatLng roost = new LatLng(53.381078, -6.592405);
        LatLng bradys = new LatLng(53.381596, -6.590388);
        LatLng oneills = new LatLng(53.381476, -6.591675);
        LatLng dukemans = new LatLng(53.381324, -6.591422);
        LatLng studentU = new LatLng(53.382964, -6.603597);
        LatLng clubEolas = new LatLng(53.384649, -6.601387);
        LatLng myHouse = new LatLng(53.321530, -6.374587);

        pubCoordinates = new HashMap<>();
        pubCoordinates.put("roost", roost);
        pubCoordinates.put("bradys", bradys);
        pubCoordinates.put("oneills", oneills);
        pubCoordinates.put("dukemans", dukemans);
        pubCoordinates.put("studenU", studentU);
        pubCoordinates.put("clubEolas", clubEolas);
        pubCoordinates.put("myHouse", myHouse);
    }

    public void setUpMenuItems() {

        Menu menu = navigationView.getMenu();

        MenuItem orderBuilder = menu.findItem(R.id.nav_camera);
        orderBuilder.setTitle("Order Builder");
        orderBuilder.setIcon(R.drawable.menu_venue);

        MenuItem favourites = menu.findItem(R.id.nav_gallery);
        favourites.setTitle("Favourites");
        favourites.setIcon(R.drawable.favourites_ic);

        MenuItem nearbyVenue = menu.findItem(R.id.nav_slideshow);
        nearbyVenue.setTitle("Nearby Venues");
        nearbyVenue.setIcon(R.drawable.ic_locate_venue);

        menu.removeItem(R.id.nav_manage);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(CustomerHome.this, OrderBuilderActivity.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(CustomerHome.this, MapsActivity.class));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        locationManager.requestLocationUpdates(
                provider, 400, 1, this);

//        if(customerHasVenue)
//            Log.i("activeVenue", ""+customer.getActiveVenue());
    }

    @Override
    public void onLocationChanged(Location location) {

        Double lat = location.getLatitude();
        Double lng = location.getLongitude();

       checkIfVenueClose(new LatLng(lat, lng));
    }

    public void checkIfVenueClose(LatLng latLng){

        Double lat = latLng.latitude;
        Double lng = latLng.longitude;

        /**
         * Iterates through the hash map to check if the we are at one
         * of the pubs.
         */

        Iterator iterator = pubCoordinates.entrySet().iterator();

        float[] results = new float[10];

        while(iterator.hasNext()){

            Map.Entry pair = (Map.Entry) iterator.next();
            LatLng current = (LatLng) pair.getValue();
            String pub = pair.getKey().toString();

            Location.distanceBetween(current.latitude, current.longitude, lat, lng,results);

            Log.i("lat", lat + "");
            Log.i("lng", lng + "");
            Log.i("pubName", pub);

            Intent i = new Intent(CustomerHome.this, PopRegister.class);
            i.putExtra("pubName", pub);

            if(results[0] < 30.0f && !hasPromptedCustomer
                    && !customerHasVenue){
                hasPromptedCustomer = true;

                Log.i("pubName checkIfVenue", pub);
                startActivityForResult(i, 1);

            } // if
        } // while
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public void setupPhoneUI(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.BLACK);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("I am the requestCode", ""+requestCode);

        Log.i("The REQUEST_CODE", ""+   requestCode);

        SharedPreferences sharedPreferences =
                this.getSharedPreferences("com.squeuesme.core",
                        Context.MODE_PRIVATE);

        // this gets executed if customer wants to order from this pub
        if(resultCode == RESULT_OK)
        {
            customerHasVenue = true;
            customer = new Customer();

            // get the customers uid
            sharedPreferences.edit().putString("customerId", customer.getUniqueId());
            String venueName = sharedPreferences.getString("activeVenue", null);

            findViewById(R.id.leaveVenue).setBackgroundColor(Color.WHITE);
            findViewById(R.id.venueView).setBackgroundColor(Color.BLACK);
            TextView tv = findViewById(R.id.venueView);
            tv.setText("Current Pub: " + venueName.toUpperCase());

            Log.i("The REQUEST_CODE", ""+   requestCode);
        }

    }

    public String getOrder(){

        // to get the data out of the db
        // a cursor allows us to loop through all the data

        // can use WHERE, AND, LIKE, LIMIT to improve query types
        // WHERE name = Ryan
        // WHERE name = Ryan AND age < 20
        // WHERE name LIKE "R%" - results names beginning with R
        // LIMIT will limit the number of results returned
        // LIMIT is very useful when using delete statements
        SQLiteDatabase db = this.openOrCreateDatabase("orders", MODE_PRIVATE, null);

        // DELETE FROM users WHERE name = 'Eric' LIMIT 1
        // UPDATE users SET age = 20 WHERE name = 'Ryan'
        Cursor c = db.rawQuery("SELECT * FROM orders", null);

        // get the column indexes - different to mysql
        int nameIndex = c.getColumnIndex("name");
        int idIndex = c.getColumnIndex("id");
        int contentIndex = c.getColumnIndex("contents");

        ArrayList<Order> orders = new ArrayList<>();

        // move to first result
        c.moveToFirst();
        while(c != null){

            Log.i("Name", c.getString(nameIndex) + "");
            Log.i("Id", c.getString(idIndex) + "");
            Log.i("Contents", c.getString(contentIndex) + "");

            orders.add(new Order());

            // move to next result
            c.moveToNext();
        }

        return orders.toString();
    }
}
