package com.ing_sebasparra.lector.Maps;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.ing_sebasparra.lector.GpsActivity;
import com.ing_sebasparra.lector.LoginActivity;
import com.ing_sebasparra.lector.MainActivity;
import com.ing_sebasparra.lector.NfcActivity;
import com.ing_sebasparra.lector.OpcionesActivity;
import com.ing_sebasparra.lector.R;

import java.io.File;


public class MapsCalleActivity extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback {

    //CARGAR EL TEMA
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    Boolean homeButton = false, themeChanged;
    FrameLayout statusBar;
    //maps
    /*private GoogleMap mMap;
    *//*  private GoogleMap m_map;*//*
    boolean mapReady = false;

    GoogleMapOptions options = new GoogleMapOptions();
    private Button otro, btnMap, btnSatellite, btnHybrid, otro2, btncalle;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Seleccionar el tema guardado por el usuario (siempre antes de setContentView)
        theme();
        setContentView(R.layout.activity_maps1);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Siguiente del tema bar---
        toolbarStatusBar();
        themeChanged();
        // hasta aca va el tema

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);
        //MAPS
        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.calle);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(MapsCalleActivity.this);

    }

    //MAPS
    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
          panorama.setPosition(new LatLng(4.329052,-74.3634342));
        StreetViewPanoramaCamera camera = new StreetViewPanoramaCamera.Builder()
                .bearing(180)
                .build();
        panorama.animateTo(camera,10000);

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
            Dialog dialog = new Dialog(MapsCalleActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialogo_acerca);
            dialog.show();
            return true;
        }
        if (id == R.id.action_configuracion) {
            Intent i = new Intent(this, OpcionesActivity.class);
            startActivity(i);
        }
        if (id == R.id.action_limpiar) {
            deleteCache(this);
            // Toast.makeText(LoginActivity.this, "Memoria Limpiada " , Toast.LENGTH_SHORT).show();
        }


        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_inicio:
                                menuItem.setChecked(true);
                                Toast.makeText(MapsCalleActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent = new Intent(MapsCalleActivity.this, MainActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.item_navigation_drawer_perfil:
                                menuItem.setChecked(true);
                                Toast.makeText(MapsCalleActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent1 = new Intent(MapsCalleActivity.this, LoginActivity.class);
                                startActivity(intent1);
                                return true;
                            case R.id.item_navigation_drawer_gps:
                                menuItem.setChecked(true);
                                Toast.makeText(MapsCalleActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent2 = new Intent(MapsCalleActivity.this, GpsActivity.class);
                                startActivity(intent2);
                                return true;
                            case R.id.item_navigation_drawer_nfc:
                                menuItem.setChecked(true);
                                Toast.makeText(MapsCalleActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent3 = new Intent(MapsCalleActivity.this, NfcActivity.class);
                                startActivity(intent3);
                                return true;
                            case R.id.item_navigation_drawer_configuracion:
                                menuItem.setChecked(true);
                                Toast.makeText(MapsCalleActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent4 = new Intent(MapsCalleActivity.this, OpcionesActivity.class);
                                startActivity(intent4);
                                return true;
                            case R.id.item_navigation_drawer_maps:
                                menuItem.setChecked(true);
                                Toast.makeText(MapsCalleActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent6 = new Intent(MapsCalleActivity.this, MapsActivity.class);
                                startActivity(intent6);
                                return true;
                        }
                        return true;
                    }
                });
    }
    // HASTA ACA MENUS LATERALES

    // BOTON ATRAS CELULAR
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MapsCalleActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // TEMA NO CAMBIAR
    public void theme() {
        sharedPreferences = getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        int theme = sharedPreferences.getInt("THEME", 0);
        settingTheme(theme);
    }

    private void themeChanged() {
        themeChanged = sharedPreferences.getBoolean("THEMECHANGED", false);
        homeButton = true;
    }

    public void toolbarStatusBar() {
        statusBar = (FrameLayout) findViewById(R.id.statusBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Titulo que se visualiza en en action bar
        getSupportActionBar().setTitle("Google Maps Calle");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void settingTheme(int theme) {
        switch (theme) {
            case 1:
                setTheme(R.style.AppTheme);
                break;
            case 2:
                setTheme(R.style.AppTheme2);
                break;
            case 3:
                setTheme(R.style.AppTheme3);
                break;
            case 4:
                setTheme(R.style.AppTheme4);
                break;
            case 5:
                setTheme(R.style.AppTheme5);
                break;
            case 6:
                setTheme(R.style.AppTheme6);
                break;
            case 7:
                setTheme(R.style.AppTheme7);
                break;
            case 8:
                setTheme(R.style.AppTheme8);
                break;
            case 9:
                setTheme(R.style.AppTheme9);
                break;
            case 10:
                setTheme(R.style.AppTheme10);
                break;
            default:
                setTheme(R.style.AppTheme);
                break;
        }
    }
    // HASTA ACA CARGAR EL TEMA

    // LIMPIAR MEMORIA
    public void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            texto_titulo();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {


            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {

                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {

                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile())
            return dir.delete();
        else {
            return false;
        }
    }

    public void texto_titulo() {
        Toast toast3 = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.msg_limpiar,
                (ViewGroup) findViewById(R.id.lytLayout));
        TextView txtMsg = (TextView) layout.findViewById(R.id.txtMensaje);
        txtMsg.setText("Memoria Limpiada");
        toast3.setDuration(Toast.LENGTH_SHORT);
        toast3.setView(layout);
        toast3.show();
    }
    // HASTA ACA LIMPIAR

}

