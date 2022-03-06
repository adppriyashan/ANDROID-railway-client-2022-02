package com.codetek.railwayandroid.Models;

public class Ticket {
    private int id,turn,status;
    private double price;
    private String start,end,date;

    public Ticket(int id, int turn, double price, String start, String end, int status, String date) {
        this.id = id;
        this.turn = turn;
        this.price = price;
        this.start = start;
        this.end = end;
        this.status = status;
        this.date = date;
    }

    public Ticket(int turn, double price, String start, String end, int status, String date) {
        this.turn = turn;
        this.price = price;
        this.start = start;
        this.end = end;
        this.status = status;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getTurn() {
        return turn;
    }

    public double getPrice() {
        return price;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}
