package com.codetek.railwayandroid.Models;

import java.util.ArrayList;

public class Train {
    private int id;
    private String alias;
    private int start;
    private int end;
    private int seatsperbox;
    private int windowed;
    private int nonwindowed;
    private int firstclass;
    private int secondclass;
    private int thirdclass;
    private double class1price,class2price,class3price;
    private ArrayList<Integer> booked;

    private ArrayList<Schedule> schedules;

    public int getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getSeatsperbox() {
        return seatsperbox;
    }

    public int getWindowed() {
        return windowed;
    }

    public int getNonwindowed() {
        return nonwindowed;
    }

    public int getFirstclass() {
        return firstclass;
    }

    public int getSecondclass() {
        return secondclass;
    }

    public int getThirdclass() {
        return thirdclass;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public double getClass1price() {
        return class1price;
    }

    public double getClass2price() {
        return class2price;
    }

    public double getClass3price() {
        return class3price;
    }

    public ArrayList<Integer> getBooked() {
        return booked;
    }
}
