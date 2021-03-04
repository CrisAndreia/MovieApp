package com.crispereira.myapplication.database;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.crispereira.myapplication.Movie;
import com.crispereira.myapplication.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.crispereira.myapplication.Movie;
import com.crispereira.myapplication.MovieDAO;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)

public abstract class MyDb extends RoomDatabase {
    public abstract MovieDAO getMovieDAO();
}
