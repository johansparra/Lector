package com.ing_sebasparra.lector.View;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.LimpiarMemoria;
import com.ing_sebasparra.lector.Recursos.NavegationLateral;
import com.ing_sebasparra.lector.Temas.SeleccionTema;

import java.util.Objects;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FrameLayout statusBar;
    //
    private LocationManager locManager;
    private Location loc;
    // public String lat="0", longi="0";
    public String lat, longi;
    //maps
    private GoogleMap mMap;
    boolean mapReady = false;

    private Button btnMap, btnSatellite, btnHybrid, estoyAqui, btncalle;

    // para marker boton
    // static final   CameraPosition LugarActual = CameraPosition.builder()
/*    CameraPosition LugarActual = CameraPosition.builder()
       //    .target(new LatLng(4.329052, -74.3634342))
            .target(new LatLng(Long.parseLong(lat), Long.parseLong(longi)))
            .zoom(17)
            .bearing(0)
            .tilt(45)
            .build();*/

    // para ahcer una linea o cuadrado
   //   LatLng renton = new LatLng(4.329052, -74.3634342);
//    LatLng renton = new LatLng(Long.parseLong(lat), Long.parseLong(longi));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SeleccionTema selecTema = new SeleccionTema();
        selecTema.theme(this);
        setContentView(R.layout.activity_maps);
        saberLatLong();
        toolbarStatusBar();

        drawerLayout = findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        if (navigationView != null) {

            NavegationLateral navegation = new NavegationLateral();
            navegation.navegationContent(navigationView, this, drawerLayout);
        }
        //MAPS
        estoyAqui = findViewById(R.id.btnestoyqui);
        estoyAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mapReady) {
                    Toast.makeText(MapsActivity.this, "latt"+lat, Toast.LENGTH_SHORT).show();

                    CameraPosition LugarActua = CameraPosition.builder()
                            //    .target(new LatLng(4.329052, -74.3634342))
                            .target(new LatLng(Double.parseDouble(lat), Double.parseDouble(longi)))
                            .zoom(17)
                            .bearing(0)
                            .tilt(45)
                            .build();

                    flyTo(LugarActua);
                }
            }
        });

        btnMap = findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });


        btnSatellite = findViewById(R.id.btnSatellite);
        btnSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        btnHybrid = findViewById(R.id.btnHybrid);
        btnHybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });

        btncalle = findViewById(R.id.btncalle);
        btncalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this, MapsCalleActivity.class);
                startActivity(i);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void saberLatLong() {
        ActivityCompat.requestPermissions(MapsActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          /*  tvLatitud.setText("No se han definido los permisos necesarios.");
            tvLongitud.setText("");
            tvAltura.setText("");
            tvPrecision.setText("");*/
            Toast.makeText(this, "Error active permiso", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //  String lat="0", longi="0";
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            assert locManager != null;
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lat = String.valueOf(loc.getLatitude());
            longi = String.valueOf(loc.getLongitude());
            // Toast.makeText(this, "lat" + lat, Toast.LENGTH_SHORT).show();
            //  Toast.makeText(this, "long" + longi, Toast.LENGTH_SHORT).show();
        }
    }

    //MAPS
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapReady = true;
        // Add a marker in Sydney and move the camera

        LatLng lugaractual = new LatLng(Double.parseDouble(lat), Double.parseDouble(longi));
        mMap.addMarker(new MarkerOptions().position(lugaractual).title("Esta Aqui"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lugaractual));

        //PARA HACER UN CIRCULO
        mMap.addCircle(new CircleOptions()
             //   .center(renton)
               .center(new LatLng(Double.parseDouble(lat), Double.parseDouble(longi)))
                .radius(100)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(64, 0, 255, 0)));


    }

    private void flyTo(CameraPosition target) {
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 10000, null);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MapsActivity.this, CuentaActivity.class);
        startActivity(intent);
    }

    // MENUS LATERALES
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_en_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_acerca) {
            Dialog dialog = new Dialog(MapsActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialogo_acerca);
            dialog.show();
            return true;
        }

        if (id == R.id.action_limpiar) {
            LimpiarMemoria limpiar = new LimpiarMemoria();
            limpiar.deleteCache(this);
        }

        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void toolbarStatusBar() {
        statusBar = findViewById(R.id.statusBar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Google Maps");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}

