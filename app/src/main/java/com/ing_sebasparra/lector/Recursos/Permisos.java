package com.ing_sebasparra.lector.Recursos;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Permisos extends AppCompatActivity {
    private static final int PERMISO_FINE = 1000;
    private static final int PERMISO_CAMARA = 2000;



    public void habilitarLocacion(Activity activity) {
        solicitarpermisoGps(activity);
    }

    public boolean permisoGps() {

        int resultado = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (resultado == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void solicitarpermisoGps(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(activity, "El permiso de locacion ya esta activado", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISO_FINE);
        }
    }


    public void habilitarCamara(Activity activity) {
        solicitarpermisoCamara(activity);

    }

    public boolean permisoCamara() {

        int resultado = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (resultado == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void solicitarpermisoCamara(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            Toast.makeText(activity, "El permiso de CAMARA ya esta activado", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PERMISO_CAMARA);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISO_FINE:
                if (permisoGps()) {
                    Toast.makeText(getApplicationContext(), "Ya esta activo la locacion", Toast.LENGTH_SHORT).show();
                } else {
                    //que vuelva a preguntar
                    Toast.makeText(getApplicationContext(), "No esta activo", Toast.LENGTH_SHORT).show();
                }
                break;
            case PERMISO_CAMARA:
                if (permisoCamara()) {
                    Toast.makeText(getApplicationContext(), "Ya esta activo la camara", Toast.LENGTH_SHORT).show();
                } else {
                    //que vuelva a preguntar
                    Toast.makeText(getApplicationContext(), "No esta activa la camara", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

}
