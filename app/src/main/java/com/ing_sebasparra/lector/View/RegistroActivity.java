package com.ing_sebasparra.lector.View;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.ComprobarCampos;
import com.ing_sebasparra.lector.Recursos.ConexionApp;
import com.ing_sebasparra.lector.Recursos.IraActividades;
import com.ing_sebasparra.lector.Temas.SeleccionTema;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegistroActivity extends AppCompatActivity {


    Toolbar toolbar;
    FrameLayout statusBar;
    private EditText edit_nombres, edit_apellidos, edit_telefono, edit_fecha,edit_email, edit_password,edit_spiner;
    private TextInputLayout lay_nombres,lay_apellidos,lay_telefono,lay_fecha,lay_email,lay_password,lay_spiner;
    private RadioGroup genero;
    private RadioButton radio1, radio2;
    private String fechapick;
    private String generoR = null;
    private Spinner tipoidentificacion;
    private Button registro;

    IraActividades actividadesir = new IraActividades();

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

    }

    private void inicializeValues() {

        edit_nombres = (EditText) findViewById(R.id.reg_nombres);
        edit_apellidos = (EditText) findViewById(R.id.reg_apellidos);
        edit_telefono = (EditText) findViewById(R.id.reg_telefono);
        edit_fecha = (EditText) findViewById(R.id.reg_fecha);
        edit_email = (EditText) findViewById(R.id.reg_email);
        edit_password = (EditText) findViewById(R.id.reg_password);
        lay_nombres=(TextInputLayout)findViewById(R.id.layout_nombres);
        lay_apellidos=(TextInputLayout)findViewById(R.id.layout_apellidos);
        lay_telefono=(TextInputLayout)findViewById(R.id.layout_telefono);
        lay_fecha=(TextInputLayout)findViewById(R.id.layout_fecha);
        lay_email=(TextInputLayout)findViewById(R.id.layout_email);
        lay_password=(TextInputLayout)findViewById(R.id.layout_password);
        lay_spiner=(TextInputLayout)findViewById(R.id.layout_spineer);





        genero = (RadioGroup) findViewById(R.id.genero_radio);
        radio1 = (RadioButton) findViewById(R.id.RDhombre);
        radio2 = (RadioButton) findViewById(R.id.RDmujer);
        tipoidentificacion = (Spinner) findViewById(R.id.spiner_identificacion);
        registro = (Button) findViewById(R.id.btn_registro);



     /*   ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_identificacion, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoidentificacion.setAdapter(adapter);
          tipoidentificacion.setOnItemSelectedListener(new SpinerTipo());*/

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_identificacion, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoidentificacion.setAdapter(adapter);
        tipoidentificacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                Toast.makeText(adapterView.getContext(),
                        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
                if(pos==0){
                    lay_spiner.setVisibility(View.GONE);
                }
                if(pos==3){
                    lay_spiner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });





        edit_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendario = Calendar.getInstance();
                DatePickerDialog pickerDialog = new DatePickerDialog(RegistroActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int mes, int dia) {
                        calendario.set(Calendar.YEAR, year);
                        calendario.set(Calendar.MONTH, mes);
                        calendario.set(Calendar.DAY_OF_MONTH, dia);
                        SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date date = calendario.getTime();
                        fechapick = simple.format(date);
                        edit_fecha.setText(fechapick);
                        edit_fecha.setError(null);
                    }
                }, calendario.get(Calendar.YEAR) - 18, calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombres = "";
                String apellidos = "";
                String telefono = "";
                String fecha = "";
                String email = "";
                String password = "";

                ConexionApp conect = new ConexionApp();
                if (!conect.conexionWifi(RegistroActivity.this)) {
                    Toast.makeText(RegistroActivity.this, getResources().getString(R.string.error_mensaje_conexion), Toast.LENGTH_SHORT).show();
                    return;
                }
                nombres = edit_nombres.getText().toString().trim();
                apellidos = edit_apellidos.getText().toString().trim();
                telefono = edit_telefono.getText().toString().trim();
                fecha = edit_fecha.getText().toString().trim();
                email = edit_email.getText().toString().trim();
                password = edit_password.getText().toString().trim();
                String setGenero = generoR;
                ComprobarCampos comprobarCampos=new ComprobarCampos();
                if (comprobarCampos.validar_campo(RegistroActivity.this,nombres,apellidos,
                        telefono,fecha,email,password,edit_nombres,edit_apellidos,edit_telefono,
                        edit_fecha,edit_email,edit_password,setGenero,radio1,radio2)){
                    Toast.makeText(RegistroActivity.this, "si", Toast.LENGTH_SHORT).show();
                }
                /*if (validar_campo(email, password)) {
                    showProgress(true);
                    servicesLogin(email, password);
                    showProgress(false);
                }*/


            }
        });


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.RDhombre:
                if (checked)
                    generoR = "Hombre";
                radio1.setError(null);
                radio2.setError(null);
                break;
            case R.id.RDmujer:
                if (checked)
                    generoR = "Mujer";
                radio1.setError(null);
                radio2.setError(null);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        actividadesir.iraLogin(this);
    }
}
