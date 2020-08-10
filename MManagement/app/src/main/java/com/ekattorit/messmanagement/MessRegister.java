package com.ekattorit.messmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ekattorit.messmanagement.session.mySession;

import java.util.HashMap;
import java.util.Map;

public class MessRegister extends AppCompatActivity {

    EditText dbBox,phnBox,pwdBox;
    Button registerButton,cancleButton;
    String urlserver = "http://10.0.2.2:8080/MessServer/CreateDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_register);

        dbBox = (EditText)findViewById(R.id.dbBox);
        phnBox = (EditText)findViewById(R.id.phnBox);
        pwdBox = (EditText)findViewById(R.id.pwdBox);

        registerButton =  (Button)findViewById(R.id.registerButton);
        cancleButton =  (Button)findViewById(R.id.cancleButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, urlserver, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("true")){
                            Toast.makeText(MessRegister.this, "Login Successful"+s, Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(Login.this,Home.class));
                        }
                        else{
                            Toast.makeText(MessRegister.this,s, Toast.LENGTH_LONG).show();
                        }
                        mySession.dbname = dbBox.getText().toString();
                        startActivity(new Intent(MessRegister.this,SetUp.class));
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MessRegister.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("dbBox", dbBox.getText().toString());
                        parameters.put("phnBox", phnBox.getText().toString());
                        parameters.put("pwdBox", pwdBox.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(MessRegister.this);
                rQueue.add(request);
            }
        });


        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessRegister.this,MainActivity.class));
            }
        });
    }
}
