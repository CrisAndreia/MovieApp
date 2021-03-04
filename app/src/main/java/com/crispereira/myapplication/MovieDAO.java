package com.crispereira.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.crispereira.myapplication.Movie;

import java.util.List;

@Dao
public interface MovieDAO {
    @Insert
    void insert(Movie movie);

    @Delete
    void remove(Movie movie);

    @Query("delete from Movie")
    void removeAll();

    @Query("select * from Movie")
    List<Movie> getAll();
}