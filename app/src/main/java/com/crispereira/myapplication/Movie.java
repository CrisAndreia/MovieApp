package com.crispereira.myapplication;

public class Movie {

    //{"Title":"Matrix","Year":"1993","Rated":"N/A","Released":"01 Mar 1993","Runtime":"60 min","Genre":"Action, Drama, Fantasy, Thriller","Director":"N/A","Writer":"Grenville Case","Actors":"Nick Mancuso, Phillip Jarrett, Carrie-Anne Moss, John Vernon","Plot":"Steven Matrix is one of the underworld's foremost hitmen until his luck runs out, and someone puts a contract out on him. Shot in the forehead by a .22 pistol, Matrix \"dies\" and finds ...","Language":"English","Country":"Canada","Awards":"1 win.","Poster":"https://m.media-amazon.com/images/M/MV5BYzUzOTA5ZTMtMTdlZS00MmQ5LWFmNjEtMjE5MTczN2RjNjE3XkEyXkFqcGdeQXVyNTc2ODIyMzY@._V1_SX300.jpg","Ratings":[{"Source":"Internet Movie Database","Value":"7.9/10"}],"Metascore":"N/A","imdbRating":"7.9","imdbVotes":"138","imdbID":"tt0106062","Type":"series","totalSeasons":"N/A","Response":

    private String title;
    private String year;
    private String plot;

    public Movie(String title, String year, String t){
        this.title = t;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getTitle(){
        return title;
    }

    public String getYear(){
        return year;
    }

    public String getPlot(){
        return plot;
    }

}
