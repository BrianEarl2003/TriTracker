package com.example.brianearlseniorproject;

public class RunGoalModel {
    private int runGoal_id;
    private String runGoal_time;
    private float runGoal_distance;

    //constructors
    public RunGoalModel(int runGoal_id, String runGoal_time, float runGoal_distance) {
        this.runGoal_id = runGoal_id;
        this.runGoal_time = runGoal_time;
        this.runGoal_distance = runGoal_distance;
    }

    //toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return  "Time: " + runGoal_time + " (MM:SS)" +
                "\nTotal Distance: " + runGoal_distance + " miles";
    }

    //getters and setters
    public int getRunGoal_id() {
        return runGoal_id;
    }

    public void setRunGoal_id(int runGoal_id) {
        this.runGoal_id = runGoal_id;
    }

    public String getRunGoal_time() {
        return runGoal_time;
    }

    public void setRunGoal_time(String runGoal_time) {
        this.runGoal_time = runGoal_time;
    }

    public float getRunGoal_distance() {
        return runGoal_distance;
    }

    public void setRunGoal_distance(float runGoal_distance) {
        this.runGoal_distance = runGoal_distance;
    }
}
