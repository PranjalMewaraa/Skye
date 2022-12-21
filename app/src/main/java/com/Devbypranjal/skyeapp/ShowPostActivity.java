package com.Devbypranjal.skyeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowPostActivity extends AppCompatActivity {
    RecyclerView postrecycle;
    List<ModelPost> posts;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AdapterPost adapterPosts;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);
        postrecycle = findViewById(R.id.RecyclerProfile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        uid = FirebaseAuth.getInstance().getUid();
        posts = new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("All Posts");
        loadMyPosts();
    }
    private void loadMyPosts() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShowPostActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        postrecycle.setLayoutManager(layoutManager);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = databaseReference.orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelPost modelPost = dataSnapshot1.getValue(ModelPost.class);
                    posts.add(modelPost);
                    adapterPosts = new AdapterPost(ShowPostActivity.this, posts);
                    postrecycle.setAdapter(adapterPosts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ShowPostActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}