package com.example.brianearlseniorproject;

public class SwimModel {
    private int swim_id;
    private String swim_date;
    private String swim_time;
    private float swim_laps;
    private float swim_lapDistance;
    private float swim_distance;
    private float swim_speed;

    //constructors
    public SwimModel(int swim_id, String swim_date, String swim_time, float swim_laps, float swim_lapDistance, float swim_distance, float swim_speed) {
        this.swim_id = swim_id;
        this.swim_date = swim_date;
        this.swim_time = swim_time;
        this.swim_laps = swim_laps;
        this.swim_lapDistance = swim_lapDistance;
        this.swim_distance = swim_distance;
        this.swim_speed = swim_speed;
    }

    //toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return  "Date: " + swim_date +
                "        Time: " + swim_time + " (MM:SS)" /*+
                "        Laps: " + swim_laps +
                "\nLap Distance: " + swim_lapDistance + " yards" */+
                "     Distance: " + swim_distance + " miles " +
                "     Speed: " + swim_speed + " mph";
    }

    //getters and setters
    public int getSwim_id() {
        return swim_id;
    }

    public void setSwim_id(int swim_id) {
        this.swim_id = swim_id;
    }

    public String getSwim_date() {
        return swim_date;
    }

    public void setSwim_date(String swim_date) {
        this.swim_date = swim_date;
    }

    public String getSwim_time() {
        return swim_time;
    }

    public void setSwim_time(String swim_time) {
        this.swim_time = swim_time;
    }

    public float getSwim_laps() {
        return swim_laps;
    }

    public void setSwim_laps(float swim_laps) {
        this.swim_laps = swim_laps;
    }

    public float getSwim_lapDistance() {
        return swim_lapDistance;
    }

    public void setSwim_lapDistance(float swim_lapDistance) {
        this.swim_lapDistance = swim_lapDistance;
    }

    public float getSwim_distance() {
        return swim_distance;
    }

    public void setSwim_distance(float swim_distance) {
        this.swim_distance = swim_distance;
    }

    public float getSwim_speed() {
        return swim_speed;
    }

    public void setSwim_speed(float swim_speed) {
        this.swim_speed = swim_speed;
    }
}
