package com.ing_sebasparra.lector.Recursos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

public class ConexionApp {

    public boolean conexionWifi(Context login) {

        boolean conexion = false;

        try {
            ConnectivityManager connMgr =
                    (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean isWifiConn = false;
            boolean isMobileConn = false;
            assert connMgr != null;
            for (Network network : connMgr.getAllNetworks()) {
                NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConn |= networkInfo.isConnected();
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    isMobileConn |= networkInfo.isConnected();
                }
            }
            if (isWifiConn || isMobileConn) {
                conexion = true;
            }


        } catch (Exception ex) {
            Log.e("Error conexion wifi", ex.getMessage());

        }
        return conexion;

    }

}
