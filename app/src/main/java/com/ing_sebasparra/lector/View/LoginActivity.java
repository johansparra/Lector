package com.ing_sebasparra.lector.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.ComprobarCamposLogin;
import com.ing_sebasparra.lector.Recursos.ConexionApp;
import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.CuadroDialogo;
import com.ing_sebasparra.lector.Recursos.SalirAplicacion;
import com.ing_sebasparra.lector.Temas.SeleccionTema;
import com.ing_sebasparra.lector.WebServices.ApiRest;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    //cargar el tema
    Toolbar toolbar;
    FrameLayout statusBar;

    // Definición de variables
    public EditText editTextEmail;
    public EditText editTextPassword;
    private Button buttonLogin1;

    private boolean loggedIn = false;

    public TextInputLayout inputLayoutCorreo, inputLayoutPassword;
    private View mProgressView;
    private Button registrarse;
    // private Activity activitypreguntar;

   // PermisosPreguntar permisosPreguntar = new PermisosPreguntar();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SeleccionTema selecTema = new SeleccionTema();
        selecTema.theme(this);
        setContentView(R.layout.activity_login);

        toolbarStatusBar();
        inicializeValues();

     //   permisosPreguntar.validapermisos(this);

    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
             //   Toast.makeText(this, "si pasa", Toast.LENGTH_SHORT).show();
            } else {
                permisosPreguntar.solicitarpermisomanual(this);
            }
        }

    }*/

    private void inicializeValues() {
        editTextEmail = findViewById(R.id.editTextEmailf);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin1 = findViewById(R.id.buttonLogin);
        inputLayoutCorreo = findViewById(R.id.layout_email);
        inputLayoutPassword = findViewById(R.id.layout_password);
        mProgressView = findViewById(R.id.login_progress);
        registrarse = findViewById(R.id.ir_registro);

        buttonLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "";
                String password = "";
                ConexionApp conect = new ConexionApp();
                if (!conect.conexionWifi(LoginActivity.this)) {
                    Config config = new Config();
                    CuadroDialogo cuadroDialogo=new CuadroDialogo();
                    cuadroDialogo.mostrar(LoginActivity.this,config.TITILO_AVISO_2,config.ALERT_NOT_WIFI_MOVILE ,R.drawable.ic_msg_wifi,null);
                    return;
                }
                email = editTextEmail.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();

                ComprobarCamposLogin comprobarCampos = new ComprobarCamposLogin();
                if(comprobarCampos.validar_campo(LoginActivity.this, email,password,editTextEmail,editTextPassword)){
                    servicesLogin(email, password);
                }

            }
        });
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityRegistro = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(activityRegistro);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Config config = new Config();
        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(config.LOGGEDIN_SHARED_PREF, false);
        if (loggedIn) {
            Intent intent = new Intent(LoginActivity.this, CuentaActivity.class);
            startActivity(intent);
        }
    }

    private void servicesLogin(String email, String password) {
        ApiRest apires = new ApiRest();
        apires.getLogin(email, password, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login_acerca, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_acerca) {
            Dialog dialog = new Dialog(LoginActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialogo_acerca);
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        SalirAplicacion salirdeaplicacion = new SalirAplicacion();
        salirdeaplicacion.now(this, LoginActivity.this, "Pulse otra vez para cerrar", 2500);

    }

    public void toolbarStatusBar() {
        statusBar = findViewById(R.id.statusBar);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.inicio_sesion));
    }


}
