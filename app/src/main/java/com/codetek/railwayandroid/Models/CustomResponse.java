package com.codetek.railwayandroid.Models;

public class CustomResponse {

    private int code;
    private String body;

    public CustomResponse(int code, String body) {
        this.code = code;
        this.body = body;
    }

    public String body(){
        return this.body;
    }

    public int code(){
        return this.code;
    }
}
