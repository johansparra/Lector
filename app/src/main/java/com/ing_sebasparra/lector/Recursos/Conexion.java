package com.ing_sebasparra.lector.Recursos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ing_sebasparra.lector.View.LoginActivity;

public class Conexion {

    public boolean conexionWifi (LoginActivity login){
        boolean wifimobile= false;
        boolean wifiDatos= false;
        boolean conexion = false;

        try {
            ConnectivityManager cm;
            NetworkInfo ni;
            cm = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
            ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                ConnectivityManager connManager1 = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                ConnectivityManager connManager2 = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mMobile = connManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if (mWifi.isConnected()) {
                    wifiDatos = true;
                }
                if (mMobile.isConnected()) {
                    wifimobile = true;
                }
                if (wifiDatos || wifimobile ) {
                    conexion = true;
                    //Llamando a la funcion login
                   // validar_campo();
                   // login();
                }
            }

        }catch (Exception ex){
            Log.e("Error conexion wifi",ex.getMessage());

        }
        return conexion;

    }

}
