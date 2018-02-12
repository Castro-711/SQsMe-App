package com.squeuesme.activities.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squeuesme.activities.R;
import com.squeuesme.activities.popup.Pop;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locaionManager;
    private String provider;
    private Marker roostMarker;
    private Marker lastMarker;
    private LatLng current;
    private Double lat;
    private Double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locaionManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locaionManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Double lat = 53.381295;
        Double lng =  -6.591918;

        LatLng current = new LatLng(lat, lng);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // satellite map view
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // a marker to keep track of last marker


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                String currentMarkerTitle = marker.getTitle();

                marker.setIcon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_ORANGE));

                // now change last marker back to green if not same one clicked
                if(lastMarker != null)
                    if(!currentMarkerTitle.equals(lastMarker.getTitle()))
                        lastMarker.setIcon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_GREEN));

                lastMarker = marker;

                Intent i = new Intent(MapsActivity.this, Pop.class);
                i.putExtra("markerTitle", currentMarkerTitle);


                if(!currentMarkerTitle.equals("User")){
                    startActivity(i);
//                    overridePendingTransition(
//                            android.R.anim.fade_in, android.R.anim.fade_out);
                }

                return false;
            }
        });

        mMap.clear();

        lat = 53.381295;
        lng =  -6.591918;

        // Add a marker in Sydney and move the camera
        current = new LatLng(lat, lng);
        roostMarker = mMap.addMarker(new MarkerOptions()
                .position(current)
                .title("User"));

        addFixedPubLocations();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 16));

    }

    public void addFixedPubLocations(){

        LatLng roost = new LatLng(53.381078, -6.592405);

        // add a marker for the roost
        mMap.addMarker(new MarkerOptions()
                .position(roost)
                .title("The Roost")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng bradys = new LatLng(53.381596, -6.590388);

        // add a marker for the bradys
        mMap.addMarker(new MarkerOptions()
                .position(bradys)
                .title("Brady's Clockhouse")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng oneils = new LatLng(53.381476, -6.591675);

        // add a marker for the oneills
        mMap.addMarker(new MarkerOptions()
                .position(oneils)
                .title("O'Neills Bar")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng dukeman = new LatLng(53.381324, -6.591422);

        // add a marker for the bradys
        mMap.addMarker(new MarkerOptions()
                .position(dukeman)
                .title("The Duke and Coachman")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng studentU = new LatLng(53.382964, -6.603597);

        // add a marker for the bradys
        mMap.addMarker(new MarkerOptions()
                .position(studentU)
                .title("Student Union Bar")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng eolasB = new LatLng(53.384649, -6.601387);

        // add a marker for the bradys
        mMap.addMarker(new MarkerOptions()
                .position(eolasB)
                .title("Club Eolas")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("Lat", lat.toString());
        Log.i("Lng", lng.toString());

        // to get address
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            List<Address> adressesList = geocoder.getFromLocation(lat, lng, 1);

            if(adressesList != null && adressesList.size() > 0)
                Log.i("PlaceInfo", adressesList.get(0).toString());
        }
        catch (IOException e) { e.printStackTrace(); }

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

    @Override
    protected void onPause() {
        super.onPause();

        locaionManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locaionManager.requestLocationUpdates(provider, 400, 1, this);
    }
}
