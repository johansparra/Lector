package com.ing_sebasparra.lector.WebServices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.CuentaBancaDTO;
import com.ing_sebasparra.lector.Recursos.IraActividades;
import com.ing_sebasparra.lector.Recursos.UsuarioDTO;
import com.ing_sebasparra.lector.View.CuentaActivity;

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
                            String tipo = "";
                            try {

                                JSONObject request = respuesta.getJSONObject("login");
                                idusuario = request.getString("idUsuario");
                                n_identificacion = request.getString("numIdentificacion");
                                tipo = request.getString("tipoIden");
                                nombre = request.getString("nombre");
                            } catch (JSONException e) {
                                Log.e("Error getLogin: ", e.getMessage());
                            }


                            SharedPreferences sharedPreferences = context.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(config.ID_USUARIO_SHARED_PREF, idusuario);
                            editor.putString(config.N_IDENTIFICACION_SHARED_PREF, n_identificacion);
                            editor.putString(config.TIPO_SHARED_PREF, tipo);
                            editor.putString(config.NOMBRE_SHARED_PREF, nombre);
                            editor.apply();

                            if(idusuario.equals("0")){
                                Toast.makeText(context, "Correo o password incorrectos", Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent = new Intent(context, CuentaActivity.class);
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

    public void consultarSaldo(final String n_identificacion, final Context context, final CuentaActivity perfil) {

        String urlConsulta = n_identificacion;
        try {
            RequestQueue respuesta = Volley.newRequestQueue(context);
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
                                perfil.saldoTV.setText("$"+saldo);
                                perfil.tituloRecargaTV.setText("Valor Ultima Recarga: ");
                                perfil.recargaTV.setText("$"+recarga);
                                perfil.tituloFechaTV.setText("Fecha Recarga: ");
                                perfil.fecharecTV.setText(fecha);



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

    public void consultarCuentaBanca(final CuentaBancaDTO cuentaBancaDTO, final Long valRecarga,final String idUser ,final Context context) {

        final String urlcuentabanca = "CuentaBancaria/User/numiden/"+cuentaBancaDTO.getNumIdentifiacion()+"/tipoiden/"+cuentaBancaDTO.getTipo()+
                          "/numtarjeta/"+cuentaBancaDTO.getNumTarjeta()+"/cvv/"+cuentaBancaDTO.getCvv()+"/fechaVenci/"+ cuentaBancaDTO.getFechaVenci();
        final String[] saldoresq = {""};
        try {
            RequestQueue respuesta = Volley.newRequestQueue(context);
            JsonObjectRequest obejto = new JsonObjectRequest(
                    Request.Method.GET, url.LOGIN_URL_1 + urlcuentabanca, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject respuesta) {
                            String saldo = "";
                            try {

                                JSONObject request = respuesta.getJSONObject("cuentabanca");
                                saldo = request.getString("saldo");
                                if(saldo.equals("NO"))
                                    Toast.makeText(context, "Datos incorrectos o cuenta no existe ", Toast.LENGTH_SHORT).show();
                                else{
                                    if(Long.parseLong(saldo)<=valRecarga){
                                        Toast.makeText(context, "Saldo insuficiente para realizar la recarga ", Toast.LENGTH_SHORT).show();
                                    }else{
                                      //  Toast.makeText(context, "Cargado correctamente ", Toast.LENGTH_SHORT).show();
                                        updateCuentabanca(cuentaBancaDTO,valRecarga,saldo,urlcuentabanca,idUser,context);
                                    }
                                }


                         //       Toast.makeText(context, "Resultado Cuenta: "+saldo, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                Toast.makeText(context, "Error consultarCuentaBanca Json: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, "Error consultarCuentaBanca: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateCuentabanca(CuentaBancaDTO cuentaBancaDTO,final Long valRecarga,final String saldo,String urlcuentabanca,final String idUser,final Context context) {

        RequestQueue queue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("saldoActual", saldo);
        params.put("recarga", String.valueOf(valRecarga));


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                url.LOGIN_URL_1+urlcuentabanca, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ok", response.toString());
                        String estadoRecarga="";
                        try {
                            JSONObject request = response.getJSONObject("recarga");
                            estadoRecarga = request.getString("recarga");
                            Toast.makeText(context, "Recarga exitosa " +estadoRecarga, Toast.LENGTH_SHORT).show();
                            updatetarjetaTransmi(idUser,valRecarga,context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    public void updatetarjetaTransmi(String idUser,final Long valRecarga,final Context context) {

        RequestQueue queue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("recarga", String.valueOf(valRecarga));
        String ulrTarjetaTransmi= "/TarjetaTransmi/"+idUser;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                url.LOGIN_URL_1+ulrTarjetaTransmi, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ok", response.toString());
                        String estadoRecarga="";
                        try {
                            JSONObject request = response.getJSONObject("recarga");
                            estadoRecarga = request.getString("recarga");
                            Toast.makeText(context, "Recarga tarjeta transmilenio exitosa" +estadoRecarga, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
