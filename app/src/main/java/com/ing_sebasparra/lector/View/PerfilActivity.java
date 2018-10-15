package com.ing_sebasparra.lector.View;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.CerrarSesion;
import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.LimpiarMemoria;
import com.ing_sebasparra.lector.Recursos.NavegationLateral;
import com.ing_sebasparra.lector.Recursos.SalirAplicacion;
import com.ing_sebasparra.lector.Temas.SeleccionTema;

import java.util.Objects;

public class PerfilActivity extends AppCompatActivity {

    //CARGAR EL TEMA
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    FrameLayout statusBar;

    //VARIABLES
    private TextView emailTV, nombreTV, apellidoTV, cargoTV, fotoTV, cedulaTV, nmostrar;
    private String email1, nombre1, apellido1, cargo1, foto1, cedula1;

    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SeleccionTema selecTema = new SeleccionTema();
        selecTema.theme(this);
        setContentView(R.layout.activity_perfil);

        toolbarStatusBar();
        cargarVariables();


        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (navigationView != null) {

            NavegationLateral navegation = new NavegationLateral();
            navegation.navegationContent(navigationView, this, drawerLayout);
        }
        }

    private void cargarVariables() {
        emailTV = (TextView) findViewById(R.id.emailview);
   /*     nombreTV = (TextView) findViewById(R.id.nombreview);
        apellidoTV = (TextView) findViewById(R.id.apellidosview);*/
        cedulaTV = (TextView) findViewById(R.id.cedulaview);
        Config config = new Config();
        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email1 = sharedPreferences.getString(config.EMAIL_SHARED_PREF, "No Disponible");
        emailTV.setText(email1);
        cedula1 = sharedPreferences.getString(config.CEDULA_SHARED_PRF, "No Disponible");
        cedulaTV.setText(cedula1);



      /*  nombre1 = sharedPreferences.getString(Config.NOMBRE_SHARED_PREF, "No Disponible");
        nombreTV.setText(nombre1);
        apellido1 = sharedPreferences.getString(Config.APELLIDOS_SHARED_PREF, "No Disponible");
        apellidoTV.setText(apellido1);*/

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
            CerrarSesion cerrar = new CerrarSesion();
            cerrar.logout(this);
        }

        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        SalirAplicacion salirdeaplicacion = new SalirAplicacion();
        salirdeaplicacion.now(this, PerfilActivity.this, "Pulse otra vez para cerrar", 2500);

    }


    public void toolbarStatusBar() {
        statusBar = (FrameLayout) findViewById(R.id.statusBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.perfil));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}



