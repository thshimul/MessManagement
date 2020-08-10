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
import com.ekattorit.messmanagement.session.Store;
import com.ekattorit.messmanagement.session.mySession;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText type,dbname,phone, passwordBox;
    Button loginButton;
    String urlserver = "http://10.0.2.2:8080/MessServer/Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        type = (EditText) findViewById(R.id.typeBox);
        dbname = (EditText) findViewById(R.id.dbName);
        phone = (EditText)findViewById(R.id.phoneBox);
        passwordBox = (EditText)findViewById(R.id.passwordBox);
        loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySession.dbname=dbname.getText().toString();
                mySession.number=phone.getText().toString();
                mySession.password=passwordBox.getText().toString();
                startActivity(new Intent(Login.this,DashBoard.class));
                StringRequest request = new StringRequest(Request.Method.POST, urlserver, new Response.Listener<String>(){

                    @Override
                    public void onResponse(String s) {
                        if(s.contains("null")){
                            Toast.makeText(Login.this, "Incorrect Information", Toast.LENGTH_LONG).show();
                        }else {
                            mySession.user = s.toString().trim();
                            Intent intent = new Intent(Login.this, DashBoard.class);
                            intent.putExtra("check","1");
                            Toast.makeText(Login.this, "Login Successful for" + s, Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Login.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("type", type.getText().toString());
                        parameters.put("dbname", dbname.getText().toString());
                        parameters.put("phone", phone.getText().toString());
                        parameters.put("passwordBox", passwordBox.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(Login.this);
                rQueue.add(request);
            }
        });
    }
}