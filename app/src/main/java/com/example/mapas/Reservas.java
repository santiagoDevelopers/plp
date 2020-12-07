package com.example.mapas;

public class Reservas {

    String imagen, fecha, nombre, origen, destino, hora;

    public Reservas(String imagen, String fecha, String nombre, String origen, String destino, String hora) {
        this.imagen = imagen;
        this.fecha = fecha;
        this.nombre = nombre;
        this.origen = origen;
        this.destino = destino;
        this.hora = hora;
    }

    public String getImagen() {
        return imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getNombre() {
        return nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }
}
