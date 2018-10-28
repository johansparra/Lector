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
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.CerrarSesion;
import com.ing_sebasparra.lector.Recursos.ConexionApp;
import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.CuadroDialogo;
import com.ing_sebasparra.lector.Recursos.LimpiarMemoria;
import com.ing_sebasparra.lector.Recursos.NavegationLateral;
import com.ing_sebasparra.lector.Recursos.SalirAplicacion;
import com.ing_sebasparra.lector.Temas.SeleccionTema;
import com.ing_sebasparra.lector.WebServices.ApiRest;

import java.util.Objects;

public class CuentaActivity extends AppCompatActivity {

    //CARGAR EL TEMA
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    FrameLayout statusBar;

    //VARIABLES
    public TextView nombreTV, cedulaTV, saldoTV,recargaTV,tituloRecargaTV, fecharecTV,tituloFechaTV;
    private String nombre1, cedula1, idconsulta;
    Config config = new Config();
    ApiRest apires = new ApiRest();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SeleccionTema selecTema = new SeleccionTema();
        selecTema.theme(this);
        setContentView(R.layout.activity_cuenta);

        toolbarStatusBar();
        cargarVariables();

       consultaSaldo(idconsulta,this);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (navigationView != null) {

            NavegationLateral navegation = new NavegationLateral();
            navegation.navegationContent(navigationView, this, drawerLayout);
        }

    }


    private void consultaSaldo(String n_identificacion, Context context) {
        ConexionApp conect = new ConexionApp();
        if (!conect.conexionWifi(CuentaActivity.this)) {
            Config config = new Config();
            CuadroDialogo cuadroDialogo=new CuadroDialogo();
            cuadroDialogo.mostrar(CuentaActivity.this,config.TITILO_AVISO_2,config.ALERT_NOT_WIFI_MOVILE ,R.drawable.ic_msg_wifi,null);
            return;
        }
        apires.consultarSaldo(n_identificacion, context, this);
    }

    private void cargarVariables() {
        nombreTV = (TextView) findViewById(R.id.nombreview);
        cedulaTV = (TextView) findViewById(R.id.cedulaview);
        saldoTV = (TextView) findViewById(R.id.saldoview);
        saldoTV.setVisibility(View.GONE);
        recargaTV = (TextView) findViewById(R.id.recargaPerview);
        recargaTV.setVisibility(View.GONE);
        tituloRecargaTV = (TextView) findViewById(R.id.titulorecaPerview);
        tituloRecargaTV.setVisibility(View.GONE);
        fecharecTV = (TextView) findViewById(R.id.fechaPerview);
        fecharecTV.setVisibility(View.GONE);
        tituloFechaTV = (TextView) findViewById(R.id.titulofechaPerview);
        tituloFechaTV.setVisibility(View.GONE);

        // SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        nombre1 = sharedPreferences.getString(config.NOMBRE_SHARED_PREF, "No Disponible");
        nombreTV.setText(nombre1);
        cedula1 = sharedPreferences.getString(config.N_IDENTIFICACION_SHARED_PREF, "No Disponible");
        cedulaTV.setText(cedula1);
        idconsulta = sharedPreferences.getString(config.ID_USUARIO_SHARED_PREF, "No Disponible");

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
            Dialog dialog = new Dialog(CuentaActivity.this);
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
        salirdeaplicacion.now(this, CuentaActivity.this, "Pulse otra vez para cerrar", 2500);

    }


    public void toolbarStatusBar() {
        statusBar = (FrameLayout) findViewById(R.id.statusBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.cuenta));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}



