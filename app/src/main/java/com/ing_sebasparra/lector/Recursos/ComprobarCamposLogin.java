package com.ing_sebasparra.lector.Recursos;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.ing_sebasparra.lector.R;

public class ComprobarCamposLogin {
    public boolean validar_campo(Context context, String email,String password,
                                 EditText email_edit, EditText password_edit) {
        Boolean mensaje;
        Boolean validaCampos = true;
        if (TextUtils.isEmpty(email)) {
            email_edit.setError(context.getString(R.string.error_campo));
            email_edit.requestFocus();
            validaCampos = false;
        }
        if (validarLongitud(context, password_edit, password, 3)) {
            validaCampos = false;
        }
        if (TextUtils.isEmpty(password)) {
            password_edit.setError(context.getString(R.string.error_campo));
            password_edit.requestFocus();
            validaCampos = false;
        }

        if (validaCampos) {
            ValidacionDatos validar = new ValidacionDatos();
            mensaje = validar.validaremail(email);
            if (mensaje) {
                email_edit.setError(context.getString(R.string.error_invalid_email_di));
                email_edit.requestFocus();
                validaCampos = false;
            }
        }

        return validaCampos;
    }

    public boolean validarLongitud(Context context, EditText editText,
                                   String campo, int longitud) {
        boolean vallong = false;
        if (campo.length() < longitud) {
            editText.setError(context.getString(R.string.error_nombre_menor) + longitud + context.getString(R.string.error_caracteres));
            editText.requestFocus();
            vallong = true;

        }
        return vallong;
    }
}
