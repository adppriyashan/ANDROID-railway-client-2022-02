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
}
