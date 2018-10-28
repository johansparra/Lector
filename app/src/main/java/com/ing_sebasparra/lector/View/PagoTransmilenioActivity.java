package com.ing_sebasparra.lector.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.CuadroDialogo;


public class PagoTransmilenioActivity extends Activity implements CreateNdefMessageCallback,
        OnNdefPushCompleteCallback {
    private static final int MESSAGE_SENT = 1;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SENT:
                    Toast.makeText(getApplicationContext(), "Mensaje enviado!", Toast.LENGTH_LONG).show();
                    Intent intent3 = new Intent(PagoTransmilenioActivity.this, CuentaActivity.class);
                    startActivity(intent3);
                    break;
            }
        }
    };
    public NdefMessage msg;
    NfcAdapter mNfcAdapter;
    TextView mInfoText;

    //VARIABLES
    private TextView  nombreTV, apellidoTV, cedulaTV;
    private String  nombre1,cedula1,idUsuario;

    FrameLayout statusBar;

    //
    Config config = new Config();

    Dialog myDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // dispositivoCompatible();
        setContentView(R.layout.activity_beam);//main

        mInfoText = findViewById(R.id.textView);

        nombreTV = (TextView) findViewById(R.id.nombre);
        apellidoTV = (TextView) findViewById(R.id.apellido);
        cedulaTV = (TextView) findViewById(R.id.cedula);

        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        nombre1 = sharedPreferences.getString(config.NOMBRE_SHARED_PREF, "No Disponible");
        nombreTV.setText(nombre1);
        apellidoTV.setVisibility(View.GONE);
        cedula1 = sharedPreferences.getString(config.N_IDENTIFICACION_SHARED_PREF, "No Disponible");
        cedulaTV.setText(cedula1);
        idUsuario = sharedPreferences.getString(config.ID_USUARIO_SHARED_PREF, "No Disponible");

        toolbarStatusBar();

    }

    private void dispositivoCompatible() {
     /*   mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            //  mInfoText = findViewById(R.id.textView);
            //  mInfoText.setText("NFC no es compatible con este dispositivo");

            Toast.makeText(this, "NFC no es compatible con este dispositivo", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PagoTransmilenioActivity.this, CuentaActivity.class);
            startActivity(intent);
        } else {
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }*/
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            CuadroDialogo cuadroDialogo=new CuadroDialogo();
            CuentaActivity cuentaActivity =new CuentaActivity();
            cuadroDialogo.mostrar(this,config.TITILO_AVISO_1,config.ALERT_NFC_NOT ,R.drawable.logo,cuentaActivity);
            return;
        }
        // Register callback
        mNfcAdapter.setNdefPushMessageCallback(this, this);


    }

    private void dialogomostrar() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogo_acerca);
        dialog.show();

      /*  myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialogo_error_nfc);
        TextView dialog_name = (TextView) myDialog.findViewById(R.id.titulo_dialogo_texto);
        CircleImageView imagen_name = (CircleImageView) myDialog.findViewById(R.id.imagen_dialogo);
        dialog_name.setText("error");
        Picasso.get().load(R.drawable.profile_image).placeholder(R.drawable.profile_image).into(imagen_name);
        myDialog.show();*/
    }

    public void toolbarStatusBar() {
        statusBar = (FrameLayout) findViewById(R.id.statusBar);
    }


    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
      //  String texto = (cedula1);
        String texto = (idUsuario);
        NdefMessage msg = new NdefMessage(NdefRecord.createMime(
                "Aplicación Transmilenio", texto.getBytes())
                //"application/com.example.android.beam", texto.getBytes())
                /**
                 * The Android Application Record (AAR) is commented out. When a device
                 * receives a push with an AAR in it, the application specified in the AAR
                 * is guaranteed to run. The AAR overrides the tag dispatch system.
                 * You can add it back in to guarantee that this
                 * activity starts when receiving a beamed message. For now, this code
                 * uses the tag dispatch system.
                 */
                //,NdefRecord.createApplicationRecord("cffgjghjgkj")
        );
        return msg;
    }

     @Override
    public void onNdefPushComplete(NfcEvent arg0) {
        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android PagoTransmilenioActivity

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
         /*   Intent intent3 = new Intent(PagoTransmilenioActivity.this, PagoTransmilenioActivity.class);
            startActivity(intent3);*/
            processIntent(getIntent());

        }
    }


    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        mInfoText.setText(new String(msg.getRecords()[0].getPayload()));
    }

}