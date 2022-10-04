package com.example.todolistapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetSheetData {

    @GET("v4/spreadsheets/1Z9f7wRKf0MrmMfDZIJjoxws2DQw20LIxKTN_uED2nDc/values/tasks?key=AIzaSyC-Mdd1eOKjSHJQ3w_UQ6sCGjCH-_haKME")
    Call<DataModel> getModelClass();


}
