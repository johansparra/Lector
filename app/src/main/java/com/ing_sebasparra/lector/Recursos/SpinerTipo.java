package com.ing_sebasparra.lector.Recursos;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class SpinerTipo implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Toast.makeText(parent.getContext(),
                "click : " + parent.getItemAtPosition(pos).toString()+"id: "+id,
                Toast.LENGTH_SHORT).show();
        if (pos == 0) {
            Toast.makeText(parent.getContext(), "default", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}