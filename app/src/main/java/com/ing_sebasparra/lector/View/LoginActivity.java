package com.ing_sebasparra.lector.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.ConexionApp;
import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.ValidacionDatos;
import com.ing_sebasparra.lector.Temas.SeleccionTema;
import com.ing_sebasparra.lector.WebServices.ApiRest;

public class LoginActivity extends AppCompatActivity {

    //cargar el tema
    Toolbar toolbar;
    FrameLayout statusBar;

    // Definición de variables
    public EditText editTextEmail;
    public EditText editTextPassword;
    private Button buttonLogin1;

    private boolean loggedIn = false;
    // mirar si carga los datos
    String PASSWORDT1 = null, EMAILT1 = null, NOMBRET1 = null, APELLIDOT1 = null, NIVELT1 = null, CEDULAT1 = null, CREDITOST1 = null;
    //hasta aca

    public TextInputLayout inputLayoutCorreo, inputLayoutPassword;
    private View mProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Seleccionar el tema guardado por el usuario (siempre antes de setContentView)
        SeleccionTema selecTema = new SeleccionTema();
        selecTema.theme(this);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Siguiente del tema bar---
        toolbarStatusBar();
        inicializeValues();


    }

    private void inicializeValues() {
        editTextEmail = findViewById(R.id.editTextEmailf);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin1 = findViewById(R.id.buttonLogin);
        inputLayoutCorreo = findViewById(R.id.layout_email);
        inputLayoutPassword = findViewById(R.id.layout_password);
        mProgressView = findViewById(R.id.login_progress);

        buttonLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "";
                String password = "";
                ConexionApp conect = new ConexionApp();
                if (!conect.conexionWifi(LoginActivity.this)) {
                    Toast.makeText(LoginActivity.this, "Error de conexión No Hay internet! ", Toast.LENGTH_SHORT).show();
                    return;
                }
                email = editTextEmail.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                if (validar_campo(email, password)) {
                    showProgress(true);
                    servicesLogin(email, password);
                    showProgress(false);
                }
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
            Intent intent = new Intent(LoginActivity.this, PerfilActivity.class);
            startActivity(intent);
        }
    }

    private void servicesLogin(String email, String password) {
        ApiRest apires = new ApiRest();
        apires.getLogin(email, password, this);
    }

/*    private void login() {
        // Obtención de valores de textos de edición
        final String email = editTextEmail.getText().toString().trim();
    //    final String password = editTextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.LOGIN_URL+email,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginActivity.this, "salida: "+ response, Toast.LENGTH_SHORT).show();
                        if (!response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.EMAIL_SHARED_PREF, email);
                            editor.commit();

                         //   CargarDatos b = new CargarDatos();
                           // b.execute(email, password);
                        //    b.execute(email);
                            showProgress(true);
                        } else {
                            Toast.makeText(LoginActivity.this, " Usuario o password Incorrectos!", Toast.LENGTH_LONG).show();
                            showProgress(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Añadiendo parámetros a la solicitud
                params.put(Config.KEY_EMAIL, email);
             //   params.put(Config.KEY_PASSWORD, password);
                //Retornando los parametros
                return params;
            }
        };
        //Añandiendo el string de la solicitud en cola
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/
    // hasta ca va el login

    // CARGAR DATOS DEL USUARIO QUE INICIO SESION
/*    class CargarDatos extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
           // String password = params[1];
            String data = "";
            int tmp;

            try {
                URL url = new URL( "http://api-transmilenio.us-west-1.elasticbeanstalk.com/API_TRANSMI/V0/User/Cuenta/ValorPasaje/");
             //   String urlParams = "email=" + email + "&password=" + password;
                String urlParams = "email=" + email ;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
               // int status = Integer.valueOf(httpURLConnection.getResponseMessage());


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
               // JSONObject user_data = root.getJSONObject("estado");
               // JSONObject user_data = root.getJSONObject("user_data");
                NOMBRET1 = root.getString("estado");
              *//*  NOMBRET1 = user_data.getString("name");
                APELLIDOT1 = user_data.getString("apellidos");
                EMAILT1 = user_data.getString("email");
                PASSWORDT1 = user_data.getString("password");
                NIVELT1 = user_data.getString("nivel");
                CEDULAT1 = user_data.getString("cedula");
                CREDITOST1 = user_data.getString("creditos");*//*

            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }
            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
            editor.putString(Config.NOMBRE_SHARED_PREF, NOMBRET1);
*//*            editor.putString(Config.APELLIDOS_SHARED_PREF, APELLIDOT1);
            editor.putString(Config.EMAIL_SHARED_PREF, EMAILT1);
            editor.putString(Config.NIVEL_SHARED_PREF, NIVELT1);
            editor.putString(Config.MATRICULA_SHARED_PREF, CEDULAT1);
            editor.putString(Config.CREDITOS_SHARED_PREF, CREDITOST1);
            editor.putString(Config.ERR_SHARED_PREF, err);// si sale error  pero nunca va  salir por el condicional*//*
            editor.commit();
            //Iniciando el  activity
            Intent intent = new Intent(LoginActivity.this, PerfilActivity.class);
            startActivity(intent);
        }
    }*/

    // BARRA DE PROGRESO
    private void showProgress(final boolean show) {
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
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        }
    }


    //MIRAR SI ESTA VACIO LOS EDIT TEXT
    public boolean validar_campo(String email, String password) {
        String mailError = null;
        Boolean mensaje;
        Boolean validaCampos = true;
        String passError = null;
        String ErrorEmail = null;

        if (TextUtils.isEmpty(email)) {
            mailError = getResources().getString(R.string.error_campo);
            validaCampos = false;
        }
        toggleTextInputLayoutError(inputLayoutCorreo, mailError);

        if (TextUtils.isEmpty(password)) {
            passError = getString(R.string.error_campo);
            validaCampos = false;
        }
        toggleTextInputLayoutError(inputLayoutPassword, passError);

        if (validaCampos) {
            ValidacionDatos validar = new ValidacionDatos();
            mensaje = validar.validaremail(email);
            if (mensaje) {
                ErrorEmail = getString(R.string.error_invalid_email_di);
                validaCampos = false;
            }
            toggleTextInputLayoutError(inputLayoutCorreo, ErrorEmail);
        }
        return validaCampos;

    }


    private void toggleTextInputLayoutError(@NonNull TextInputLayout textInputLayout,
                                            String msg) {
        textInputLayout.setError(msg);
        if (msg == null) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setErrorEnabled(true);
        }
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
        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void toolbarStatusBar() {
        statusBar = findViewById(R.id.statusBar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle("Inicio de Sesión");
        getSupportActionBar().setTitle(getResources().getString(R.string.inicio_sesion));

    }




}
