package com.Devbypranjal.skyeapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class MemeFragment extends Fragment {

    private ArrayList<DataHandler> dataHandlers=new ArrayList<>();
    ViewPager2 viewPager2;
    FirebaseDatabase database;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_extras, container, false);
        database = FirebaseDatabase.getInstance();
        viewPager2= view.findViewById(R.id.VideoPagerVideo);
        viewPager2.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        DatabaseReference def = database.getReference("Videos");
        def.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataHandlers.clear();
                for (DataSnapshot s:snapshot.getChildren()){
                    DataHandler dataHandler = s.getValue(DataHandler.class);
                    dataHandlers.add(dataHandler);
                    viewPager2.setAdapter(new PagerAdapter(dataHandlers,getContext()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }
}