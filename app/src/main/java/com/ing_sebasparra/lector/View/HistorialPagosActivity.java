package com.ing_sebasparra.lector.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.IraActividades;
import com.ing_sebasparra.lector.Temas.SeleccionTema;

import java.util.Objects;

public class HistorialPagosActivity extends AppCompatActivity {

    //cargar el tema
    Toolbar toolbar;
    FrameLayout statusBar;

    public EditText editParametro1;
    IraActividades iraActividades=new IraActividades();

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
        editParametro1=(EditText)findViewById(R.id.parametro1);
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
