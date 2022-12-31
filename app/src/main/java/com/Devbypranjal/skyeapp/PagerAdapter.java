package com.Devbypranjal.skyeapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.ViewHolder> {

    private ArrayList<DataHandler> dataHandlers;
    Context context;
    private final DatabaseReference liekeref;
    private final DatabaseReference postref;
    boolean mprocesslike = false;
    String myuid;
    String count;

    public PagerAdapter(ArrayList<DataHandler> dataHandlers, Context context) {
        this.dataHandlers = dataHandlers;
        this.context = context;
        liekeref = FirebaseDatabase.getInstance().getReference().child("VLikes");
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.e("UIDPAGEADAPTER",""+myuid);
        postref = FirebaseDatabase.getInstance().getReference().child("Videos");
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videolayout,parent,false);
     return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final String ptime =dataHandlers.get(position).getPtime();
        final String uname = dataHandlers.get(position).getUsername();
        final String image = dataHandlers.get(position).getProfilepic();
        final String caption = dataHandlers.get(position).getCaption();
        holder.username.setText(uname);
        holder.caption.setText(caption);

        try {
            Glide.with(context).load(image).into(holder.imageView);
        } catch (Exception e) {
            Log.d("DP","DP ERROR");
        }
        String uri = dataHandlers.get(position).getUrl();
        holder.Vview.setVideoURI(Uri.parse(uri));
        holder.Vview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                holder.progressBar.setVisibility(View.GONE);
                mediaPlayer.start();
                mediaPlayer.isLooping();
                float VideoRatio = ((float) mediaPlayer.getVideoWidth())/mediaPlayer.getVideoHeight();
                float screenRatio= ((float) holder.Vview.getWidth())/holder.Vview.getHeight();
                float scale=VideoRatio/screenRatio;
                if(scale>1f){
                    Log.e("scalex", "scalex ");
                    holder.Vview.setScaleX(scale);
                }else{
                    holder.Vview.setScaleY((1f/scale));
                    Log.e("scaley", "scaley ");
                }
            }
        });
        holder.Vview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        holder.likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }




    @Override
    public int getItemCount() {
        return dataHandlers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        VideoView Vview;
        ProgressBar progressBar;
        CircleImageView imageView;
        TextView username,caption,likecount;
        ImageView likebtn,sharebtn;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Vview = itemView.findViewById(R.id.videoView);
            progressBar = itemView.findViewById(R.id.progressVideo);
            imageView = itemView.findViewById(R.id.profileimg3);
            username = itemView.findViewById(R.id.usernameVideoPage);
            caption = itemView.findViewById(R.id.videoDescription);
            likebtn = itemView.findViewById(R.id.likebutton);
            sharebtn = itemView.findViewById(R.id.sharebtn);

        }
    }

}
