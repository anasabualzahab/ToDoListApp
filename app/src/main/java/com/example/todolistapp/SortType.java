package com.example.todolistapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class SortType {


    public static Comparator<ArrayList<String>> sortByEndDate = new Comparator<ArrayList<String>>(){

        @Override
        public int compare(ArrayList<String> strings, ArrayList<String> t1) {
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

            try {
                return f.parse(strings.get(4)).compareTo(f.parse(t1.get(4)));
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }

        }
    };

    public static Comparator<ArrayList<String>> sortByAddedDate = new Comparator<ArrayList<String>>(){

        @Override
        public int compare(ArrayList<String> strings, ArrayList<String> t1) {
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

            try {
                return f.parse(strings.get(0)).compareTo(f.parse(t1.get(0)));
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }

        }
    };
}
