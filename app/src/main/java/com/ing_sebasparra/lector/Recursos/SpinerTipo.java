package com.ing_sebasparra.lector.Recursos;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class SpinerTipo  extends Activity implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
      /*  Toast.makeText(parent.getContext(),
                "click : " + parent.getItemAtPosition(pos).toString()+"id: "+id,
                Toast.LENGTH_SHORT).show();*/
      int numero =0;
        if (pos == 0) {
            Toast.makeText(parent.getContext(), "default", Toast.LENGTH_SHORT).show();
            irseleccionado();
        }

        if (pos == 1) {
            Toast.makeText(parent.getContext(), "cedula", Toast.LENGTH_SHORT).show();

        }
        if (pos == 2) {
            Toast.makeText(parent.getContext(), "es el nit", Toast.LENGTH_SHORT).show();
        }
        if (pos == 3) {
            Toast.makeText(parent.getContext(), "es pasaporte", Toast.LENGTH_SHORT).show();
        }
        if (pos == 4) {
            Toast.makeText(parent.getContext(), "es tarjeta de identificacion", Toast.LENGTH_SHORT).show();
        }
        if (pos == 5) {
            Toast.makeText(parent.getContext(), "es cedula de extranjeria", Toast.LENGTH_SHORT).show();
        }

    }

    private void irseleccionado() {

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}