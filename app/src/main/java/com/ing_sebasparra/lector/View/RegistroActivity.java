package com.ing_sebasparra.lector.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.ComprobarCampos;
import com.ing_sebasparra.lector.Recursos.ConexionApp;
import com.ing_sebasparra.lector.Recursos.IraActividades;
import com.ing_sebasparra.lector.Recursos.UsuarioDTO;
import com.ing_sebasparra.lector.Temas.SeleccionTema;
import com.ing_sebasparra.lector.WebServices.ApiRest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class RegistroActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout statusBar;
    private EditText edit_nombres, edit_apellidos, edit_telefono, edit_fecha, edit_email, edit_password, edit_spiner;
    private TextInputLayout lay_spiner;
    private RadioButton radio1, radio2;
    private String fechapick;
    private int n_identificacion = 0;
    private String generoR = null;
    private Spinner tipoidentificacion;
    private Button registro;
    public ProgressDialog loadingbar;

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
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.registro));
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
        lay_spiner = (TextInputLayout) findViewById(R.id.layout_spineer);
        edit_spiner = (EditText) findViewById(R.id.reg_spinner);

        loadingbar = new ProgressDialog(this);

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
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                registro.setVisibility(View.VISIBLE);
                if (pos == 0) {
                    lay_spiner.setVisibility(View.GONE);
                    registro.setVisibility(View.GONE);
                }
                if (pos == 1) {
                    lay_spiner.setVisibility(View.VISIBLE);
                    edit_spiner.setHint("Cedula");
                    n_identificacion = 1;
                }
                if (pos == 2) {
                    lay_spiner.setVisibility(View.VISIBLE);
                    edit_spiner.setHint("NIT");
                    n_identificacion = 2;
                }
                if (pos == 3) {
                    lay_spiner.setVisibility(View.VISIBLE);
                    edit_spiner.setHint("Pasaporte");
                    n_identificacion = 3;
                }

                if (pos == 4) {
                    lay_spiner.setVisibility(View.VISIBLE);
                    edit_spiner.setHint("Tarjeta de identificación");
                    n_identificacion = 4;
                }
                if (pos == 5) {
                    lay_spiner.setVisibility(View.VISIBLE);
                    edit_spiner.setHint("Cedula extranjera");
                    n_identificacion = 5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
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
                        SimpleDateFormat simple = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
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

                UsuarioDTO usuarioDTO = new UsuarioDTO();

                ConexionApp conect = new ConexionApp();
                if (!conect.conexionWifi(RegistroActivity.this)) {
                    Toast.makeText(RegistroActivity.this, getResources().getString(R.string.error_mensaje_conexion), Toast.LENGTH_SHORT).show();
                    return;
                }
                usuarioDTO.setNombres(edit_nombres.getText().toString().trim());
                usuarioDTO.setApellidos(edit_apellidos.getText().toString().trim());
                usuarioDTO.setCelular(edit_telefono.getText().toString().trim());
                usuarioDTO.setFechaNaci(edit_fecha.getText().toString().trim());
                usuarioDTO.setEmail(edit_email.getText().toString().trim());
                usuarioDTO.setPassword(edit_password.getText().toString().trim());
                usuarioDTO.setTipo_identificacion(n_identificacion);
                usuarioDTO.setNum_identificacion(edit_spiner.getText().toString().trim());
                usuarioDTO.setGenero(generoR);

                ComprobarCampos comprobarCampos = new ComprobarCampos();

                if (comprobarCampos.validar_campo(RegistroActivity.this,
                        usuarioDTO, edit_nombres, edit_apellidos, edit_telefono,
                        edit_fecha, edit_email, edit_password, edit_spiner, radio1, radio2)) {
                    loadingbar.setTitle("Registrando");
                    loadingbar.setMessage("Por favor espera");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                    ApiRest apirest = new ApiRest();
                    apirest.consultarUsuario(usuarioDTO, RegistroActivity.this);
                    loadingbar.dismiss();

                }


            }
        });


    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.RDhombre:
                if (checked)
                    generoR = "M";
                radio1.setError(null);
                radio2.setError(null);
                break;
            case R.id.RDmujer:
                if (checked)
                    generoR = "F";
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
