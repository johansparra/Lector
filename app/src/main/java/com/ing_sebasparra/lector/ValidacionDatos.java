package com.ing_sebasparra.lector;

public class ValidacionDatos {

    public Boolean validaremail(String email) {
        Boolean mensaje=false;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern))
            mensaje =true;
        return mensaje;
    }
}
