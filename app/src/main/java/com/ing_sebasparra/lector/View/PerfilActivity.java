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
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.CerrarSesion;
import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.LimpiarMemoria;
import com.ing_sebasparra.lector.Recursos.NavegationLateral;
import com.ing_sebasparra.lector.Recursos.SalirAplicacion;
import com.ing_sebasparra.lector.Temas.SeleccionTema;
import com.ing_sebasparra.lector.WebServices.ApiRest;
import com.ing_sebasparra.lector.WebServices.UrlServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class PerfilActivity extends AppCompatActivity {

    //CARGAR EL TEMA
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    FrameLayout statusBar;

    //VARIABLES
    private TextView emailTV, nombreTV, apellidoTV, cargoTV, fotoTV, cedulaTV, nmostrar, saldoTV;
    private String email1, nombre1, apellido1, cargo1, foto1, cedula1, idconsulta, saldo1;

    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;
    Config config = new Config();
    ApiRest apires = new ApiRest();

    private final UrlServices url = new UrlServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SeleccionTema selecTema = new SeleccionTema();
        selecTema.theme(this);
        setContentView(R.layout.activity_perfil);

        toolbarStatusBar();
       // SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        cargarVariables();


       consultaSaldo(idconsulta,this);


        //consulta();
      /*  ConsultaDTO consultaDTO = new ConsultaDTO();
        saldo1 = consultaDTO.getSaldo();
        saldoTV.setText("$"+saldo1);*/

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (navigationView != null) {

            NavegationLateral navegation = new NavegationLateral();
            navegation.navegationContent(navigationView, this, drawerLayout);
        }

    }



    private void consulta() {
        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();


        // RequestQueue respuesta = Volley.newRequestQueue(this);
        String urlConsulta = idconsulta;
        try {
            JsonObjectRequest obejto = new JsonObjectRequest(
                    Request.Method.GET, url.CONSULTA_SALDO_URL + urlConsulta, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject respuesta) {

                            String cuenta = "";
                            String saldo = "";
                            String fecha = "";
                            try {

                                JSONObject request = respuesta.getJSONObject("cuenta");
                                cuenta = request.getString("ultimaRecarga");
                                saldo = request.getString("saldo");
                                fecha = request.getString("fechaRecarga");
                                saldoTV.setText("$" + saldo);
                            } catch (JSONException e) {
                                Toast.makeText(PerfilActivity.this, "Error Consulta Json: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );
            mRequestQueue.add(obejto);


        } catch (Exception error) {
            Toast.makeText(PerfilActivity.this, "Error Consulta: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

  /*  @Override
    protected void onResume() {
        super.onResume();
        consulta();

    }*/

    private void consultaSaldo(String n_identificacion, Context context) {
        apires.consultarSaldo(n_identificacion, context);
        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_CONSULTA, Context.MODE_PRIVATE);
        saldo1 = sharedPreferences.getString(config.SALDO_SHARED_PREF, "No Disponible");
        saldoTV.setText("$"+saldo1);

      /*  SharedPreferences preferences = context.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(config.SHARED_PREF_CONSULTA, false);
        editor.putString(config.SALDO_SHARED_PREF, "");
        editor.commit();*/

    }

    private void cargarVariables() {
        nombreTV = (TextView) findViewById(R.id.nombreview);
        cedulaTV = (TextView) findViewById(R.id.cedulaview);
        saldoTV = (TextView) findViewById(R.id.saldoview);

        // SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        nombre1 = sharedPreferences.getString(config.NOMBRE_SHARED_PREF, "No Disponible");
        nombreTV.setText(nombre1);
        cedula1 = sharedPreferences.getString(config.N_IDENTIFICACION_SHARED_PREF, "No Disponible");
        cedulaTV.setText(cedula1);
        idconsulta = sharedPreferences.getString(config.ID_USUARIO_SHARED_PREF, "No Disponible");

        // saldotiemporeal();
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



