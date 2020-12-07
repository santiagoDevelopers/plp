package com.example.mapas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Cuenta_Usuario extends AppCompatActivity {
    private TextInputLayout nombre, usuario, contrasena, correo;
    private CheckBox terminos;
    private Button crear_cuenta;
    private CircularImageView foto_perfil;
    private String imagen = "";
    private Uri uri;
    private String URL = "http://168.254.1.104/Sitio/crear_cuenta.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta__usuario);
        nombre = findViewById(R.id.nombre);
        usuario = findViewById(R.id.usuario);
        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);
        terminos = findViewById(R.id.terminos);
        crear_cuenta = findViewById(R.id.crear_cuenta);
        foto_perfil = findViewById(R.id.foto_perfil);


        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validar()) {
                    if (validar_Imagen()) {
                        if (aceptar_Terminos()) {
                            Solicitud(URL);
                        }
                    }
                }
            }
        });

    }

    private String get_StringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void guardar_Imagen(Uri uri) {
        try {
            Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            int alto = bmp.getHeight();
            int ancho = bmp.getWidth();
            double relacion = alto / (ancho * 1.0);
            if (relacion > 1) {
                ancho = 720;
                alto = (int) (ancho * relacion);
            } else {
                alto = 720;
                ancho = (int) (alto / relacion);
            }
            bmp = Bitmap.createScaledBitmap(bmp, ancho, alto, true);
            imagen = get_StringImagen(bmp);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void elegir_Imagen(View v) {
        CropImage.activity().start(Cuenta_Usuario.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                foto_perfil.setImageURI(uri);
                //Picasso.get().load(foto_uri).fit().into(foto_perfil);
                guardar_Imagen(uri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception ex = result.getError();
                Toast.makeText(this, "posible error is:" + ex, Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean validar_Nombre() {
        String str = nombre.getEditText().getText().toString().trim();
        int max = nombre.getCounterMaxLength();
        String permitidos = "[a-zA-Z\\s]{1,}";
        if (str.isEmpty()) {
            nombre.setError("Rellene el campo");
            return false;
        } else if (!str.matches(permitidos)) {
            nombre.setError("Nombre inválido");
            return false;
        } else if (nombre.getEditText().length() > max) {
            nombre.setError("Respete el máximo de caracteres");
            return false;
        } else {
            nombre.setError(null);
            nombre.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validar_Usuario() {
        String str = usuario.getEditText().getText().toString().trim();
        int max = usuario.getCounterMaxLength();
        String permitidos = "[\\p{Alnum}[@#$%^&+=._/*:!-]]{1,}";
        if (str.isEmpty()) {
            usuario.setError("Rellene el campo");
            return false;
        } else if (!str.matches(permitidos)) {
            usuario.setError("No se permiten espacios en blanco");
            return false;
        } else if (usuario.getEditText().length() > max) {
            usuario.setError("Respete el máximo de caracteres");
            return false;
        } else {
            usuario.setError(null);
            usuario.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validar_Correo() {
        String str = correo.getEditText().getText().toString().trim();
        int max = correo.getCounterMaxLength();
        if (str.isEmpty()) {
            correo.setError("Rellene el campo");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
            correo.setError("Correo inválido");
            return false;
        } else if (correo.getEditText().length() > max) {
            correo.setError("Respete el máximo de caracteres");
            return false;
        } else {
            correo.setError(null);
            correo.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validar_Contrasena() {
        String str = contrasena.getEditText().getText().toString().trim();
        int max = contrasena.getCounterMaxLength();
        String permitidos = "^" +
                //"(?=.*[0-9])" +       //al menos 1 digito
                //"(?=.*[a-z])" +       //al menos 1 minuscula
                //"(?=.*[A-Z])" +       //al menos  1 mayuscula
                "(?=.*[a-zA-Z])" +      //cualquier letra
                //"(?=.*[@#$%^&+=])" +    //al menos 1 caracter especial
                "(?=\\S+$)" +           //sin espacios en blanco
                ".{6,}" +                //al menos 6 caracteres
                "$";
        if (str.isEmpty()) {
            contrasena.setError("Rellene el campo");
            return false;
        } else if (!str.matches(permitidos)) {
            contrasena.setError("La contraseña debe tener al menos 6 caracteres, entre ellos una letra, y no puede contener espacios en blancos");
            return false;
        } else if (contrasena.getEditText().length() > max) {
            contrasena.setError("Respete el máximo de caracteres");
            return false;
        } else {
            contrasena.setError(null);
            contrasena.setErrorEnabled(false);
            return true;
        }
    }

    private boolean Validar() {
        if (!validar_Nombre() | !validar_Usuario() | !validar_Correo() | !validar_Contrasena()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validar_Imagen() {
        if (imagen.isEmpty()) {
            String msg = "Por favor, elige una imagen de perfil";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private boolean aceptar_Terminos() {
        if (terminos.isChecked()) {
            return true;
        } else {
            String msg = "Tienes que aceptar los terminos para crear tu cuenta";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private String get_Json(String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("tipo", "usuario");
            json.put("tipo_vehc", "NULL");
            json.put("usuario", usuario.getEditText().getText().toString());
            json.put("contrasena", contrasena.getEditText().getText().toString());
            json.put("nombre", nombre.getEditText().getText().toString().toUpperCase());
            json.put("correo", correo.getEditText().getText().toString());
            json.put("foto", "http://168.254.1.104/Sitio//fotos/" + id + ".png");
            json.put("foto_vehc", "NULL");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    private void Solicitud(String URL) {
        StringRequest string_request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.isEmpty()) {
                    usuario.setError("El Usuario ya existe");
                    usuario.getEditText().setText("");
                } else {
                    String json = get_Json(response);
                    Intent intent = new Intent(Cuenta_Usuario.this, Sesion_Usuario.class);
                    intent.putExtra("json", json);
                    startActivity(intent);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("tipo", "usuario");
                parameters.put("tipo_vehc", "NULL");
                parameters.put("usuario", usuario.getEditText().getText().toString());
                parameters.put("contrasena", contrasena.getEditText().getText().toString());
                parameters.put("nombre", nombre.getEditText().getText().toString().toUpperCase());
                parameters.put("correo", correo.getEditText().getText().toString());
                parameters.put("foto", imagen);
                parameters.put("foto_vehc", "NULL");


                return parameters;
            }
        };
        RequestQueue request_queue = Volley.newRequestQueue(this);
        request_queue.add(string_request);
    }

}
