package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.todolistapp.fragments.HomeFragment;
import com.example.todolistapp.fragments.SettingsFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {



    MeowBottomNavigation    bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNav);

        bottomNav.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNav.add(new MeowBottomNavigation.Model(2,R.drawable.ic_settings));

        //bottomNav.setCount(1,"15");

        bottomNav.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                Fragment fragment = null ;
                if(item.getId()==1){
                    fragment = new HomeFragment();
                }else if (item.getId()==2){
                    fragment = new SettingsFragment();

                }
                loadFragment(fragment);
            }
        });

        bottomNav.show(1, true);
        bottomNav.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(MainActivity.this, "you clicked 1", Toast.LENGTH_SHORT).show();
            }
        });

        bottomNav.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(MainActivity.this, "You Released me", Toast.LENGTH_SHORT).show();

            }
        });


        System.out.println("onCreate");
//        System.out.println(arrayList);

    }

    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameee, fragment, null)
                .commit();
    }




}