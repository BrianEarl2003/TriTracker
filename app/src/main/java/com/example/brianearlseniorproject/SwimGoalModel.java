package com.example.brianearlseniorproject;

public class SwimGoalModel {
    private int swimGoal_id;
    private String swimGoal_time;
    private float swimGoal_distance;

    //constructors
    public SwimGoalModel(int swimGoal_id, String swimGoal_time, float swimGoal_distance) {
        this.swimGoal_id = swimGoal_id;
        this.swimGoal_time = swimGoal_time;
        this.swimGoal_distance = swimGoal_distance;
    }

    //toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return  "Time: " + swimGoal_time + " (MM:SS)" +
                "\nTotal Distance: " + swimGoal_distance + " miles";
    }

    //getters and setters
    public int getSwimGoal_id() {
        return swimGoal_id;
    }

    public void setSwimGoal_id(int swimGoal_id) {
        this.swimGoal_id = swimGoal_id;
    }

    public String getSwimGoal_time() {
        return swimGoal_time;
    }

    public void setSwimGoal_time(String swimGoal_time) {
        this.swimGoal_time = swimGoal_time;
    }

    public float getSwimGoal_distance() {
        return swimGoal_distance;
    }

    public void setSwimGoal_distance(float swimGoal_distance) {
        this.swimGoal_distance = swimGoal_distance;
    }
}
