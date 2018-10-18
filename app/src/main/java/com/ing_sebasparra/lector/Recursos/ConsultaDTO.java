package com.ing_sebasparra.lector.Recursos;

public class ConsultaDTO {
    private String recarga="";
    private String saldo="";
    private String fecha="";



   /* public ConsultaDTO(String recarga, String saldo, String fecha) {

        this.recarga = recarga;
        this.saldo = saldo;
        this.fecha = fecha;
    }*/
    public String getRecarga() {
        return recarga;
    }

    public void setRecarga(String recarga) {
        this.recarga = recarga;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
