package com.ekattorit.messmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageMess extends AppCompatActivity {

    int memberNumber;
    Button submit;
    ArrayList<memberModel> memberList;
    String urlserver = "http://10.0.2.2:8080/MessServer/InsertAllMembers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_mess);

        memberNumber = Integer.parseInt(getIntent().getStringExtra("memberNumber"));
        Toast.makeText(ManageMess.this, "Total member :"+memberNumber, Toast.LENGTH_LONG).show();

        submit = (Button)findViewById(R.id.submit);

        final ListView itemsListView  = (ListView)findViewById(R.id.list);
        CustomListAdapter adapter = new CustomListAdapter(this, generateItemsList());
        itemsListView.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                memberList = new ArrayList<>();

                for(int i = 0 ;i<memberNumber;i++){
                    View view = itemsListView.getChildAt(i);
                    EditText nametext = (EditText)view.findViewById(R.id.text_view_item_name);
                    EditText phonetext = (EditText)view.findViewById(R.id.text_view_item_description);
                    String name = nametext.getText().toString();
                    String number = phonetext.getText().toString();
                    memberList.add(new memberModel(name,number));
                }
                Store.list=memberList;
                for(int i = 0 ;i<memberNumber;i++){
                    //Toast.makeText(ManageMess.this,memberList.get(i).getName(), Toast.LENGTH_LONG).show();
                }

                StringRequest request = new StringRequest(Request.Method.POST, urlserver, new Response.Listener<String>(){

                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(ManageMess.this,s, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ManageMess.this,Login.class));
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(ManageMess.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("len",String.valueOf(memberList.size()));
                        parameters.put("db", mySession.dbname);
                        memberModel model;
                        for(int i=0;i<memberNumber;i++){
                            parameters.put("name"+i,memberList.get(i).getName());
                            parameters.put("number"+i,memberList.get(i).getNumber());
                        }

                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(ManageMess.this);
                rQueue.add(request);

            }
        });
    }

    private ArrayList<Item> generateItemsList() {
        ArrayList<Item> list = new ArrayList<>();

        for (int i=0;i<memberNumber;i++){

            list.add(new Item("name"));
        }

        return list;
    }
}
