package com.example.mapas;

import android.graphics.Bitmap;

public class Cardf {



   String image, vehc;
    String title, name, dir, tiempo;


    public Cardf(String image, String vehc, String title, String name, String dir, String tiempo) {
        this.image = image;
        this.vehc = vehc;
        this.title = title;
        this.name = name;
        this.dir = dir;
        this.tiempo = tiempo;
    }

    public String getTiempo() {
        return tiempo;
    }

    public String getImage() {
        return image;
    }

    public String getVehc() {
        return vehc;
    }

    public String getName() {
        return name;
    }

    public String getDir() {
        return dir;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    void changeTit(String text){
        title=text;

    }

    public String getTitle() {
        return title;
    }
}
