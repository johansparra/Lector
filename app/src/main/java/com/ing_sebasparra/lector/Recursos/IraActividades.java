package com.ing_sebasparra.lector.Recursos;

import android.content.Context;
import android.content.Intent;

import com.ing_sebasparra.lector.View.LoginActivity;

public class IraActividades  {

    public void iraLogin(Context context) {
        Intent Loginintent = new Intent(context, LoginActivity.class);
        context.startActivity(Loginintent);
    }
}
