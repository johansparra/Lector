package com.ing_sebasparra.lector;

public class Config {
    // URL de nuestro archivo login.php
    // A TODOS LES QUITE EL public
    static final String LOGIN_URL = "https://johansparra1.000webhostapp.com/Registro/logmenu/login.php";

    // Claves para el correo electrónico y la contraseña como se define  $_POST['key'] en login.php
    static final String KEY_EMAIL = "email";
    static final String KEY_PASSWORD = "password";

    // Si la respuesta del servidor es igual a esto, significa que el inicio de sesión es correcto
    static final String LOGIN_SUCCESS = "success";

    // Claves para las preferencias compartidas
    // Este sería el nombre de nuestras preferencias compartidas
    static final String SHARED_PREF_NAME = "myloginapp";

    // Esto sería usado para almacenar el correo electrónico del usuario registrado en el momento
    static final String EMAIL_SHARED_PREF = "email";

    // Usaremos esto para almacenar el booleano en preferencia compartida
    // para rastrear que el usuario ha iniciado sesión o no
    static final String LOGGEDIN_SHARED_PREF = "loggedin";

    /// guardar los otros datos id nombre nivel matricula
    static final String NOMBRE_SHARED_PREF = "name";
    static final String APELLIDOS_SHARED_PREF = "apellido";
    static final String NIVEL_SHARED_PREF = "nivel";
    static final String MATRICULA_SHARED_PREF = "matricula";
    static final String CREDITOS_SHARED_PREF = "creditos";
    static final String ERR_SHARED_PREF = "err";
    static final String SHARED_BEAM = "";

}
//otra prueba