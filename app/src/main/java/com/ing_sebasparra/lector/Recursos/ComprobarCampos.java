package com.ing_sebasparra.lector.Recursos;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioButton;

import com.ing_sebasparra.lector.R;

public class ComprobarCampos {

    public boolean validar_campo(Context context,UsuarioDTO usuarioDTO,
                                 EditText nombre_edit, EditText apellido_edit, EditText telefono_edit,
                                 EditText fecha_edit, EditText email_edit, EditText password_edit, EditText tipo,
                                 RadioButton radio1, RadioButton radio2) {

        Boolean mensaje;
        Boolean validaCampos = true;
        if (TextUtils.isEmpty(usuarioDTO.getNombres())) {
            nombre_edit.setError(context.getString(R.string.error_campo));
            nombre_edit.requestFocus();
            validaCampos = false;
        }
     /*   if(validaNumericos(context,nombre_edit,usuarioDTO.getNombres())){
            validaCampos=false;
        }*/

        if(validarLongitud(context,nombre_edit,usuarioDTO.getNombres(),3)){
            validaCampos = false;
        }

        if (TextUtils.isEmpty(usuarioDTO.getApellidos())) {
            apellido_edit.setError(context.getString(R.string.error_campo));
            apellido_edit.requestFocus();
            validaCampos = false;
        }
        if (TextUtils.isEmpty(usuarioDTO.getCelular())) {
            telefono_edit.setError(context.getString(R.string.error_campo));
            telefono_edit.requestFocus();
            validaCampos = false;
        }
     /*   if (TextUtils.isEmpty(telefono)) {
            telefono_edit.setError(context.getString(R.string.error_campo));
            telefono_edit.requestFocus();
            validaCampos = false;
        }*/
        if (TextUtils.isEmpty(usuarioDTO.getFechaNaci())) {
            fecha_edit.setError(context.getString(R.string.error_campo));
            fecha_edit.requestFocus();
            validaCampos = false;
        }
        if (validaCampos) {
            ValidacionDatos validar = new ValidacionDatos();
            mensaje = validar.validaremail(usuarioDTO.getEmail());
            if (mensaje) {
                email_edit.setError(context.getString(R.string.error_invalid_email_di));
                email_edit.requestFocus();
                validaCampos = false;
            }
        }
        if (TextUtils.isEmpty(usuarioDTO.getEmail())) {
            email_edit.setError(context.getString(R.string.error_campo));
            email_edit.requestFocus();
            validaCampos = false;
        }
        if (TextUtils.isEmpty(usuarioDTO.getNum_identificacion())) {
            tipo.setError(context.getString(R.string.error_campo));
            tipo.requestFocus();
            validaCampos = false;
        }
        if (TextUtils.isEmpty(usuarioDTO.getPassword())) {
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
        if (TextUtils.isEmpty(usuarioDTO.getGenero())) {
            radio1.setError(context.getString(R.string.error_campo));
            radio2.setError(context.getString(R.string.error_campo));
            radio1.requestFocus();
            validaCampos = false;
        }



        return validaCampos;

    }

/*public boolean validaNumericos(Context context,EditText editText, String campo){
        boolean esNumero=false;
    if (campo.contains("[0-9]+") == true) {
        editText.setError(context.getString(R.string.error_nombre_numero));
        editText.requestFocus();
        esNumero=true;
    }
    return esNumero;
}*/

  /*  public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;

        }

        return resultado;
    }
    */

    public boolean validarLongitud(Context context,EditText editText,
                                   String campo,int longitud) {
        boolean vallong = false;
        if (campo.length() < longitud) {
            editText.setError(context.getString(R.string.error_nombre_menor) + longitud + context.getString(R.string.error_caracteres));
            editText.requestFocus();
            vallong = true;

        }
        return vallong;
    }



}
