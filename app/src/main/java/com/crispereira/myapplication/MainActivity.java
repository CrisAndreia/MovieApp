package com.crispereira.myapplication;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crispereira.myapplication.database.MyDb;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    public MyDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button btBuscar = findViewById(R.id.btSearch);
        btBuscar.setOnClickListener(v -> {
            TextView txtTitle = findViewById(R.id.inputMovie);
            String title = txtTitle.getText().toString();
            TextView result = findViewById(R.id.result);
            String url = "http://www.omdbapi.com/?apikey=e28c429d&t="+title;

            result.setText(url);

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

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
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
                MyDb.class, "database-of-movies")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        if (!db.getMovieDAO().getAll().stream().map(Movie::getTitle).collect(Collectors.toList()).contains(title)) {
            db.getMovieDAO().insert(new Movie(title, year, plot));
            result.setText("Dados salvos no Banco de Dados");
        } else {
            result.setText("Filme já salvo");
        }
    }
}