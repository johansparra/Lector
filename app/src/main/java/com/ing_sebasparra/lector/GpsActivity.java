package com.ing_sebasparra.lector;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ing_sebasparra.lector.Recursos.LimpiarMemoria;
import com.ing_sebasparra.lector.Recursos.NavegationLateral;
import com.ing_sebasparra.lector.Temas.SeleccionTema;
import com.ing_sebasparra.lector.View.PerfilActivity;

public class GpsActivity extends AppCompatActivity {

    //CARGAR EL TEMA
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    Boolean homeButton = false, themeChanged;
    FrameLayout statusBar;
    //GPS
    TextView mensaje1;
    String coorde;
    TextView coordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SeleccionTema selecTema = new SeleccionTema();
        selecTema.theme(this);
        setContentView(R.layout.activity_gps);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarStatusBar();

        drawerLayout = findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        if (navigationView != null) {

            NavegationLateral navegation =new NavegationLateral();
            navegation.navegationContent(navigationView,this,drawerLayout);
        }

        //GPS
        mensaje1 = findViewById(R.id.coordenadaid);
        coordenadas = findViewById(R.id.coordenadas_mostrar);
        // permisos para el gps
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }
    }

    // GPS
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setGpsActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Local);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    // Aqui empieza la Clase Localizacion
    public class Localizacion implements LocationListener {
        GpsActivity gpsActivity;

        public GpsActivity getGpsActivity() {
            return gpsActivity;
        }

        public void setGpsActivity(GpsActivity gpsActivity) {
            this.gpsActivity = gpsActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            //String Text = "Lat = " + loc.getLatitude() + "Long = " + loc.getLongitude();
            //mensaje1.setText(Text);
            coorde = "Lat = " + loc.getLatitude() + "  Long = " + loc.getLongitude();
            coordenadas.setText(coorde); // para visualizar las coordenadas en tiempo real

        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado

            mensaje1.setText("GPS Desactivado");
            mensaje1.setVisibility(View.VISIBLE);
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            //   mensaje1.setText("GPS Activado");
            mensaje1.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "28/5000\n" +
                            "Proveedor de ubicación DISPONIBLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "Proveedor de ubicación Fuera de servicio");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "Proveedor de ubicación TEMPORALMENTE NO DISPONIBLE");
                    break;
            }
        }
    }

    // HASTA ACA EL GPS


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
            Dialog dialog = new Dialog(GpsActivity.this);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GpsActivity.this, PerfilActivity.class);
        startActivity(intent);
    }


    public void toolbarStatusBar() {
        statusBar = findViewById(R.id.statusBar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Titulo que se visualiza en en action bar
        getSupportActionBar().setTitle("GPS");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}

