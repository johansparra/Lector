package com.ing_sebasparra.lector.WebServices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ing_sebasparra.lector.UrlServices;

import org.json.JSONException;
import org.json.JSONObject;


public class ApiRes {
    UrlServices url = new UrlServices();

    public void getLogin(final String email, final String password, final Context context) {
//email/PRUEBA@GMAIL.COM/password/123456789
        String urllogin = "email/" + email + "/password/" + password;
        final int status = 0;
        try {
            RequestQueue respuesta = Volley.newRequestQueue(context);
            JsonObjectRequest obejto = new JsonObjectRequest(
                    Request.Method.GET, url.LOGIN_URL_1 + urllogin, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject respuesta) {


                            try {

                                JSONObject request = respuesta.getJSONObject("login");
                                String emailreq = request.getString("email");
                                String passreq = request.getString("password");
                                // String estado1 = respuesta.get("estado");
                                Toast.makeText(context, "email: " + request, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                           //     Toast.makeText(context, "Correo o password incorrectos", Toast.LENGTH_SHORT).show();
                            }


                            Log.e("bien", respuesta.toString());
                            //  Toast.makeText(context, "salida: " + respuesta.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ocurrio un error", error.toString());
                    Toast.makeText(context, "Correo o password incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
            );
            respuesta.add(obejto);

        } catch (Exception error) {
            Log.e("ocurrio un error", error.getMessage().toString());
        }


    }


}
