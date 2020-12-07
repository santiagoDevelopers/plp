package com.example.mapas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    private CircularImageView foto_pefil, edit_foto;
    private String imagen="";
    private LinearLayout layout_name, layout_user, layout_pswd,layout_email,layout_phone;
    private TextView name, user, pswd, email, phone;
    private EditText edit_name, edit_user, edit_pswd, edit_email, edit_phone;
    private CircularImageView btn_name, btn_user, btn_pswd, btn_email, btn_phone;
    private Handler handler = new Handler();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).LayoutCond(View.GONE);
        ((MainActivity)getActivity()).topBarColor(getResources().getColor(R.color.dark_white));
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        foto_pefil = view.findViewById(R.id.foto_perfil);
        edit_foto = view.findViewById(R.id.edit_foto);
        layout_name = view.findViewById(R.id.layout_name);
        layout_user = view.findViewById(R.id.layout_user);
        layout_pswd = view.findViewById(R.id.layout_pswd);
        layout_email = view.findViewById(R.id.layout_email);
        layout_phone = view.findViewById(R.id.layout_phone);
        name = view.findViewById(R.id.name);
        user = view.findViewById(R.id.user);
        pswd = view.findViewById(R.id.pswd);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        edit_name = view.findViewById(R.id.edit_name);
        edit_user = view.findViewById(R.id.edit_user);
        edit_pswd = view.findViewById(R.id.edit_pswd);
        edit_email = view.findViewById(R.id.edit_email);
        edit_phone = view.findViewById(R.id.edit_phone);
        btn_name = view.findViewById(R.id.btn_name);
        btn_user = view.findViewById(R.id.btn_user);
        btn_pswd = view.findViewById(R.id.btn_pswd);
        btn_email = view.findViewById(R.id.btn_email);
        btn_phone = view.findViewById(R.id.btn_phone);
        //cargar_Datos();



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideEdit();
            }
        });

        foto_pefil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        edit_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().start(getActivity());
                foto.run();
            }
        });

        layout_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideEdit();
                name.setVisibility(View.GONE);
                edit_name.setVisibility(View.VISIBLE);
                btn_name.setVisibility(View.VISIBLE);

            }
        });
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideEdit();
                user.setVisibility(View.GONE);
                edit_user.setVisibility(View.VISIBLE);
                btn_user.setVisibility(View.VISIBLE);

            }
        });

        layout_pswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideEdit();
                pswd.setVisibility(View.GONE);
                edit_pswd.setVisibility(View.VISIBLE);
                btn_pswd.setVisibility(View.VISIBLE);

            }
        });

        layout_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideEdit();
                email.setVisibility(View.GONE);
                edit_email.setVisibility(View.VISIBLE);
                btn_email.setVisibility(View.VISIBLE);

            }
        });

        layout_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideEdit();
                phone.setVisibility(View.GONE);
                edit_phone.setVisibility(View.VISIBLE);
                btn_phone.setVisibility(View.VISIBLE);


            }
        });


        btn_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar_Nombre()){
                    hideKeyboard(edit_name);
                    edit_name.setVisibility(View.GONE);
                    btn_name.setVisibility(View.GONE);
                    name.setText(edit_name.getText().toString());
                    name.setVisibility(View.VISIBLE);
                    setInfo("nombre", name.getText().toString());
                }
                edit_name.setText("");


            }
        });
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar_Usuario()){
                    hideKeyboard(edit_user);
                    edit_user.setVisibility(View.GONE);
                    btn_user.setVisibility(View.GONE);
                    user.setText(edit_user.getText().toString());
                    user.setVisibility(View.VISIBLE);
                    setInfo("usuario", user.getText().toString());

                }
                edit_user.setText("");
            }
        });

        btn_pswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar_Contrasena()){
                    hideKeyboard(edit_pswd);
                    edit_pswd.setVisibility(View.GONE);
                    btn_pswd.setVisibility(View.GONE);
                    pswd.setText(edit_pswd.getText().toString());
                    pswd.setVisibility(View.VISIBLE);
                    setInfo("contrasena", pswd.getText().toString());

                }
                edit_pswd.setText("");

            }
        });

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar_Correo()){
                    hideKeyboard(edit_email);
                    edit_email.setVisibility(View.GONE);
                    btn_email.setVisibility(View.GONE);
                    email.setText(edit_email.getText().toString());
                    email.setVisibility(View.VISIBLE);
                    setInfo("correo", email.getText().toString());

                }
                edit_email.setText("");

            }
        });

        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        return view;
    }

    private void cargar_Datos(){
        Picasso.get().load(((MainActivity)getActivity()).getFoto()).into(foto_pefil);
        user.setText(((MainActivity)getActivity()).getUser());
        name.setText(((MainActivity)getActivity()).getName());
        pswd.setText(((MainActivity)getActivity()).getPswd());
        email.setText(((MainActivity)getActivity()).getEmail());
        phone.setText(((MainActivity)getActivity()).getPhone());
    }

    private void hideKeyboard(EditText edit){
        InputMethodManager input = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(edit.getWindowToken(), 0);

    }

    private Runnable foto = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 200);
            Uri uri = ((MainActivity)getActivity()).getURI();
            if( uri != null){
                Picasso.get().load(uri).into(foto_pefil);
                ((MainActivity)getActivity()).setURI(null);
                guardar_Imagen(uri);
                setInfo("foto", imagen);
                handler.removeCallbacks(this);

            }
        }
    };

    private void hideEdit(){
        edit_name.setVisibility(View.GONE);
        btn_name.setVisibility(View.GONE);
        name.setVisibility(View.VISIBLE);
        edit_user.setVisibility(View.GONE);
        btn_user.setVisibility(View.GONE);
        user.setVisibility(View.VISIBLE);
        edit_pswd.setVisibility(View.GONE);
        btn_pswd.setVisibility(View.GONE);
        pswd.setVisibility(View.VISIBLE);
        edit_email.setVisibility(View.GONE);
        btn_email.setVisibility(View.GONE);
        email.setVisibility(View.VISIBLE);
        edit_phone.setVisibility(View.GONE);
        btn_phone.setVisibility(View.GONE);
        phone.setVisibility(View.VISIBLE);

    }

    private void setInfo(String keyname, String newdata){
        String url = ((MainActivity)getActivity()).getURL();
        ((MainActivity)getActivity()).setInfo(url+"set_Info.php", keyname, newdata);
    }

    private String get_StringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void guardar_Imagen(Uri uri){
        try {
            Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            int alto = bmp.getHeight();
            int ancho = bmp.getWidth();
            double relacion = alto/(ancho*1.0);
            if (relacion > 1){
                ancho = 720;
                alto = (int) (ancho * relacion);
            } else {
                alto = 720;
                ancho = (int) (alto/relacion);
            }
            bmp = Bitmap.createScaledBitmap(bmp, ancho, alto, true);
            imagen = get_StringImagen(bmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private boolean validar_Nombre(){
        String str = edit_name.getText().toString().trim();
        String permitidos = "[[a-zA-Z\\s][áéíóúÁÉÍÓÚ]]{1,}";
        if (str.isEmpty()){
            edit_name.setError("Rellene el campo");
            return false;
        }else if (!str.matches(permitidos)){
            edit_name.setError("Nombre inválido");
            return false;
        }else{
            edit_name.setError(null);
            return true;
        }
    }
    private boolean validar_Usuario(){
        String str = edit_user.getText().toString().trim();
        String permitidos = "[\\p{Alnum}[@#$%^&+=._/*:!-]]{1,}" ;
        if (str.isEmpty()){
            edit_user.setError("Rellene el campo");
            return false;
        }else if (!str.matches(permitidos)){
            edit_user.setError("Espacio en blanco o caracter inválido detectado");
            return false;
        }else{
            edit_user.setError(null);
            return true;
        }
    }
    private boolean validar_Correo(){
        String str = edit_email.getText().toString().trim();
        if (str.isEmpty()){
            edit_email.setError("Rellene el campo");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(str).matches()){
            edit_email.setError("Correo inválido");
            return false;
        } else {
            edit_email.setError(null);
            return true;
        }
    }
    private boolean validar_Contrasena(){
        String str = edit_pswd.getText().toString().trim();
        String permitidos = "^" +
                //"(?=.*[0-9])" +       //al menos 1 digito
                //"(?=.*[a-z])" +       //al menos 1 minuscula
                //"(?=.*[A-Z])" +       //al menos  1 mayuscula
                "(?=.*[a-zA-Z])" +      //al menos 1 letra
                //"(?=.*[@#$%^&+=])" +    //al menos 1 caracter especial
                "(?=\\S+$)" +           //sin espacios en blanco
                ".{6,}"+                //al menos 6 caracteres
                "$";
        if (str.isEmpty()){
            edit_pswd.setError("Rellene el campo");
            return false;
        }else if (!str.matches(permitidos)){
            edit_pswd.setError("La contraseña debe tener al menos 6 caracteres, entre ellos una letra, y no puede contener espacios en blancos");
            return false;
        } else {
            edit_pswd.setError(null);
            return true;
        }
    }
    private boolean Validar(){
        if (!validar_Nombre() | !validar_Usuario() | !validar_Correo() | !validar_Contrasena()){
            return false;
        }
        return true;
    }
    private boolean validar_Imagen(){
        if (imagen.isEmpty()){
            String msg ="Por favor, elija su imagen de perfil y la de su automóvil";
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private String get_Json(String id){
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("tipo", "usuario");
            json.put("usuario", edit_user.getText().toString());
            json.put("contrasena", edit_pswd.getText().toString());
            json.put("nombre", edit_name.getText().toString().toUpperCase());
            json.put("correo", edit_email.getText().toString());
            json.put("foto", "http://192.168.0.128:8080/cuber//fotos/"+id+".png");
            json.put("foto_vehc", "http://192.168.0.128:8080/cuber//fotos_vehc/"+id+".png");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }




}