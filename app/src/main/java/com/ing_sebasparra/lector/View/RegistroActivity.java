package com.ing_sebasparra.lector.View;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.SpinerTipo;
import com.ing_sebasparra.lector.Temas.SeleccionTema;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegistroActivity extends AppCompatActivity {

    //cargar el tema
    Toolbar toolbar;
    FrameLayout statusBar;
    private EditText nombres, apellidos, telefono, fecha, email, password;
    private RadioGroup genero;
    private RadioButton radio1, radio2;
    private String fechapick;
    private String generoR = null;
    private Spinner tipoidentificacion;

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

        nombres = (EditText) findViewById(R.id.reg_nombres);
        apellidos = (EditText) findViewById(R.id.reg_apellidos);
        telefono = (EditText) findViewById(R.id.reg_telefono);
        fecha = (EditText) findViewById(R.id.reg_fecha);
        email = (EditText) findViewById(R.id.reg_email);
        password = (EditText) findViewById(R.id.reg_password);
        genero = (RadioGroup) findViewById(R.id.genero_radio);
        radio1 = (RadioButton) findViewById(R.id.RDhombre);
        radio2 = (RadioButton) findViewById(R.id.RDmujer);
        tipoidentificacion = (Spinner) findViewById(R.id.spiner_identificacion);


        // Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_identificacion, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        tipoidentificacion.setAdapter(adapter);
        tipoidentificacion.setOnItemSelectedListener(new SpinerTipo());



        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendario = Calendar.getInstance();
                DatePickerDialog pickerDialog = new DatePickerDialog(RegistroActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int mes, int dia) {
                        //   Calendar calendariores = Calendar.getInstance();
                        calendario.set(Calendar.YEAR, year);
                        calendario.set(Calendar.MONTH, mes);
                        calendario.set(Calendar.DAY_OF_MONTH, dia);
                        SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date date = calendario.getTime();
                        fechapick = simple.format(date);
                        fecha.setText(fechapick);
                        fecha.setError(null);
                    }
                }, calendario.get(Calendar.YEAR) - 18, calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.RDhombre:
                if (checked)
                    //   Toast.makeText(this, "HOMBRE ESSCOGIO", Toast.LENGTH_SHORT).show();
                    generoR = "Hombre";
                radio1.setError(null);
                radio2.setError(null);
                break;
            case R.id.RDmujer:
                if (checked)
                    generoR = "Mujer";
                radio1.setError(null);
                radio2.setError(null);
                //    Toast.makeText(this, "MUJER ESSCOGIO", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
