package com.ing_sebasparra.lector.Recursos;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class SalirAplicacion {

    public static long lastClickTime;

 /*   public void now(Activity ctx, @StringRes int message) {
        now(ctx, ctx.getString(message), 2500);
    }

    public void now(Activity ctx, @StringRes int message, long time) {
        now(ctx, ctx.getString(message), time);
    }*/

    public void now(Context context, Activity ctx, String mensaje, long tiempo) {
        if (ctx != null && !mensaje.isEmpty() && tiempo != 0) {
            if (lastClickTime + tiempo > System.currentTimeMillis()) {
                salir(context,ctx);

            } else {
                Toast.makeText(ctx, mensaje, Toast.LENGTH_SHORT).show();
                lastClickTime = System.currentTimeMillis();
            }
        }
    }

    private void salir(Context ctx1, Activity ctx) {

        ActivityCompat.finishAffinity(ctx);
        System.exit(1);


      /*   Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);

        System.exit(1);
        ctx.finish();

        System.exit(0);
        ActivityCompat.finishAffinity(ctx);

        System.exit(1);

        System.exit(1);
        ctx.moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);*/

    }
}
