package com.example.todolistapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel {


    @SerializedName("values")
    private List<List<String>> values ;

    public List<List<String>> getValues() {
        return values;
    }

}
