package com.example.mapas;

import org.json.JSONException;
import org.json.JSONObject;

public class CSesion {
    private JSONObject json;


    public CSesion(String str){
        try {
            json = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public String get_Id(){
        try {
            return json.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String get_Tipo(){
        try {
            return json.getString("tipo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String get_tipo_Vehc(){
        try {
            return json.getString("tipo_vehc");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String get_Usuario(){
        try {
            return json.getString("usuario");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String get_Contrasena(){
        try {
            return json.getString("contrasena");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";

    }
    public String get_Nombre(){
        try {
            return json.getString("nombre");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String get_Correo(){
        try {
            return json.getString("correo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String get_Foto(){
        try {
            return json.getString("foto").replace("localhost", "192.168.0.128:8080");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String get_foto_Vehc(){
        try {
            return json.getString("foto_vehc").replace("localhost", "192.168.0.128:8080");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void set_Id(String id){
        try {
            json.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void set_Tipo(String tipo){
        try {
            json.getString("tipo");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void set_tipo_Vehc(String tipo_vehc){
        try {
            json.getString("tipo_vehc");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void set_Usuario(String usuario){
        try {
            json.getString("usuario");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void set_Contrasena(String contrasena){
        try {
            json.getString("contrasena");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void set_Nombre(String nombre){
        try {
            json.getString("nombre");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void set_Correo(String correo){
        try {
            json.getString("correo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void set_Foto(String foto){
        try {
            json.getString("foto").replace("localhost", "192.168.0.128:8080");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void set_foto_Vehc(String foto_vehc){
        try {
            json.getString("foto_vehc").replace("localhost", "192.168.0.128:8080");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
