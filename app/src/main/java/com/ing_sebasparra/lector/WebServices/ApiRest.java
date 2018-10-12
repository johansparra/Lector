package com.ing_sebasparra.lector.WebServices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.UrlServices;
import com.ing_sebasparra.lector.View.PerfilActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ApiRest {
    private UrlServices url = new UrlServices();


    public void getLogin(String email, String password, final Context context) {
//email/PRUEBA@GMAIL.COM/password/123456789
        String urllogin = "email/" + email + "/password/" + password;
        int status = 0;

        try {
            RequestQueue respuesta = Volley.newRequestQueue(context);
            JsonObjectRequest obejto = new JsonObjectRequest(
                    Request.Method.GET, url.LOGIN_URL_1 + urllogin, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject respuesta) {
                            boolean salida = true;
                            String emailreq = "";
                            String passreq = "";
                            String celudareq = "";
                            try {

                                JSONObject request = respuesta.getJSONObject("login");
                                emailreq = request.getString("email");
                                passreq = request.getString("password");
                                celudareq = request.getString("cedula");
                                //Toast.makeText(context, "email: " + request, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Log.e("Error getLogin", e.getMessage());
                            }

                            Log.e("bien", respuesta.toString());
                            //  Toast.makeText(context, "salida: " + respuesta.toString(), Toast.LENGTH_SHORT).show();

                            Config config = new Config();

                            SharedPreferences sharedPreferences = context.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(config.EMAIL_SHARED_PREF, emailreq);
                            editor.putString(config.CEDULA_SHARED_PRF, celudareq);
                            editor.apply();


                            Intent intent = new Intent(context, PerfilActivity.class);
                            context.startActivity(intent);
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   Log.e("ocurrio un error", error.toString());
                    Toast.makeText(context, "Correo o password incorrectos", Toast.LENGTH_SHORT).show();

                }
            }
            );
            respuesta.add(obejto);


        } catch (Exception error) {
            Log.e("ocurrio un error", error.getMessage().toString());

        }


    }

    public void postRegistro(String nombres, String apellidos,
                             String telefono, String fecha, String email,
                             String password, int tipo_identificacion, String identificacion,
                             String genero, final Context context) {

    }


    public void prueba(Context context) {
        RequestQueue respuesta = Volley.newRequestQueue(context);
        String url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        // Log.d("Error.Response", response);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Alif");
                params.put("domain", "http://itsalif.info");

                return params;
            }
        };
        respuesta.add(postRequest);
    }

}
