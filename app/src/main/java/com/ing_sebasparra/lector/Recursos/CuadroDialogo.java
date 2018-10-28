package com.ing_sebasparra.lector.Recursos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

public class CuadroDialogo {

    public void mostrar(final Context context, String titulo, String mensaje, int icon, final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(titulo);
        alertDialogBuilder.setMessage(mensaje);
        alertDialogBuilder.setIcon(icon);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK",

                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if(activity!=null){
                            Intent CuentaIntent = new Intent(context,activity.getClass());
                            context.startActivity(CuentaIntent);
                        }
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


}
