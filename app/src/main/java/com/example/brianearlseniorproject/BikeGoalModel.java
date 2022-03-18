package com.example.brianearlseniorproject;

public class BikeGoalModel {
    private int bikeGoal_id;
    private String bikeGoal_time;
    private float bikeGoal_distance;

    //constructors
    public BikeGoalModel(int bikeGoal_id, String bikeGoal_time, float bikeGoal_distance) {
        this.bikeGoal_id = bikeGoal_id;
        this.bikeGoal_time = bikeGoal_time;
        this.bikeGoal_distance = bikeGoal_distance;
    }

    //toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return  "Time: " + bikeGoal_time + " (MM:SS)" +
                "\nTotal Distance: " + bikeGoal_distance + " miles";
    }

    //getters and setters
    public int getBikeGoal_id() {
        return bikeGoal_id;
    }

    public void setBikeGoal_id(int bikeGoal_id) {
        this.bikeGoal_id = bikeGoal_id;
    }

    public String getBikeGoal_time() {
        return bikeGoal_time;
    }

    public void setBikeGoal_time(String bikeGoal_time) {
        this.bikeGoal_time = bikeGoal_time;
    }

    public float getBikeGoal_distance() {
        return bikeGoal_distance;
    }

    public void setBikeGoal_distance(float bikeGoal_distance) {
        this.bikeGoal_distance = bikeGoal_distance;
    }
}
