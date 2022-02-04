package com.example.brianearlseniorproject;

public class BikeModel {
    private int bike_id;
    private String bike_date;
    private String bike_time;
    private float bike_distance;

    //constructors
    public BikeModel(int bike_id, String bike_date, String bike_time, float bike_distance) {
        this.bike_id = bike_id;
        this.bike_date = bike_date;
        this.bike_time = bike_time;
        this.bike_distance = bike_distance;
    }

    //toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return  "Date: " + bike_date +
                "        Time: " + bike_time + " (MM:SS)" +
                "\nTotal Distance: " + bike_distance + " miles";
    }

    //getters and setters
    public int getBike_id() {
        return bike_id;
    }

    public void setBike_id(int bike_id) {
        this.bike_id = bike_id;
    }

    public String getBike_date() {
        return bike_date;
    }

    public void setBike_date(String bike_date) {
        this.bike_date = bike_date;
    }

    public String getBike_time() {
        return bike_time;
    }

    public void setBike_time(String bike_time) {
        this.bike_time = bike_time;
    }

    public float getBike_distance() {
        return bike_distance;
    }

    public void setBike_distance(float bike_distance) {
        this.bike_distance = bike_distance;
    }
}
