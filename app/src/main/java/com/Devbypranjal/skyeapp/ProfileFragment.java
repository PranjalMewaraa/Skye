package com.Devbypranjal.skyeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView avatartv, covertv;
    TextView nam, email, loc;
    Button Eprofile,Logout,showPost;
    RecyclerView postrecycle;
    StorageReference storageReference;
    String storagepath = "Users_Profile_Cover_image/";
    FloatingActionButton fab;

    String uid;
    ProgressDialog pd;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    String[] cameraPermission;
    String[] storagePermission;
    Uri imageuri;
    String image;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        avatartv = view.findViewById(R.id.profileimage1);
        nam = view.findViewById(R.id.ProfileName);
        loc = view.findViewById(R.id.LocProfile);
        email = view.findViewById(R.id.emailProfile);
        uid = FirebaseAuth.getInstance().getUid();
        Eprofile= view.findViewById(R.id.EditProfileButton);
        showPost=view.findViewById(R.id.ShowPosts);
        Logout=view.findViewById(R.id.LogOutButton);

        pd = new ProgressDialog(getActivity());

        pd.setCanceledOnTouchOutside(false);

        // Retrieving user data from firebase
        Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
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
                    email.setText(emaill);
                    loc.setText(Loc);
                    try {
                        Glide.with(getActivity()).load(image).into(avatartv);
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Eprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfilePage.class));
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                onDestroy();
                startActivity(new Intent(getContext(),Welcome.class));
            }
        });
        showPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ShowPostActivity.class));
            }
        });


        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


}