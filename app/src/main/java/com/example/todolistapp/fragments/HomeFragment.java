package com.example.todolistapp.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.todolistapp.AddNewTaskActivity;
import com.example.todolistapp.DataModel;
import com.example.todolistapp.GetSheetData;
import com.example.todolistapp.MainActivity;
import com.example.todolistapp.R;
import com.example.todolistapp.RecyclerAdapter;
import com.example.todolistapp.SortType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView ;
    RecyclerAdapter recyclerAdapter ;

    Button addNewTask ;

    AutoCompleteTextView sortAutoCompleteTextView;
    ArrayAdapter<String> adapterSortTypes;
    String [] sortTypes = {"Added Date" , "End Date"};


    ArrayList<ArrayList<String>> mainArrayList ;

    LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mainArrayList = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        addNewTask = rootView.findViewById(R.id.buttonAddNewTask);


        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(mainArrayList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();

        addNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext() , AddNewTaskActivity.class);
                startActivity(intent);
            }
        });

        fetchData();

        sortAutoCompleteTextView = rootView.findViewById(R.id.sortAuto_complete_txt);
        adapterSortTypes = new ArrayAdapter<String>(rootView.getContext() , R.layout.menu_item , sortTypes);
        sortAutoCompleteTextView.setAdapter(adapterSortTypes);

        sortAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String type = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(rootView.getContext(), "Item " + type ,Toast.LENGTH_LONG).show();

                if (type.equals("Added Date")){
                    Collections.sort(mainArrayList , SortType.sortByAddedDate);
                    recyclerAdapter.notifyDataSetChanged();
                }else if (type.equals("End Date")){
                    Collections.sort(mainArrayList , SortType.sortByEndDate);
                    recyclerAdapter.notifyDataSetChanged();
                }


            }
        });


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
                        //endDate = 4
                        newArrayList.add(""+response.body().getValues().get(i).get(6));
                        newArrayList.add(""+response.body().getValues().get(i).get(7));
                        newArrayList.add(""+response.body().getValues().get(i).get(8));

                        mainArrayList.add(newArrayList);
                        System.out.println("before change");
                        System.out.println(mainArrayList);

                        recyclerAdapter.notifyDataSetChanged();
                        System.out.println("after change");
                        System.out.println(mainArrayList);

                    }
                    mainArrayList.remove(0);

                    System.out.println("retrofit before sorting");
                    //System.out.println(mainArrayList);

                    for(int i = 0 ; i < mainArrayList.size() ; i++){
                        System.out.println(mainArrayList.get(i));

                    }

                    Collections.sort(mainArrayList, new Comparator<ArrayList<String>>(){

                        @Override
                        public int compare(ArrayList<String> strings, ArrayList<String> t1) {

                            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

                            try {
                                return f.parse(strings.get(4)).compareTo(f.parse(t1.get(4)));
                            } catch (ParseException e) {
                                throw new IllegalArgumentException(e);
                            }

                        }
                    });
                    System.out.println("retrofit After sorting");
                    for(int i = 0 ; i < mainArrayList.size() ; i++){
                        System.out.println(mainArrayList.get(i));
                    }


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