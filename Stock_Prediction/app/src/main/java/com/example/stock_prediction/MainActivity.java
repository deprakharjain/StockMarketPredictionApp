package com.example.stock_prediction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText open,low,high,volume;
    Button predict;
    TextView result;
    String url="https://stockx-prediction.herokuapp.com/predict";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        open=findViewById(R.id.Open);
        low=findViewById(R.id.Low);
        high=findViewById(R.id.High);
        volume=findViewById(R.id.Volume);
        predict=findViewById(R.id.Predict);
        result=findViewById(R.id.Result);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hit the API--> Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data=jsonObject.getString("closing");
                            result.setText(data);
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this,error.getMessage().toString(),Toast.LENGTH_SHORT);
                            }
                        }){

                    @Override
                    protected Map <String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("Open",open.getText().toString());
                        params.put("Low",low.getText().toString());
                        params.put("High",high.getText().toString());
                        params.put("Volume",volume.getText().toString());

                        return params;



                    }
                };

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
            }
        });
    }
}