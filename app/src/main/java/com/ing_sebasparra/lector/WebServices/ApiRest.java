package com.ing_sebasparra.lector.WebServices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.IraActividades;
import com.ing_sebasparra.lector.Recursos.UsuarioDTO;
import com.ing_sebasparra.lector.View.PerfilActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ApiRest {
    private UrlServices url = new UrlServices();
    IraActividades actividades = new IraActividades();
    Config config = new Config();


    public void getLogin(String email, String password, final Context context) {
        RequestQueue respuesta = Volley.newRequestQueue(context);
        String urllogin = "email/" + email + "/password/" + password;

        try {
            JsonObjectRequest obejto = new JsonObjectRequest(
                    Request.Method.GET, url.LOGIN_URL_1 + urllogin, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject respuesta) {
                            String idusuario = "";
                            String n_identificacion = "";
                            String nombre = "";
                            try {

                                JSONObject request = respuesta.getJSONObject("login");
                                idusuario = request.getString("idUsuario");
                                n_identificacion = request.getString("numIdentificacion");
                                nombre = request.getString("nombre");
                            } catch (JSONException e) {
                                Log.e("Error getLogin: ", e.getMessage());
                            }


                            SharedPreferences sharedPreferences = context.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(config.ID_USUARIO_SHARED_PREF, idusuario);
                            editor.putString(config.N_IDENTIFICACION_SHARED_PREF, n_identificacion);
                            editor.putString(config.NOMBRE_SHARED_PREF, nombre);
                            editor.apply();

                            if(idusuario.equals("0")){
                                Toast.makeText(context, "Correo o password incorrectos", Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent = new Intent(context, PerfilActivity.class);
                                context.startActivity(intent);
                            }



                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error getLogin:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            );
            respuesta.add(obejto);


        } catch (Exception error) {
            Log.e("ocurrio un error", error.getMessage().toString());

        }
        //para terminarlo
       cancelarRespuestas(respuesta,context);

    }

    public void consultarUsuario(final UsuarioDTO usuarioDTO, final Context context) {

        String urlConsulta = "tipoIden/" + usuarioDTO.getTipo_identificacion() +
                "/User/" + usuarioDTO.getNum_identificacion() + "/email/" + usuarioDTO.getEmail();
        try {
            RequestQueue respuesta = Volley.newRequestQueue(context);
            JsonObjectRequest obejto = new JsonObjectRequest(
                    Request.Method.GET, url.CONSULTA_URL + urlConsulta, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject respuesta) {

                            String consulta = "";
                            try {

                                JSONObject request = respuesta.getJSONObject("usuario");
                                consulta = request.getString("consultar");

                                if (consulta.equals("OK")) {
                                    postRegistro(usuarioDTO, context);
                                } else {
                                    Toast.makeText(context, consulta, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(context, "Error Consulta Json: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );
            respuesta.add(obejto);


        } catch (Exception error) {
            Toast.makeText(context, "Error Consulta: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    public void consultarSaldo(final String n_identificacion, final Context context) {

        RequestQueue respuesta;
        final Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        respuesta = new RequestQueue(cache, network);

        respuesta.start();

        String urlConsulta = n_identificacion;
        try {
          //  RequestQueue respuesta = Volley.newRequestQueue(context);
            JsonObjectRequest obejto = new JsonObjectRequest(
                    Request.Method.GET, url.CONSULTA_SALDO_URL + urlConsulta, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject respuesta) {

                            String recarga = "";
                            String saldo = "";
                            String fecha = "";
                            try {

                                JSONObject request = respuesta.getJSONObject("cuenta");
                                recarga = request.getString("ultimaRecarga");
                                saldo = request.getString("saldo");
                                fecha = request.getString("fechaRecarga");
                               /* ConsultaDTO consultaDTO=new ConsultaDTO();
                                consultaDTO.setSaldo(saldo);
                                consultaDTO.setRecarga(recarga);
                                consultaDTO.setFecha(fecha);*/

                                SharedPreferences sharedPreferences = context.getSharedPreferences(config.SHARED_PREF_CONSULTA, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(config.SALDO_SHARED_PREF, saldo);
                                editor.apply();
                                //actividades.iraCuenta(context);


                            } catch (JSONException e) {
                                Toast.makeText(context, "Error Consulta Json: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );
            respuesta.add(obejto);


        } catch (Exception error) {
            Toast.makeText(context, "Error Consulta: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void postRegistro(UsuarioDTO usuarioDTO, final Context context) {

        RequestQueue queue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("numIdentificacion", usuarioDTO.getNum_identificacion());
        params.put("tipoIdentificacion", String.valueOf(usuarioDTO.getTipo_identificacion()));
        params.put("nombre", usuarioDTO.getNombres());
        params.put("apellidos", usuarioDTO.getApellidos());
        params.put("fechaNaci", usuarioDTO.getFechaNaci());
        params.put("sexo", usuarioDTO.getGenero());
        params.put("telefono", "0");
        params.put("celular", usuarioDTO.getCelular());
        params.put("email", usuarioDTO.getEmail());
        params.put("password", usuarioDTO.getPassword());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url.REGISTRO_URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ok", response.toString());
                        Toast.makeText(context, "Usuario registrado Correctamenten", Toast.LENGTH_SHORT).show();
                        actividades.iraLogin(context);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(context,
                            context.getString(R.string.error_network_timeout),
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    //TODO
                } else if (error instanceof NetworkError) {
                    //TODO
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "Error inconsistencia de datos", Toast.LENGTH_SHORT).show();
                    //TODO
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        queue.add(jsonObjReq);
    }

    public void cancelarRespuestas(RequestQueue respuesta,Context context){
       // respuesta.cancelAll("PETICIONES");
        respuesta.cancelAll(context);

    }




}
