package com.Devbypranjal.skyeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddShortsActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    EditText caption;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    String[] cameraPermission;
    String filepath = "ShortVideos/";
    String[] storagePermission;
    ProgressDialog pd;
    VideoView video;
    FirebaseStorage storage;
    String name,uid,imageDP,dp;
    private static final int VIDEO_GALLERY_REQUEST = 300;
    private static final int VIDEO_PICKCAMERA_REQUEST = 400;
    DatabaseReference databaseReference;
    Uri videouri = null;
    Button uploadbtn;
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shorts);
        //TODO: create upload reels Activity
        video = findViewById(R.id.VideoUpload);
        caption = findViewById(R.id.VideoCaptionEditText);
        storage=FirebaseStorage.getInstance();
        uploadbtn = findViewById(R.id.VideoUploadbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uid = firebaseAuth.getUid();
        storageReference=storage.getReference(filepath);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());

         query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    name = dataSnapshot1.child("name").getValue().toString();
                    Log.e("NAME",""+name);
                    imageDP = "" + dataSnapshot1.child("image").getValue();
                    dp = "" + dataSnapshot1.child("image").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Initialising camera and storage permission
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,123);
            }
        });
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (caption.getText().toString().isEmpty() && videouri == null) {
                    Toast.makeText(AddShortsActivity.this, "Caption or Video might not be selected", Toast.LENGTH_SHORT).show();
                } else if (caption.getText().toString().isEmpty() || videouri == null) {
                    Toast.makeText(AddShortsActivity.this, "Caption or Video might not be selected", Toast.LENGTH_SHORT).show();
                } else {
                    StorageReference UploadRef = storageReference.child(caption.getText().toString() + ".mp4");
                    UploadRef.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            String downloadUri = uriTask.getResult().toString();
                            if (uriTask.isSuccessful()) {
                                // if task is successful the update the data into firebase
                                HashMap<Object, String> map = new HashMap<>();
                                Log.e("uri", "" + videouri);
                                map.put("caption", caption.getText().toString());
                                map.put("username", name);
                                map.put("uid", uid);
                                map.put("plike", "0");
                                map.put("url", "" + downloadUri);
                                map.put("profilepic", dp);
                                map.put("ptime",""+System.currentTimeMillis());


                                // set the data into firebase and then empty the title ,description and image data
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Videos");
                                databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                startActivity(new Intent(AddShortsActivity.this, DashboardActivity.class));
                                                AddShortsActivity.this.finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pd.dismiss();

                                            }
                                        });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==123 && resultCode ==RESULT_OK){
            if(data!=null){
                videouri  = data.getData();
                Toast.makeText(this, "Selected", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "NotSelected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}