package com.example.mapas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.hsalf.smileyrating.SmileyRating;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    private TextInputLayout usuario, contrasena;
    private Button iniciar_sesion;
    private Button crear_cuenta;
    private String Url = "http://168.254.1.104/Sitio/iniciar_sesion.php";
    TextView tv1, tv2, tv3;
    String tt;

    String texto;
    int markadores;

    String[] Coords = new String[40];


    int cont = 1;
    int y;
    int h;
    String s2;
    Handler handler = new Handler();

    boolean flag = false;
    int vuelta = 0;
    SmileyRating smileyRating;

    LinearLayout l1;
    ImageView m;

    int way = 0;
    boolean flag2 = true;
    float sis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = findViewById(R.id.usuario);
        contrasena = findViewById(R.id.contrasena);
        iniciar_sesion = findViewById(R.id.iniciar_sesion);
        crear_cuenta = findViewById(R.id.crear_cuenta);
        tv1 = findViewById(R.id.texto);
        tv2 = findViewById(R.id.texto2);
        smileyRating = findViewById(R.id.smile);
        l1 = findViewById(R.id.base);
        m = findViewById(R.id.motito);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        //tv2.setText(remplazo(" Santiago de Cuba", " ", "+"));
        smileyRating.setTitle(SmileyRating.Type.GREAT, "Excelente");
        smileyRating.setTitle(SmileyRating.Type.GOOD, "Bien");
        smileyRating.setTitle(SmileyRating.Type.OKAY, "Aceptable");
        smileyRating.setTitle(SmileyRating.Type.BAD, "Malo");
        smileyRating.setTitle(SmileyRating.Type.TERRIBLE, "Terrible");



        Vibrator vibrator=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);

        //Toast.makeText(getApplicationContext(), String.valueOf(displayMetrics.widthPixels) + " " + String.valueOf(displayMetrics.heightPixels), Toast.LENGTH_SHORT).show();


        /*Resources resources = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35f, resources.getDisplayMetrics());
        float b = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 21f, resources.getDisplayMetrics());
        sis = displayMetrics.widthPixels;
        sis = sis - px - b;
        Toast.makeText(getApplicationContext(), String.valueOf(sis), Toast.LENGTH_SHORT).show();

        double total = 3;

        final double[] actual = {0};
        final float[] move = new float[1];


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (flag2) {
                    actual[0] += 0.6;
                    move[0] = (float) (((actual[0]) * sis) / total);
                }
                Toast.makeText(getApplicationContext(), String.valueOf(move), Toast.LENGTH_SHORT).show();
                if (move[0] < sis) {

                    m.setTranslationX(move[0]);
                } else
                    flag2 = false;

                handler.postDelayed(this, 100);

            }
        }, 1000);*/


        iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                /*if (Validar()) {
                    Solicitud(Url);

                }*/
            }
        });

        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, SConductor.class);
                startActivity(intent);
            }
        });

    }

    public boolean isNumber(String in) {
        String perimitidos = "-+1234567890.,";
        for (int i = 0; i < in.length(); i++)
            if (perimitidos.indexOf(in.substring(i, i + 1)) == -1) {
                return false;
            }

        return true;
    }

    public void extraerInfo(String texto, String char1, String char2) {
        if (vuelta > 0) {
            y = texto.indexOf("%");
            s2 = texto.substring(0, y);
            Coords[cont * 2 + 1] = s2;
            cont++;
            texto = texto.substring(y + 3);
        }
        int h = texto.indexOf(char1);
        String s1 = texto.substring(0, h);
        texto = texto.substring(h + 3);
        h = texto.indexOf(char2);
        s2 = texto.substring(0, h);
        Coords[cont * 2] = s1;
        if (!isNumber(s2)) {
            vuelta++;
            extraerInfo(texto, "%", "&");

        } else {
            Coords[cont * 2 + 1] = s2;
        }
    }

    public String remplazo(String entrada, String Char, String newChar) {
        String salida = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.substring(i, i + 1).indexOf(Char) == -1) {
                salida = salida + entrada.substring(i, i + 1);
            } else
                salida = salida + newChar;
        }
        return salida;
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
        if (!validar_Usuario() | !validar_Contrasena()) {
            return false;
        } else {
            return true;
        }
    }

    private String get_Tipo(String str) {
        JSONObject json = null;
        try {
            json = new JSONObject(str);
            return json.getString("tipo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";

    }

    private void Solicitud(String URL) {
        StringRequest string_request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.isEmpty()) {
                    usuario.setError("Nombre de usuario incorrecto");
                    usuario.getEditText().setText("");
                    contrasena.getEditText().setText("");


                } else if (response.contentEquals(usuario.getEditText().getText().toString())) {
                    contrasena.setError("Contraseña incorrecta");
                    contrasena.getEditText().setText("");
                } else {

                    if (get_Tipo(response).contentEquals("usuario")) {
                        Intent intent = new Intent(MainActivity2.this, Sesion_Usuario.class);
                        intent.putExtra("json", response);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity2.this, Sesion_Conductor.class);
                        intent.putExtra("json", response);
                        startActivity(intent);
                    }
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
                parameters.put("usuario", usuario.getEditText().getText().toString());
                parameters.put("contrasena", contrasena.getEditText().getText().toString());
                return parameters;
            }
        };
        RequestQueue request_queue = Volley.newRequestQueue(this);
        request_queue.add(string_request);
    }
}