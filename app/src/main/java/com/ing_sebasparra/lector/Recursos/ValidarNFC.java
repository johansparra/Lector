package com.ing_sebasparra.lector.Recursos;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.View.CuentaActivity;
import com.ing_sebasparra.lector.View.PagoTransmilenioActivity;

public class ValidarNFC {

   private NfcAdapter mNfcAdapter;
   private Config config = new Config();

    public void dispositivoCompatible(Context context) {

        mNfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if (mNfcAdapter == null) {
            CuadroDialogo cuadroDialogo=new CuadroDialogo();
            CuentaActivity cuentaActivity =new CuentaActivity();
            cuadroDialogo.mostrar(context,config.TITILO_AVISO_1,config.ALERT_NFC_NOT ,R.drawable.ic_msg_nfc_error,null);
            return;
        }
        Intent PagoIntent = new Intent(context, PagoTransmilenioActivity.class);
        context.startActivity(PagoIntent);
    }
}
