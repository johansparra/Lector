package com.ing_sebasparra.lector.Recursos;

public class Config {
    //   static final String LOGIN_URL = "https://johansparra1.000webhostapp.com/Registro/logmenu/login.php";

    // Claves para las preferencias compartidas
    // Este sería el nombre de nuestras preferencias compartidas
    public final String SHARED_PREF_NAME = "myloginapp";

    // Esto sería usado para almacenar el correo electrónico del usuario registrado en el momento
    public final String EMAIL_SHARED_PREF = "email";

    // Usaremos esto para almacenar el booleano en preferencia compartida
    // para rastrear que el usuario ha iniciado sesión o no
    public final String LOGGEDIN_SHARED_PREF = "loggedin";


    public final String SHARED_PREF_CONSULTA = "valor";
    public final String CONSULTA_SHARED_PREF = "consulta";
    public final String CEDULA_SHARED_PREF = "cedula";
    public final String ID_USUARIO_SHARED_PREF = "idusuario";
    public final String N_IDENTIFICACION_SHARED_PREF = "n_identificacion";
    public final String NOMBRE_SHARED_PREF = "nombre";
    public final String TIPO_SHARED_PREF = "tipo";


    //TARJETA DE CREDITO
    public final String SHARED_PREF_TARJETA = "tarjeta";
    public final String TARJETA_SHARED_PREF = "guardada";
    public final String TARJETA_CVV = "cvv";
    public final String TARJETA_NOMBRE = "nombre";
    public final String TARJETA_EXPIRA = "fecha";
    public final String TARJETA_NUMERO = "numero";

    //MENSAJES CUADRO DE DIALOGO
    public final String RECARGA_TARJETA = "Recarga Exitosa";
    public final String TITILO_AVISO_1 = "Aviso";
    public final String TITILO_AVISO_2 = "Conexión";
    public final String ALERT_LOGIN_INCORR = "Correo o password incorrectos";
    public final String ALERT_REGISTER= "Usuario registrado correctamente";
    public final String ALERT_TARJETA_INCORRECT= "Datos del cliente no existe";
    public final String ALERT_SALDO_INSU= "Saldo insuficiente para realizar la recarga";
    public final String ALERT_NFC_NOT= "El dispositivo movil NO dispone de hardware NFC";
    public final String ALERT_NOT_WIFI_MOVILE= "Error de conexión No Hay internet!";



}
