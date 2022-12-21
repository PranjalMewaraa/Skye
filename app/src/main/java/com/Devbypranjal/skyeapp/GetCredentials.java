package com.Devbypranjal.skyeapp;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GetCredentials {
    String name;
    String imageDP;

    public String getImageDP() {
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance("https://console.firebase.google.com/u/0/project/skye-app-a870a/database/skye-app-a870a-default-rtdb/data/~2F");
        databaseReference = firebaseDatabase.getReference("Users");
        Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    imageDP = "" + dataSnapshot1.child("image").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return imageDP;
    }
}
