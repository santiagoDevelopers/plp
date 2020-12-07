package com.example.mapas;

public class Lug_Fav {

    private int icono;
    private String lugar;
    private int background;


    public Lug_Fav(){}

    public Lug_Fav(int icono, String lugar, int background) {
        this.icono = icono;
        this.lugar = lugar;
        this.background = background;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }
}
