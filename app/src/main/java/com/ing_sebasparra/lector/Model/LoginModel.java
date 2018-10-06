package com.ing_sebasparra.lector.Model;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;

import com.ing_sebasparra.lector.View.LoginActivity;
import com.ing_sebasparra.lector.R;
import com.ing_sebasparra.lector.Recursos.ValidacionDatos;

public class LoginModel extends LoginActivity {

    LoginActivity login;

    public boolean validarLogin(String email, String password) {
        boolean validalogin = false;
        Boolean mensaje = false;
        boolean loginOK =true;
        try {

            if (validarCamposVacios(email,password)) {
                ValidacionDatos validar = new ValidacionDatos();
                mensaje = validar.validaremail(email);
                if (mensaje)
                    loginOK = false;
            }else
                loginOK =false;


            }catch(Exception ex){
                Log.e("Error Validar login ", ex.getMessage());

            }
            return loginOK;

        }

        private boolean validarCamposVacios (String email, String password) {
            try {

                String mailError = null;
                Boolean mensaje;

                if (TextUtils.isEmpty(email)) {
                    mailError = login.getResources().getString(R.string.error_campo);
                }

                toggleTextInputLayoutError(login.inputLayoutCorreo, mailError);

                String passError = null;
                String ErrorEmail = null;
                if (TextUtils.isEmpty(password)) {
                    passError = login.getString(R.string.error_campo);
                }
                toggleTextInputLayoutError(login.inputLayoutPassword, passError);

                ValidacionDatos validar = new ValidacionDatos();
                mensaje = validar.validaremail(email);
                if (mensaje) {
                    // Toast.makeText(this, "Email invalido", Toast.LENGTH_SHORT).show();
                    ErrorEmail = getString(R.string.error_invalid_email_di);

                }
                toggleTextInputLayoutError(login.inputLayoutCorreo, ErrorEmail);

            } catch (Exception ex) {
                Log.e("Error Validar login ", ex.getMessage());

            }
            return true;

        }
    private void toggleTextInputLayoutError(@NonNull TextInputLayout textInputLayout,
                                            String msg) {
        textInputLayout.setError(msg);
        if (msg == null) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setErrorEnabled(true);
        }
    }
}
