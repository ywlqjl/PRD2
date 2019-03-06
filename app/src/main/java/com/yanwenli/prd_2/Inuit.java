package com.yanwenli.prd_2;

public class Inuit {
    private int id;
    private String info;
    private String title;
    private String date;
    private String artist;
    private String medium;

    public Inuit(int id, String title, String artist, String date, String medium) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.date = date;
        this.medium = medium;
    }

    public Inuit(int id, String info) {
        this.id = id;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
