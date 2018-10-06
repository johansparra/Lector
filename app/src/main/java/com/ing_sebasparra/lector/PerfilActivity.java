package com.ing_sebasparra.lector;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.LimpiarMemoria;
import com.ing_sebasparra.lector.View.LoginActivity;
import com.ing_sebasparra.lector.View.OpcionesActivity;
import com.ing_sebasparra.lector.View.PagoNFC;

/*public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {*/
public class PerfilActivity extends AppCompatActivity {

    //CARGAR EL TEMA
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    Boolean homeButton = false, themeChanged;
    FrameLayout statusBar;

    //VARIABLES
    private TextView emailTV, nombreTV, apellidoTV, cargoTV, fotoTV, cedulaTV, nmostrar;
    private String email1, nombre1, apellido1, cargo1, foto1, cedula1;
    // para la iamgen
    ImageView ivImageFromUrl;
    // el scan
    Button scanbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Seleccionar el tema guardado por el usuario (siempre antes de setContentView)
        theme();
        setContentView(R.layout.activity_perfil);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Siguiente del tema bar---
        toolbarStatusBar();
        themeChanged();
        // hasta aca va el tema

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

/*        DrawerLayout nfc=findViewById(R.id.item_navigation_drawer_nfc);
        //nfc.setVisibility(View.INVISIBLE);
      //  nfc.setEnabled(false);*/

        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);

        //VARIABLES
        emailTV = (TextView) findViewById(R.id.emailview);
        nombreTV = (TextView) findViewById(R.id.nombreview);
        apellidoTV = (TextView) findViewById(R.id.apellidosview);
        cedulaTV = (TextView) findViewById(R.id.cedulaview);
        Config config=new Config();
        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email1 = sharedPreferences.getString(config.EMAIL_SHARED_PREF, "No Disponible");
        emailTV.setText(email1);
        cedula1 = sharedPreferences.getString(config.CEDULA_SHARED_PRF, "No Disponible");
        cedulaTV.setText(cedula1);
      /*  nombre1 = sharedPreferences.getString(Config.NOMBRE_SHARED_PREF, "No Disponible");
        nombreTV.setText(nombre1);
        apellido1 = sharedPreferences.getString(Config.APELLIDOS_SHARED_PREF, "No Disponible");
        apellidoTV.setText(apellido1);
       */

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
            Dialog dialog = new Dialog(PerfilActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialogo_acerca);
            dialog.show();
            return true;
        }
        if (id == R.id.action_limpiar) {

            LimpiarMemoria limpiar = new LimpiarMemoria();
            limpiar.deleteCache(this);

        }
        if (id == R.id.action_logout) {
            logout();
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
                                Toast.makeText(PerfilActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.item_navigation_drawer_perfil:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_gps:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_nfc:

                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent3 = new Intent(PerfilActivity.this, PagoNFC.class);
                                startActivity(intent3);
                                return true;

                            case R.id.item_navigation_drawer_configuracion:
                                menuItem.setChecked(true);
                                Toast.makeText(PerfilActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent1 = new Intent(PerfilActivity.this, OpcionesActivity.class);
                                startActivity(intent1);
                                return true;
                            case R.id.item_navigation_drawer_maps:
                                menuItem.setChecked(true);
                                Toast.makeText(PerfilActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
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
        Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
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
        getSupportActionBar().setTitle("Perfil");
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


    private void logout() {
        // Creación de un cuadro de diálogo de alerta para confirmar el cierre de sesión
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(" ¿Seguro que quieres cerrar la sesión? ");
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        // Salir de las preferencias compartidas
                        Config config=new Config();
                        SharedPreferences preferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(config.LOGGEDIN_SHARED_PREF, false);
                        editor.putString(config.EMAIL_SHARED_PREF, "");
                        editor.commit();
                        Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
        alertDialogBuilder.setNegativeButton("No ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        // Mostrar el cuadro de diálogo de alerta
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}



