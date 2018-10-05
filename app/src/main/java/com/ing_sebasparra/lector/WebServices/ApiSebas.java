package com.ing_sebasparra.lector.WebServices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ing_sebasparra.lector.UrlServices;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiSebas {

    int respuesta = 0;
    StringBuilder resultado = null;
    String linea;



    public String getLoginSebas(final String email, final String password, final Context context) {
        UrlServices urlserv = new UrlServices();
        try {
            URL url = new URL(urlserv.LOGIN_URL_1 + "1069738505");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            int status = Integer.valueOf(httpURLConnection.getResponseMessage());
            Log.e("Status", String.valueOf(status));
            respuesta = httpURLConnection.getResponseCode();
            resultado = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader leer = new BufferedReader(new InputStreamReader(in));

                Toast.makeText(context, "Respuesta" + respuesta, Toast.LENGTH_SHORT).show();
                Log.e("Respuesta", String.valueOf(respuesta));

                while ((linea = leer.readLine()) != null) {
                    resultado.append(linea);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("Respuesta", String.valueOf(ex));
          //  return "Error1: " + e.getMessage();

        }

     //   return resultado.toString();
        return resultado.toString();

    }
}
