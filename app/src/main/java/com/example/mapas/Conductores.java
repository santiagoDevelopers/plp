package com.example.mapas;

public class Conductores {
    private int id;
    private String foto;
    private String nombre;
    private float distancia;
    private String foto_vehc;
    private int valoracion;
    private boolean favorito;
    private String tipo_vehc;


    public Conductores(){}

    public Conductores(int id, String foto, String nombre, float distancia, String foto_vehc, int valoracion, boolean favorito, String tipo_vehc) {
        this.id = id;
        this.foto = foto;
        this.nombre = nombre;
        this.distancia = distancia;
        this.foto_vehc = foto_vehc;
        this.valoracion = valoracion;
        this.favorito = favorito;
        this.tipo_vehc = tipo_vehc;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoto() {
        return foto.replace("localhost", "192.168.0.128:8080");
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public String getFoto_vehc() {
        return foto_vehc.replace("localhost", "192.168.0.128:8080");
    }

    public void setFoto_vehc(String foto_vehc) {
        this.foto_vehc = foto_vehc;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String getTipo_vehc() {
        return tipo_vehc;
    }

    public void setTipo_vehc(String tipo_vehc) {
        this.tipo_vehc = tipo_vehc;
    }
}
