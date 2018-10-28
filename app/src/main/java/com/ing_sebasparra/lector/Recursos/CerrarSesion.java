package com.ing_sebasparra.lector.Recursos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.View.LoginActivity;

public class CerrarSesion extends AppCompatActivity {
    public void logout(final Context context) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Aviso");

        alertDialogBuilder.setMessage(" ¿Seguro que quieres cerrar la sesión? ");
        alertDialogBuilder.setIcon(R.drawable.ic_msg_war);
        alertDialogBuilder.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                        Config config = new Config();
                        SharedPreferences preferences = context.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(config.LOGGEDIN_SHARED_PREF, false);
                        editor.putString(config.EMAIL_SHARED_PREF, "");
                        editor.commit();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancelar ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
