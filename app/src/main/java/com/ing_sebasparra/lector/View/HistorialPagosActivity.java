package com.ing_sebasparra.lector.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.CuentaBancaDTO;
import com.ing_sebasparra.lector.Recursos.IraActividades;
import com.ing_sebasparra.lector.Temas.SeleccionTema;
import com.ing_sebasparra.lector.WebServices.ApiRest;

import java.util.Objects;

public class HistorialPagosActivity extends AppCompatActivity {

    //cargar el tema
    Toolbar toolbar;
    FrameLayout statusBar;
    private EditText editValorRecarga;

    IraActividades iraActividades=new IraActividades();
    private Button btPagar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SeleccionTema selecTema = new SeleccionTema();
        selecTema.theme(this);
        setContentView(R.layout.activity_historial);

        toolbarStatusBar();
        inicializeValues();
    }

    private void inicializeValues() {
        editValorRecarga = (EditText) findViewById(R.id.valorRecarga);
        btPagar = findViewById(R.id.buttonPagar);
        btPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "";
                String password = "";
                Toast.makeText(HistorialPagosActivity.this, "Usuario registrado Correctamenten", Toast.LENGTH_SHORT).show();
                getCuentaBancaria();
            }
        });

    }

    public void getCuentaBancaria(){
        try {
            CuentaBancaDTO cuentabancaDTO =new CuentaBancaDTO();
            ApiRest apirest = new ApiRest();
            Config config = new Config();
            SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String idUser = sharedPreferences.getString(config.ID_USUARIO_SHARED_PREF, "No Disponible");


            Long valRecarga=Long.parseLong(editValorRecarga.getText().toString().trim());
            cuentabancaDTO.setNumIdentifiacion("1069738505");
            cuentabancaDTO.setTipo(1);
            cuentabancaDTO.setNumTarjeta("4594186388989000");
            cuentabancaDTO.setCvv(123);
            cuentabancaDTO.setFechaVenci(1224);

            apirest.consultarCuentaBanca(cuentabancaDTO,valRecarga,idUser,this);

        }catch (Exception ex){
            Log.e("Error getCuentaBancaria",ex.getMessage());

        }
    }


    public void toolbarStatusBar() {
        statusBar = findViewById(R.id.statusBar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("titulo de esto");
    }

    @Override
    public void onBackPressed() {
      iraActividades.iraCuenta(this);

    }




}
