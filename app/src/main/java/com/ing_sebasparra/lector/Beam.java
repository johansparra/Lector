package com.ing_sebasparra.lector;

import android.app.Activity;
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
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Beam extends Activity implements CreateNdefMessageCallback,
        OnNdefPushCompleteCallback {
    private static final int MESSAGE_SENT = 1;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SENT:
                    Toast.makeText(getApplicationContext(), "Mensaje enviado!", Toast.LENGTH_LONG).show();
                    Intent intent3 = new Intent(Beam.this, MainActivity.class);
                    startActivity(intent3);
                    break;
            }
        }
    };
    public NdefMessage msg;
    NfcAdapter mNfcAdapter;
    TextView mInfoText;

    private boolean loggedIn = false;
    //VARIABLES
    private TextView emailTV, nombreTV,apellidoTV, cargoTV, fotoTV, cedulaTV, nmostrar;
    private String email1, nombre1,apellido1, cargo1, foto1, cedula1;

    Toolbar toolbar;
    FrameLayout statusBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beam);//main
        sabersiestalog();

        mInfoText = findViewById(R.id.textView);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            mInfoText = findViewById(R.id.textView);
            mInfoText.setText("NFC no es compatible con este dispositivo");
        } else {
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }


        //VARIABLES

        nombreTV = (TextView) findViewById(R.id.nombre);
        apellidoTV = (TextView) findViewById(R.id.apellido);
        cedulaTV = (TextView) findViewById(R.id.cedula);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        nombre1 = sharedPreferences.getString(Config.NOMBRE_SHARED_PREF, "No Disponible");
        nombreTV.setText(nombre1);
        apellido1 = sharedPreferences.getString(Config.APELLIDOS_SHARED_PREF, "No Disponible");
        apellidoTV.setText(apellido1);
        cedula1 = sharedPreferences.getString(Config.MATRICULA_SHARED_PREF, "No Disponible");
        cedulaTV.setText(cedula1);


        toolbarStatusBar();
        //toolbar = (Toolbar) findViewById(R.id.toolbar);



    }


    public void toolbarStatusBar() {
        statusBar = (FrameLayout) findViewById(R.id.statusBar);
       // toolbar = (Toolbar) findViewById(R.id.toolbar);
   /*     setSupportActionBar(toolbar);
        // Titulo que se visualiza en en action bar
        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }


    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        //String texto = ("Val"+"1069738505");
       // String texto = ("1069738505");
        String texto = (cedula1);
        NdefMessage msg = new NdefMessage(NdefRecord.createMime(
                "Aplicacion Transmilenio", texto.getBytes())
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

    /**
     * Implementation for the OnNdefPushCompleteCallback interface
     */
    @Override
    public void onNdefPushComplete(NfcEvent arg0) {
        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
         /*   Intent intent3 = new Intent(Beam.this, Beam.class);
            startActivity(intent3);*/
            processIntent(getIntent());

        }
    }

    private void sabersiestalog() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
        if (!loggedIn) {
            Toast.makeText(this, "Tienes que ingresar a tu cuenta", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Beam.this, LoginActivity.class);
            startActivity(intent);
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