package com.example.todolistapp;

import java.util.HashMap;

public class ItemsModel {

    private String addedDate ;
    private String agent ;
    private String title ;
    private String description ;
    private String endDate ;
    private String type ;
    private String needTwoDepartments ;

    private HashMap<String, String > items = new HashMap<String, String >();


    public ItemsModel(HashMap<String, String> itemsss) {

        this.items = itemsss;
    }

    public String getAddedDate() {
        return items.get("AddedDate");
    }


    public String getAgent() {
        return items.get("Agent");
    }

    public String getTitle() {
        return items.get("Title");
    }

    public String getDescription() {
        return items.get("Description");
    }

    public String getEndDate() {
        return items.get("EndDate");
    }

    public String getType() {
        return items.get("Type");
    }

    public String getNeedTwoDepartments() {
        return items.get("NeedTwoDepartments");
    }
}
