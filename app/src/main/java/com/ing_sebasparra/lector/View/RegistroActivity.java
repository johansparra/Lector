package com.ing_sebasparra.lector.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Temas.SeleccionTema;

public class RegistroActivity extends AppCompatActivity {

    //cargar el tema
    Toolbar toolbar;
    FrameLayout statusBar;
    private EditText nombres,apellidos,telefono,fecha,email,password;
    private RadioGroup genero;
    private RadioButton radio1, radio2;
    private String fechapick;
    private String generoR = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SeleccionTema selecTema = new SeleccionTema();
        selecTema.theme(this);
        setContentView(R.layout.activity_registro);

        toolbarStatusBar();
        inicializeValues();

    }



    public void toolbarStatusBar() {
        statusBar = findViewById(R.id.statusBar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.registro));

    }
    private void inicializeValues() {

    }
}
