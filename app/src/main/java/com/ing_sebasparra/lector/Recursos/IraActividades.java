package com.ing_sebasparra.lector.Recursos;

import android.content.Context;
import android.content.Intent;

import com.ing_sebasparra.lector.GpsActivity;
import com.ing_sebasparra.lector.Prueba;
import com.ing_sebasparra.lector.TarjetaCredito.CardEditActivity;
import com.ing_sebasparra.lector.TarjetaCredito.CardGuardadaActivity;
import com.ing_sebasparra.lector.View.CuentaActivity;
import com.ing_sebasparra.lector.View.LoginActivity;
import com.ing_sebasparra.lector.View.MapsActivity;
import com.ing_sebasparra.lector.View.OpcionesActivity;
import com.ing_sebasparra.lector.View.PagoTransmilenioActivity;

public class IraActividades  {

    public void iraLogin(Context context) {
        Intent Loginintent = new Intent(context, LoginActivity.class);
        context.startActivity(Loginintent);
    }
    public void iraCuenta(Context context) {
        Intent CuentaIntent = new Intent(context, CuentaActivity.class);
        context.startActivity(CuentaIntent);
    }
    public void iraTarjetaCredito(Context context) {
        Intent TarjetaIntent = new Intent(context, CardEditActivity.class);
        context.startActivity(TarjetaIntent);
    }
    public void iraPagoTransmilenio(Context context) {
        Intent PagoIntent = new Intent(context, PagoTransmilenioActivity.class);
        context.startActivity(PagoIntent);
    }
    public void iraGPS(Context context) {
        Intent GpsIntent = new Intent(context, GpsActivity.class);
        context.startActivity(GpsIntent);
    }
    public void iraOpciones(Context context) {
        Intent OpcionesIntent = new Intent(context, OpcionesActivity.class);
        context.startActivity(OpcionesIntent);
    }
    public void iraMaps(Context context) {
        Intent MapsIntent = new Intent(context, MapsActivity.class);
        context.startActivity(MapsIntent);
    }
    public void iraCardGuardada(Context context) {
        Intent CardGuardadaIntent = new Intent(context, CardGuardadaActivity.class);
        context.startActivity(CardGuardadaIntent);
    }
    public void iraPrueba(Context context) {
        Intent CardGuardadaIntent = new Intent(context, Prueba.class);
        context.startActivity(CardGuardadaIntent);
    }

}
