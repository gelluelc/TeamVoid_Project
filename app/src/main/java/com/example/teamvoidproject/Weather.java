package com.example.teamvoidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class Weather extends AppCompatActivity implements View.OnClickListener {

    private EditText et;
    private TextView tvtemp, tvhumid, tvwindspeed, tvcountry, comment;

    private Button btn_search,btn_logout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initialization();
        listeners();
    }

    public void initialization(){
        et = findViewById(R.id.edt_email);
        tvtemp = findViewById(R.id.tvtemp);
        tvhumid = findViewById(R.id.tvhumid);
        tvwindspeed = findViewById(R.id.tvwindspeed);
        tvcountry = findViewById(R.id.tvcountry);
        comment = findViewById(R.id.comment);

        //button
        btn_search = findViewById(R.id.btn_search);
        btn_logout = findViewById(R.id.btn_logout);

        mAuth = FirebaseAuth.getInstance();
    }

    public void listeners(){
        btn_search.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                search();
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    public void search(){
        String apikey = "1c41140148ebdd3a5b38055473fffead";
        String city = et.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid=" + apikey;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = response.getJSONObject("main");
                    String temperature = object.getString("temp");
                    Double temp = Double.parseDouble(temperature)-273.15;
                    tvtemp.setText("TEMPERATURE:  " + temp.toString().substring(0,5) + "C");

                    String humidity = object.getString("humidity");
                    Double humid = Double.parseDouble(humidity);
                    tvhumid.setText("HUMIDITY:  " + humid.toString());

                    JSONObject wind = response.getJSONObject("wind");
                    String windspd = wind.getString("speed");
                    Double winds = Double.parseDouble(windspd);
                    tvwindspeed.setText("Wind Speed: " + winds.toString());

                    JSONObject cntry = response.getJSONObject("sys");
                    String country = cntry.getString("country");
                    tvcountry.setText("Country: " + country.toString());

                    if (temp <= 32){
                        comment.setText("Advice:\nIt might rain. Bring an umbrella!");
                    }else if(temp <=22){
                        comment.setText("Advice:\nIt's so Cold bring a Jacket!");
                    }
                    else {
                        comment.setText("Advice:\nWear clothes made from Cotton!");
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Weather.this, "Incorrect City Name,", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    public void logout(){
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Weather.this, MainActivity.class);
            startActivity(intent);
            finish();

    }
}