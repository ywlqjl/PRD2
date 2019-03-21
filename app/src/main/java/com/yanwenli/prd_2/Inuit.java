package com.yanwenli.prd_2;

public class Inuit {
    private int id;
    private String info;
    private String title;
    private String date;
    private String artist;
    private String medium;

    /**
     * Constructor
     * @param id id of the work
     * @param title title of the work
     * @param artist artist of the work
     * @param date create date of the work
     * @param medium materiel of the work
     */
    public Inuit(int id, String title, String artist, String date, String medium) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.date = date;
        this.medium = medium;
    }

    /**
     * Constructor
     * @param id id of the work
     * @param info information of the work
     */
    public Inuit(int id, String info) {
        this.id = id;
        this.info = info;
    }

    /**
     * Getters and setters
     */

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
