package com.example.fatimajannat.musicapp.Model;



public class Artist {
    private String artistId;
    private String artistName;
    private String artistGenre;

    public Artist() {
    }

    public Artist( String artistId,String artistName, String artistGenre) {
        this.artistName = artistName;
        this.artistGenre = artistGenre;
        this. artistId = artistId;
    }


    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }

    public void setArtistGenre(String artistGenre) {
        this.artistGenre = artistGenre;
    }
}
