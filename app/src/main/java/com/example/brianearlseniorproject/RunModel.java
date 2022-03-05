package com.example.brianearlseniorproject;

public class RunModel {
    private int run_id;
    private String run_date;
    private String run_time;
    private float run_distance;
    private float run_speed;

    //constructors
    public RunModel(int run_id, String run_date, String run_time, float run_distance, float run_speed) {
        this.run_id = run_id;
        this.run_date = run_date;
        this.run_time = run_time;
        this.run_distance = run_distance;
        this.run_speed = run_speed;
    }

    //toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return  "Date: " + run_date +
                "        Time: " + run_time + " (MM:SS)" +
                "\nTotal Distance: " + run_distance + " miles " +
                "        Speed: " + run_speed + " mph";
    }

    //getters and setters
    public int getRun_id() {
        return run_id;
    }

    public void setRun_id(int run_id) {
        this.run_id = run_id;
    }

    public String getRun_date() {
        return run_date;
    }

    public void setRun_date(String run_date) {
        this.run_date = run_date;
    }

    public String getRun_time() {
        return run_time;
    }

    public void setRun_time(String run_time) {
        this.run_time = run_time;
    }

    public float getRun_distance() {
        return run_distance;
    }

    public void setRun_distance(float run_distance) {
        this.run_distance = run_distance;
    }

    public float getRun_speed() {
        return run_speed;
    }

    public void setRun_speed(float run_distance) {
        this.run_speed = run_distance;
    }
}
