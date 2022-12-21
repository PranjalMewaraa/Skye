package com.Devbypranjal.skyeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    private EditText Username,Email,Password,City,Country,DOB,RecoveryEmail,Gender;
    private ProgressDialog progressDialog;
    ImageView img;
    Button ImgUpload;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar= getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Username = findViewById(R.id.userLogininput1);
        Email = findViewById(R.id.EmaInputLogin);
        Password =findViewById(R.id.PInputLogin);
        City=findViewById(R.id.CitInputLogin);
        Country=findViewById(R.id.ContInputLogin);
        RecoveryEmail=findViewById(R.id.REmaInputLogin);
        Gender=findViewById(R.id.GInputLogin);
        DOB=findViewById(R.id.DInputLogin);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Register");
    }

    public void CreateAccxx(View view) {
        String emaill = Email.getText().toString().trim();
        String uname = Username.getText().toString().trim();
        String City1 = City.getText().toString().trim();
        String recEmail = RecoveryEmail.getText().toString().trim();
        String Country1 = Country.getText().toString().trim();
        String Gender1 = Gender.getText().toString().trim();
        String DOB1 = DOB.getText().toString().trim();
        String pass = Password.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()) {
            Email.setError("Invalid Email");
            Email.setFocusable(true);
        } else if (pass.length() < 6) {
            Password.setError("Length Must be greater than 6 character");
            Password.setFocusable(true);
        } else {
            registerUser(emaill, pass, uname,City1,Country1,Gender1,DOB1,recEmail);
        }
    }

    private void registerUser(String emaill, String pass, String uname, String city1, String country1, String Gneder1, String Dob1,String RecEmail) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(emaill, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    FirebaseUser user = mAuth.getCurrentUser();
                    String email = emaill;
                    String uid = user.getUid();
                    HashMap<Object, String> hashMap = new HashMap<>();
                    hashMap.put("email", email);
                    hashMap.put("uid", uid);
                    hashMap.put("name", uname);
                    hashMap.put("city",city1);
                    hashMap.put("country",country1);
                    hashMap.put("Gender",Gneder1);
                    hashMap.put("DOB",Dob1);
                    hashMap.put("DOB",RecEmail);
                    hashMap.put("onlineStatus", "online");
                    hashMap.put("typingTo", "noOne");
                    hashMap.put("image", "");
                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://skye-app-a870a-default-rtdb.firebaseio.com/");
                    DatabaseReference reference = database.getReference("Users");
                    reference.child(uid).setValue(hashMap);
                    Toast.makeText(RegistrationActivity.this, "Registered User " + user.getEmail(), Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(RegistrationActivity.this, Add_Profile_Image.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                } else {
                    progressDialog.dismiss();
                    Log.e("Register","Registered but not to db");
                    Toast.makeText(RegistrationActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.e("Register","not Registered but not to db");
                Toast.makeText(RegistrationActivity.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

}
