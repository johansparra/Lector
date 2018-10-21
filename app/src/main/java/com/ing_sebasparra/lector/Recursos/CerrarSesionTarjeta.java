package com.ing_sebasparra.lector.Recursos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.ing_sebasparra.lector.View.CuentaActivity;

public class CerrarSesionTarjeta extends AppCompatActivity {
    public void logout(final Context context) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(" ¿Seguro que quieres quitar la tarjeta? ");
        alertDialogBuilder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                        Config config = new Config();
                        SharedPreferences preferences = context.getSharedPreferences(config.SHARED_PREF_TARJETA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(config.TARJETA_SHARED_PREF, false);
                        editor.putString(config.TARJETA_CVV, "");
                        editor.putString(config.TARJETA_NOMBRE, "");
                        editor.putString(config.TARJETA_EXPIRA, "");
                        editor.putString(config.TARJETA_NUMERO, "");
                        editor.commit();
                        Intent intent = new Intent(context, CuentaActivity.class);
                        context.startActivity(intent);
                    }
                });
        alertDialogBuilder.setNegativeButton("No ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
