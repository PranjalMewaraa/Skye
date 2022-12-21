package com.Devbypranjal.skyeapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChatListFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    List<ModelChatList> chatListList;
    List<ModelUser> usersList;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    AdapterChatList adapterChatList;
    List<ModelChat> chatList;
    public ChatListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view=inflater.inflate(R.layout.fragment_chat_list, container, false);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        recyclerView=view.findViewById(R.id.chatlistrecycle);
        chatListList=new ArrayList<>();
        chatList=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatListList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ModelChatList modelChatList = ds.getValue(ModelChatList.class);
                    if(!modelChatList.getId().equals(firebaseUser.getUid())) {
                        chatListList.add(modelChatList);
                    }

                }
                loadChats();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;

    }

    private void loadChats() {
        usersList=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    ModelUser user=dataSnapshot1.getValue(ModelUser.class);
                    for (ModelChatList chatList:chatListList){
                        if(user.getUid()!=null && user.getUid().equals(chatList.getId())){
                            usersList.add(user);
                            break;
                        }
                    }
                    adapterChatList=new AdapterChatList(getActivity(),usersList);
                    recyclerView.setAdapter(adapterChatList);
                    for (int i=0; i<usersList.size(); i++){
                        lastMessage(usersList.get(i).getUid());
                        lastmessagetime(usersList.get(i).getUid());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void lastMessage(final String uid) {

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Chats");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lastmess = "default";
                String lastmesstime="";
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    ModelChat chat=dataSnapshot1.getValue(ModelChat.class);
                    if(chat==null){
                        continue;
                    }
                    String sender=chat.getSender();
                    String receiver=chat.getReceiver();
                    if(sender == null || receiver == null){
                        continue;
                    }
                    if(chat.getReceiver().equals(firebaseUser.getUid())&&
                            chat.getSender().equals(uid)||
                            chat.getReceiver().equals(uid)&&
                                    chat.getSender().equals(firebaseUser.getUid())){
                        if(chat.getType().equals("images")){
                            lastmess="Sent a Photo";


                        }
                        else {
                            lastmess=chat.getMessage();
                        }
                    }

                }
                adapterChatList.setlastMessageMap(uid,lastmess);
                adapterChatList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private  void lastmessagetime(final String uid){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Chats");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lastmesstime ="00:00 AM";
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    ModelChat chat=dataSnapshot1.getValue(ModelChat.class);
                    if(chat==null){
                        continue;
                    }
                    String sender=chat.getSender();
                    String receiver=chat.getReceiver();
                    if(sender == null || receiver == null){
                        continue;
                    }
                    if(chat.getReceiver().equals(firebaseUser.getUid())&&
                            chat.getSender().equals(uid)||
                            chat.getReceiver().equals(uid)&&
                                    chat.getSender().equals(firebaseUser.getUid())){
                        if(chat.getType().equals("images")){

                        }
                        else {
                            String timedate=chat.getTimestamp();
                            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                            calendar.setTimeInMillis(Long.parseLong(timedate));
                            lastmesstime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString().substring(lastmesstime.length()-(-2));

                        }
                    }

                }
                adapterChatList.setLastMessagetimeMap(uid,lastmesstime);
                adapterChatList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }







}