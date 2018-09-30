package com.ing_sebasparra.lector;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ing_sebasparra.lector.Maps.MapsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //cargar el tema
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    Boolean homeButton = false, themeChanged;
    FrameLayout statusBar;

    // Definición de variables
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin1;

    // variable booleana para comprobar que el usuario ha iniciado sesión o no
    // inicialmente es falso
    private boolean loggedIn = false;
    // mirar si carga los datos
    String PASSWORDT1 = null, EMAILT1 = null, NOMBRET1 = null, APELLIDOT1 = null, NIVELT1 = null, CEDULAT1 = null, CREDITOST1 =null;
    //hasta aca

    // saber si hay conexion a ineternet
    boolean tipoConexion1 = false;
    boolean tipoConexion2 = false;
    //LOGIN CAMPOS VACIOS
    private TextInputLayout inputLayoutCorreo, inputLayoutPassword;
    private boolean camposVacios = false;

    private View mProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Seleccionar el tema guardado por el usuario (siempre antes de setContentView)
        theme();
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Siguiente del tema bar---
        toolbarStatusBar();
        themeChanged();
        // hasta aca va el tema

        drawerLayout = findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);

        //DECLARACION

        editTextEmail = findViewById(R.id.editTextEmailf);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin1 = findViewById(R.id.buttonLogin);

        // Adición detector de clics
        buttonLogin1.setOnClickListener(this);
        //LOOGIN
        inputLayoutCorreo = findViewById(R.id.layout_email);
        inputLayoutPassword = findViewById(R.id.layout_password);
        mProgressView = findViewById(R.id.login_progress);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_en_perfil, menu);
        return true;
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
                                Toast.makeText(LoginActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.item_navigation_drawer_perfil:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_gps:
                                menuItem.setChecked(true);
                                Toast.makeText(LoginActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent2 = new Intent(LoginActivity.this, GpsActivity.class);
                                startActivity(intent2);
                                return true;
                            case R.id.item_navigation_drawer_nfc:
                                menuItem.setChecked(true);
                                Toast.makeText(LoginActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent3 = new Intent(LoginActivity.this, Beam.class);
                                startActivity(intent3);
                                return true;
                            case R.id.item_navigation_drawer_configuracion:
                                menuItem.setChecked(true);
                                Toast.makeText(LoginActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent4 = new Intent(LoginActivity.this, OpcionesActivity.class);
                                startActivity(intent4);
                                return true;
                            case R.id.item_navigation_drawer_maps:
                                menuItem.setChecked(true);
                                Toast.makeText(LoginActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent6 = new Intent(LoginActivity.this, MapsActivity.class);
                                startActivity(intent6);
                                return true;
                        }
                        return true;
                    }
                });
    }

    // boton atras del celular
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
    // TEMA NO CAMBIAR
    public void theme() {
        sharedPreferences = getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        int theme = sharedPreferences.getInt("THEME", 0);
        settingTheme(theme);
    }
    private void themeChanged() {
        themeChanged = sharedPreferences.getBoolean("THEMECHANGED",false);
        homeButton = true;
    }
    public void toolbarStatusBar() {
        statusBar = findViewById(R.id.statusBar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Titulo que se visualiza en en action bar
        getSupportActionBar().setTitle("Inicio de Sesión");
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
    public void texto_titulo(){
        Toast toast3 = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.msg_limpiar,
                (ViewGroup) findViewById(R.id.lytLayout));
        TextView txtMsg = layout.findViewById(R.id.txtMensaje);
        txtMsg.setText("Memoria Limpiada");
        toast3.setDuration(Toast.LENGTH_SHORT);
        toast3.setView(layout);
        toast3.show();
    }
    // HASTA ACA LIMPIAR


    //XML

    // Login
    @Override
    protected void onResume() {
        super.onResume();
        // En el valor de recuperación de búsqueda de preferencias compartidas
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // Recuperación del valor booleano de las preferencias compartidas
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        // Si llegamos a obtener el valor verdadero
        if (loggedIn) {
            // Iniciaremos la Activity del Perfil
            Intent intent = new Intent(LoginActivity.this, PerfilActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onClick(View v) {
        // mirar primero si hay conexion a internet para poder seguir

        ConnectivityManager cm;
        NetworkInfo ni;
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        if (ni != null) {
            ConnectivityManager connManager1 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            ConnectivityManager connManager2 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobile = connManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mWifi.isConnected()) {
                tipoConexion1 = true;
            }
            if (mMobile.isConnected()) {
                tipoConexion2 = true;
            }
          /*  if (tipoConexion1 == true || tipoConexion2 == true) {*/
                if (tipoConexion1 || tipoConexion2 ) {
                //Llamando a la funcion login
                validar_campo();
                login();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Error de conexión No Hay internet! ", Toast.LENGTH_SHORT).show();
        }
        // HASTA ACA SI HAY CONEXION INTERNET

    }

    private void login() {
        // Obtención de valores de textos de edición
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        // Creacion a de peticion a un string
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Si tenemos éxito desde el servidor
                        if (response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {
                            // Creación de una preferencia compartida
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            // Creación de un editor para almacenar valores en preferencias compartidas
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            // Añadiendo valores al editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.EMAIL_SHARED_PREF, email);
                            // Para guardar los valores en el editor
                            editor.commit();

                            CargarDatos b = new CargarDatos();
                            b.execute(email, password);
                            showProgress(true);
                        } else {
                            // Si la respuesta del servidor no tiene éxito
                            // Visualización de un mensaje de error
                            Toast.makeText(LoginActivity.this, " Usuario o password Incorrectos!", Toast.LENGTH_LONG).show();
                            showProgress(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Puedes manejar el error aquí si quieres
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Añadiendo parámetros a la solicitud
                params.put(Config.KEY_EMAIL, email);
                params.put(Config.KEY_PASSWORD, password);
                //Retornando los parametros
                return params;
            }
        };
        //Añandiendo el string de la solicitud en cola
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    // hasta ca va el login

    // CARGAR DATOS DEL USUARIO QUE INICIO SESION
    class CargarDatos extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            String data = "";
            int tmp;

            try {
                URL url = new URL("https://johansparra1.000webhostapp.com/Registro/logmenu/mirar_datos/login.php");
                String urlParams = "email=" + email + "&password=" + password;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err = null;
            try {
                JSONObject root = new JSONObject(s);
                JSONObject user_data = root.getJSONObject("user_data");
                NOMBRET1 = user_data.getString("name");
                APELLIDOT1 = user_data.getString("apellidos");
                EMAILT1 = user_data.getString("email");
                PASSWORDT1 = user_data.getString("password");
                NIVELT1 = user_data.getString("nivel");
                CEDULAT1 = user_data.getString("cedula");
                CREDITOST1 = user_data.getString("creditos");

            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }
            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
            editor.putString(Config.NOMBRE_SHARED_PREF, NOMBRET1);
            editor.putString(Config.APELLIDOS_SHARED_PREF, APELLIDOT1);
            editor.putString(Config.EMAIL_SHARED_PREF, EMAILT1);
            editor.putString(Config.NIVEL_SHARED_PREF, NIVELT1);
            editor.putString(Config.MATRICULA_SHARED_PREF, CEDULAT1);
            editor.putString(Config.CREDITOS_SHARED_PREF, CREDITOST1);
            editor.putString(Config.ERR_SHARED_PREF, err);// si sale error  pero nunca va  salir por el condicional
            editor.commit();
            //Iniciando el  activity
            Intent intent = new Intent(LoginActivity.this, PerfilActivity.class);
            startActivity(intent);
        }
    }

    // BARRA DE PROGRESO
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        }
    }


    //MIRAR SI ESTA VACIO LOS EDIT TEXT
    public void validar_campo() {
        String mailError = null;
        if (TextUtils.isEmpty(editTextEmail.getText())) {
            mailError = getResources().getString(R.string.error_campo);
        }
        toggleTextInputLayoutError(inputLayoutCorreo,mailError);

        String passError = null;
        if (TextUtils.isEmpty(editTextPassword.getText())) {
            passError = getString(R.string.error_campo);
        }
        toggleTextInputLayoutError(inputLayoutPassword, passError);
    }

    private  void toggleTextInputLayoutError(@NonNull TextInputLayout textInputLayout,
                                             String msg) {
        textInputLayout.setError(msg);
        if (msg == null) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setErrorEnabled(true);
        }
    }

}

//login de aca