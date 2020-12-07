package com.example.mapas;

public class BusquedaReciente {

    String busqueda;
    double latitud, longitud;

    public BusquedaReciente(String busqueda, double latitud, double longitud) {
        this.busqueda = busqueda;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
