package com.ing_sebasparra.lector;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class Prueba extends Activity implements LocationListener {
    // public class MainActivity extends Activity implements LocationListener {

    Intent intentThatCalled;
    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;

    String voice2text; //added

    TextView la,lo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        intentThatCalled = getIntent();
        voice2text = intentThatCalled.getStringExtra("v2txt");
        getLocation();
        iniciar();
    }

    private void iniciar() {
        la=(TextView)findViewById(R.id.lati);
        lo=(TextView)findViewById(R.id.longitud);
    }

    public static boolean isLocationEnabled(Context context) {
        //...............
        return true;
    }

    protected void getLocation() {
        if (isLocationEnabled(Prueba.this)) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            Location location = locationManager.getLastKnownLocation(bestProvider);
                if (location != null) {
                    Log.e("TAG", "GPS is on");
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Toast.makeText(Prueba.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
               //     searchNearestPlace(voice2text);

                /*    if (String.valueOf(latitude)!=null&&String.valueOf(longitude)!=null) {
                        la.setText(String.valueOf(latitude));
                        lo.setText(String.valueOf(longitude));
                    }*/

                }
                else{
                    //This is what you need:
                    locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
                }
            }
            else
            {
                //prompt user to enable location....
                //.................
            }
        }

        @Override
        protected void onPause() {
            super.onPause();
            locationManager.removeUpdates(this);

        }

        @Override
        public void onLocationChanged(Location location) {
            //Hey, a non null location! Sweet!

            //remove location callback:
            locationManager.removeUpdates(this);

            //open the map:
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Toast.makeText(Prueba.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();

            la.setText(String.valueOf(latitude));
            lo.setText( String.valueOf(longitude));
         //   searchNearestPlace(voice2text);
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

        public void searchNearestPlace(String v2txt) {
            //.....
        }
    }





