package com.example.mapas;

public class Direcc {

    int image;
    String direccion, distancia, title;
    double latitud, longitud;

    public Direcc(int image, String direccion, String distancia, String title, double latitud, double longitud) {
        this.image = image;
        this.direccion = direccion;
        this.distancia = distancia;
        this.title = title;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDistancia() {
        return distancia;
    }
}
