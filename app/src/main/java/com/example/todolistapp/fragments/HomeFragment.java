package com.example.todolistapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.todolistapp.AddNewTaskActivity;
import com.example.todolistapp.DataModel;
import com.example.todolistapp.GetSheetData;
import com.example.todolistapp.MainActivity;
import com.example.todolistapp.R;
import com.example.todolistapp.RecyclerAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView ;
    RecyclerAdapter recyclerAdapter ;

    Button addNewTask ;

    ArrayList<ArrayList<String>> arrayList ;

    LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        arrayList = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        addNewTask = rootView.findViewById(R.id.buttonAddNewTask);


        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(recyclerAdapter);

        addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext() , AddNewTaskActivity.class);
                startActivity(intent);
            }
        });

        fetchData();


        // Inflate the layout for this fragment
        return rootView;


    }


    private void fetchData(){

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://sheets.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetSheetData retrofitApi = retrofit.create(GetSheetData.class);

        Call<DataModel> call = retrofitApi.getModelClass();


        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {

                if (response.isSuccessful()){

                    System.out.println("Hello Anas, this is your response");
                    System.out.println(response.body().getValues());

                    for ( int i = 0 ; i < response.body().getValues().size() ; i ++){
                        System.out.println("response.body()");
                        System.out.println(response.body().getValues().get(i));

                        ArrayList<String> newArrayList = new ArrayList<>();

                        newArrayList.add(""+response.body().getValues().get(i).get(1));
                        newArrayList.add(""+response.body().getValues().get(i).get(3));
                        newArrayList.add(""+response.body().getValues().get(i).get(4));
                        newArrayList.add(""+response.body().getValues().get(i).get(5));
                        newArrayList.add(""+response.body().getValues().get(i).get(6));
                        newArrayList.add(""+response.body().getValues().get(i).get(7));
                        newArrayList.add(""+response.body().getValues().get(i).get(8));

                        arrayList.add(newArrayList);
                        System.out.println("before change");
                        System.out.println(arrayList);

                        recyclerAdapter.notifyDataSetChanged();
                        System.out.println("after change");
                        System.out.println(arrayList);



                    }
                    arrayList.remove(0);

                    System.out.println("retrofit");
                    System.out.println(arrayList);


                }else {
                    System.out.println("response not Successful" );

                }
            }
            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

                System.out.println("Hello Anas, this is your response");
                System.out.println("something went wrong");
                System.out.println(t.getMessage());

//                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}