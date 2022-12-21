package com.Devbypranjal.skyeapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;
import java.util.PrimitiveIterator;

public class AdapterChatList extends RecyclerView.Adapter<AdapterChatList.Myholder> {

    Context context;
    FirebaseAuth firebaseAuth;
    String uid;

    public AdapterChatList(Context context, List<ModelUser> users) {
        this.context = context;
        this.usersList = users;
        lastMessageMap = new HashMap<>();
        lastmessgaetimemap = new HashMap<>();
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getUid();
    }

    List<ModelUser> usersList;
    private final HashMap<String, String> lastMessageMap;
    private  final HashMap<String,String>lastmessgaetimemap;
    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_chat_list, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, final int position) {

        final String hisuid = usersList.get(position).getUid();
        String userimage = usersList.get(position).getImage();
        String username = usersList.get(position).getName();
        String lastmess = lastMessageMap.get(hisuid);
        String lastmessagemaps = lastmessgaetimemap.get(hisuid);
        holder.name.setText(username);

        // if no last message then Hide the layout
        if (lastmess == null || lastmess.equals("default")) {
            holder.lastmessage.setVisibility(View.GONE);
            holder.lastmessagetime.setVisibility(View.GONE);
        } else {
            holder.lastmessage.setVisibility(View.VISIBLE);
            holder.lastmessagetime.setVisibility(View.VISIBLE);
            holder.lastmessage.setText(lastmess);
            holder.lastmessagetime.setText(lastmessagemaps);
        }
        try {
            // loading profile pic of user
            Glide.with(context).load(userimage).into(holder.profile);
        } catch (Exception e) {
            Log.e("ADIm","NUll");
        }

        // redirecting to chat activity on item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                // putting uid of user in extras
                intent.putExtra("Useruid", hisuid);
                Log.e("uidAdapterchat",""+hisuid);
                context.startActivity(intent);
            }
        });

    }

    // setting last message sent by users.
    public void setlastMessageMap(String userId, String lastmessage) {
        lastMessageMap.put(userId, lastmessage);
    }
    public void setLastMessagetimeMap(String userID,String lastmessagetime){
        lastmessgaetimemap.put(userID,lastmessagetime);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class Myholder extends RecyclerView.ViewHolder {
        ImageView profile, status, block, seen;
        TextView name, lastmessage,lastmessagetime;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profileimage);
            status = itemView.findViewById(R.id.onlinestatus);
            name = itemView.findViewById(R.id.nameonline);
            lastmessage = itemView.findViewById(R.id.lastmessge);
            lastmessagetime =itemView.findViewById(R.id.chatTime);

        }
    }
}
