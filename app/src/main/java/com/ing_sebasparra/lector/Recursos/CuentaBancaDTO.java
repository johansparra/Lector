package com.ing_sebasparra.lector.Recursos;

public class CuentaBancaDTO {

    private String numIdentifiacion ="";
    private int tipo=0;
    private String numTarjeta="";
    private int cvv=0;
    private String fechaVenci="";

    public String getNumIdentifiacion() {
        return numIdentifiacion;
    }

    public void setNumIdentifiacion(String numIdentifiacion) {
        this.numIdentifiacion = numIdentifiacion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getFechaVenci() {
        return fechaVenci;
    }

    public void setFechaVenci(String fechaVenci) {
        this.fechaVenci = fechaVenci;
    }
}

