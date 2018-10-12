package com.ing_sebasparra.lector.Recursos;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioButton;

import com.ing_sebasparra.lector.R;

public class ComprobarCampos {

    public boolean validar_campo(Context context, String nombres, String apellidos,
                                 String telefono, String fecha, String email, String password,String identificacion,
                                 EditText nombre_edit, EditText apellido_edit, EditText telefono_edit,
                                 EditText fecha_edit, EditText email_edit, EditText password_edit,EditText tipo,
                                 String setGenero, RadioButton radio1,RadioButton radio2) {

        Boolean mensaje;
        Boolean validaCampos = true;
        if (TextUtils.isEmpty(nombres)) {
            nombre_edit.setError(context.getString(R.string.error_campo));
            nombre_edit.requestFocus();
            validaCampos = false;
        }
        if (TextUtils.isEmpty(apellidos)) {
            apellido_edit.setError(context.getString(R.string.error_campo));
            apellido_edit.requestFocus();
            validaCampos = false;
        }
        if (TextUtils.isEmpty(telefono)) {
            telefono_edit.setError(context.getString(R.string.error_campo));
            telefono_edit.requestFocus();
            validaCampos = false;
        }
        if (TextUtils.isEmpty(fecha)) {
            fecha_edit.setError(context.getString(R.string.error_campo));
            fecha_edit.requestFocus();
            validaCampos = false;
        }
        if (TextUtils.isEmpty(email)) {
            email_edit.setError(context.getString(R.string.error_campo));
            email_edit.requestFocus();
            validaCampos = false;
        }
        if (TextUtils.isEmpty(identificacion)) {
            tipo.setError(context.getString(R.string.error_campo));
            tipo.requestFocus();
            validaCampos = false;
        }
        if (TextUtils.isEmpty(password)) {
          // password_edit.setError(context.getString(R.string.error_campo), context.getDrawable(R.drawable.ic_acerca));
            Drawable icon =
                    context.getResources().getDrawable(R.drawable.ic_action_action_settings);
            if (icon != null) {
                icon.setBounds(-50, 0,
                        icon.getIntrinsicWidth(),
                        icon.getIntrinsicHeight());
            }
            password_edit.setError(context.getString(R.string.error_campo), icon);

            password_edit.requestFocus();
            validaCampos = false;
        }
        if (TextUtils.isEmpty(setGenero)) {
            radio1.setError(context.getString(R.string.error_campo));
            radio2.setError(context.getString(R.string.error_campo));
            radio1.requestFocus();
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


}
