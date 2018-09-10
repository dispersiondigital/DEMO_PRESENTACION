package com.luisgonzalez.demo.FRAGMENTS;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dispersiondigital.demo.CORE.Myapp;
import com.dispersiondigital.smartclaritydemo.R;

import org.json.JSONException;
import org.json.JSONObject;


public class mRegisterFragment extends Fragment  implements View.OnClickListener{

    private static String FLAG_CURRENT_VIEW =mRegisterFragment.class.getName();

    private EditText nombreUser, apellidoUser, correoUser,passUser;
    private Button buttonRegister;
    public mRegisterFragment() {
        // Required empty public constructor
    }


    public static mRegisterFragment newInstance() {
        mRegisterFragment fragment = new mRegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_m_register, container, false);
        nombreUser = (EditText)rootView.findViewById(R.id.nombre_registro);
        apellidoUser= (EditText)rootView.findViewById(R.id.apellido_registro);
        correoUser = (EditText)rootView.findViewById(R.id.mail_registro);
        passUser = (EditText)rootView.findViewById(R.id.pass_registro);
        buttonRegister = (Button)rootView.findViewById(R.id.button_registro);
        buttonRegister.setEnabled(false);
        buttonRegister.setOnClickListener(this);

        correoUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (correoUser.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+") && s.length() > 0)
                {
                    buttonRegister.setEnabled(true);
                }
                else
                {
                    buttonRegister.setEnabled(false);
                }

            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_registro:
                registerProfileRequest(nombreUser.getText().toString().replace(" ", ""),apellidoUser.getText().toString());
                break;
        }
    }

    private void registerProfileRequest(String name, String lastName){

        Log.d(FLAG_CURRENT_VIEW,"enviando profile: "+ name +" "+lastName);

        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Myapp.getUrlForRequestRestApi()+"mysql/registrar/profile?name="+name.trim()+"&lastname="+lastName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(FLAG_CURRENT_VIEW,"Response is: "+ response);
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("status")){
                                registerUserRequest(correoUser.getText().toString(),passUser.getText().toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(FLAG_CURRENT_VIEW,"Volley error ");
            }
        });
        queue.add(stringRequest);

    }

    private void registerUserRequest(String userName, String userPass){



        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Myapp.getUrlForRequestRestApi()+
                "mysql/registrar/user?user_mail="+userName+"&user_pass="+Myapp.bin2hex(Myapp.getInstance().getHash(userPass)).toString().toLowerCase()
                +"&user_token="+Myapp.bin2hex(Myapp.getInstance().getHash(userName+userPass)).toString().toLowerCase(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(FLAG_CURRENT_VIEW,"Response is: "+ response);
                        Toast.makeText(getActivity(),"registro con exito",Toast.LENGTH_LONG).show();
                        clearForm();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(FLAG_CURRENT_VIEW,"Volley error ");
            }
        });
        queue.add(stringRequest);

    }

    private void clearForm()
    {
        this.nombreUser.setText("");
        this.apellidoUser.setText("");
        this.correoUser.setText("");
        this.passUser.setText("");
        this.buttonRegister.setEnabled(false);
    }


}
