package com.Devbypranjal.skyeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class OtherProfileActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    TextView nam,loc,mail;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String Useruid,image;

    ProgressDialog pd;
    ImageView profilepic;
    Button postbutton, messagebutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        profilepic=findViewById(R.id.profileimageOther);
        loc=findViewById(R.id.LocProfileOther);
        nam=findViewById(R.id.ProfileNameOther);
        mail=findViewById(R.id.emailProfileOther);
        postbutton=findViewById(R.id.OtherShowPost);
        Useruid  = getIntent().getStringExtra("uid");
        messagebutton=findViewById(R.id.OtherMessage);
        pd = new ProgressDialog(OtherProfileActivity.this);
        pd.setCanceledOnTouchOutside(false);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        Query query = databaseReference.orderByChild("uid").equalTo(Useruid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String name = "" + dataSnapshot1.child("name").getValue();
                    String emaill = "" + dataSnapshot1.child("email").getValue();
                    String Loc =""+dataSnapshot1.child("city").getValue()+", "+dataSnapshot1.child("country").getValue();
                    if(dataSnapshot1.child("image").getValue()==null){
                        image = String.valueOf(R.drawable.user);
                    }else {
                        image = "" + dataSnapshot1.child("image").getValue();
                    }
                    nam.setText(name);
                    mail.setText(emaill);
                    loc.setText(Loc);
                    try {
                        Glide.with(OtherProfileActivity.this).load(image).into(profilepic);
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        messagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherProfileActivity.this,ChatActivity.class);
                intent.putExtra("Useruid",Useruid);
                startActivity(intent);
            }
        });
        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherProfileActivity.this,OtherPostActivity.class);
                intent.putExtra("Useruid",Useruid);
                startActivity(intent);
            }
        });

    }
    public void Wave(View view) {
    //TODO: Create a wave notification
    }

}