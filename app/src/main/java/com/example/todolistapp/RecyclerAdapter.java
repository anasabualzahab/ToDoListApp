package com.example.todolistapp;

import android.animation.LayoutTransition;
import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {

//    Context context ;
    //HashMap<String, String > hashMap = new HashMap<>();
    ArrayList<ArrayList<String>> arrayListAdapter = new ArrayList<>();


    public RecyclerAdapter(ArrayList<ArrayList<String>> arrayList) {
//        this.context= c;
        this.arrayListAdapter = arrayList;

        System.out.println("Your Adapter");
        System.out.println(arrayListAdapter);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list ,parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        System.out.println("Your Adapter 2 "  + position);
        System.out.println(arrayListAdapter);

        //[8/16/2022, العميل 5, تغيير سير الطلبات, جميع الطلبات تغيير سير عملهم, 0, 0, 1],
        holder.parentItem.setText(arrayListAdapter.get(position).get(1) );
        holder.childItem.setText(arrayListAdapter.get(position).get(2));
        holder.grandChild.setText(arrayListAdapter.get(position).get(3));
        holder.endDate.setText(arrayListAdapter.get(position).get(4));
    }


    @Override
    public int getItemCount() {
        return arrayListAdapter.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView parentItem, childItem,grandChild,endDate;
        LinearLayout linearLayout , childLayout ;
        CardView    cardView ;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            parentItem = itemView.findViewById(R.id.parentItem);
            childItem = itemView.findViewById(R.id.childItem);
            grandChild = itemView.findViewById(R.id.grandChild);
            endDate = itemView.findViewById(R.id.endDateValueTextView);

            childLayout = itemView.findViewById(R.id.childLayout);
            linearLayout = itemView.findViewById(R.id.layout);

            cardView = itemView.findViewById(R.id.cardView);

            linearLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int v = (childLayout.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

                    TransitionManager.beginDelayedTransition(linearLayout , new AutoTransition());
                    childLayout.setVisibility(v);
                }
            });


        }


    }

}

