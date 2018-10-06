package com.ing_sebasparra.lector.Recursos;

public class ValidacionDatos {

    public Boolean validaremail(String email) {
        Boolean mensaje=false;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-zA-Z0-9._-]+";

        if (!email.matches(emailPattern))
            mensaje =true;
        return mensaje;
    }
}
