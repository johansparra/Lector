package com.ing_sebasparra.lector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ing_sebasparra.lector.Recursos.Config;
import com.ing_sebasparra.lector.Recursos.CuadroDialogo;


public class Prueba extends AppCompatActivity {

 private Button dialog,dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        // Set up the login form.
        dialog=(Button)findViewById(R.id.dialogo);
        dialog1=(Button)findViewById(R.id.dialogo1);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogopre();
            }
        });
        dialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogopre1();
            }
        });
       
    }

    private void dialogopre() {
        CuadroDialogo cuadroDialogo=new CuadroDialogo();
    //    cuadroDialogo.mostrar(this,R.drawable.logo);
        Config config = new Config();
      //  cuadroDialogo.mostrar(this,config.TITILO_AVISO_1,config.RECARGA_TARJETA,R.drawable.logo);
    }
    private void dialogopre1() {
        CuadroDialogo cuadroDialogo=new CuadroDialogo();
      //  cuadroDialogo.mostrar(this,R.drawable.z_img_amex_center_face);
    }


}

