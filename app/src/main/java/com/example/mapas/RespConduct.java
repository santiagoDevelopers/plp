package com.example.mapas;

public class RespConduct {
    String vehcImg, nombre, precio, tiempo, title;

    public RespConduct(String vehcImg, String nombre, String precio, String tiempo, String title) {
        this.vehcImg = vehcImg;
        this.nombre = nombre;
        this.precio = precio;
        this.tiempo = tiempo;
        this.title = title;
    }

    public String getVehcImg() {
        return vehcImg;
    }

    public String getNombr() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public String getTiempo() {
        return tiempo;
    }

    public String getTitle() {
        return title;
    }
}
