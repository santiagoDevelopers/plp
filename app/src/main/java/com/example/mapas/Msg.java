package com.example.mapas;

import android.graphics.Bitmap;

public class Msg {




    String image;
    String title, direc, nombre, dire2, fecha;

    public Msg(String image, String title, String direc, String nombre, String dire2, String fecha) {
        this.image = image;
        this.title = title;
        this.direc = direc;
        this.nombre = nombre;
        this.dire2 = dire2;
        this.fecha = fecha;
    }

    void changeTit(String text){
        title="aceptado";
    }
    String getID(){
        return title;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDire2() {
        return dire2;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getFecha() {
        return fecha;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirec() {
        return direc;
    }

    public void setDirec(String direc) {
        this.direc = direc;
    }
}
