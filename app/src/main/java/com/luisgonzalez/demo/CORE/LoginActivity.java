package com.luisgonzalez.demo.CORE;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.luisgonzalez.demo.MainActivity;
import com.dispersiondigital.smartclaritydemo.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private static String FLAG_CURRENT_VIEW ="login";

    private EditText username, pass;
    private Button actionLogin;
    private String currentPass ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Myapp.getInstance();
        this.username = (EditText) findViewById(R.id.nombre_login);
        this.pass = (EditText)findViewById(R.id.pass_login);
        this.actionLogin = (Button) findViewById(R.id.button_login);
        this.actionLogin.setOnClickListener(this);
        this.actionLogin.setEnabled(false);
        this.pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentPass = "";
                currentPass=Myapp.bin2hex(Myapp.getInstance().getHash(s.toString())).toString().toLowerCase();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (username.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+") && s.length() > 0)
                {
                    actionLogin.setEnabled(true);
                }
                else
                {
                    actionLogin.setEnabled(false);
                }

            }
        });

    }




    public void conectedToEndPint(String user, String psw) throws IOException {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Myapp.getUrlForRequestRestApi()+"mysql/login?user="+user+"&pass="+psw,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getJSONArray("auth").length()>0){

                                Myapp.getInstance().setJsonStringUser(response);
                                MainActivity.show(LoginActivity.this);
                                finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this,"USUARIO NO VALIDO",Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
               /* try {
                    conectedToEndPint(this.username.getText().toString(),currentPass);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                MainActivity.show(LoginActivity.this);
                finish();
                break;
        }
    }

    public static void show(Context context) {
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

}
