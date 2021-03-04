package com.crispereira.myapplication;

import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.room.Room;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crispereira.myapplication.database.MyDb;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    public MyDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button btBuscar = findViewById(R.id.btSearch);
        btBuscar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView txtTitle = findViewById(R.id.inputMovie);
                String title = txtTitle.getText().toString();
                TextView result = findViewById(R.id.result);
                String url = "http://www.omdbapi.com/?apikey=e28c429d&t="+title;

                try {
                    url = URLEncoder.encode(url, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                result.setText(url);

                /*try{
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                    }
                    else{
                        result.setText("Connected!");
                    }
                } catch (Exception e){
                    result.setText("Not able to connect!");
                }*/

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        response -> {
                            try{
                                JSONObject responseJSON = new JSONObject(response);
                                result.setText(responseJSON.getString("Plot"));

                                String stitle = (responseJSON.getString("Title"));
                                String year = responseJSON.getString("Year");
                                String plot = (responseJSON.getString("Plot"));
                                saveOnMyDb(stitle, year, plot);
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            result.setText("nao funciona no json");
                        });

                /*

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                result.setText("Response is: "+ response.substring(0,500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        result.setText("nao ta funcionando no volley a url recebida: "+url);
                    }
                });*/

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TextView txtTitle = findViewById(R.id.inputMovie);
                    String title = txtTitle.getText().toString();
                    TextView result = findViewById(R.id.result);

                    try {
                        Movie movie = ClientMovie.returnMovie(title);
                        result.setText(movie.getPlot());
                    } catch (Exception e){
                        result.setText("That dindn't work");
                    }
                } else {
                    TextView result = findViewById(R.id.result);
                    result.setText("Permission denied!");
                }
                return;
            }
        }
    }

    public void saveOnMyDb(String title, String year, String plot){
        TextView result = findViewById(R.id.result);

        db = Room.databaseBuilder(getApplicationContext(),
                MyDb.class, "database-of-movies").build();
        db.getMovieDAO().insert(new Movie(title, year, plot));

        result.setText("Dados salvos no Banco de Dados");

    }
}