package com.Devbypranjal.skyeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        actionBar= getSupportActionBar();
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        if (actionBar != null) {
            actionBar.setTitle(Html.fromHtml("<font color='#00000' style='font-size:30px'>Feed</font>"));
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.background));
            actionBar.setCustomView(R.layout.abs_layout);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setLogo(R.drawable.background);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.isHideOnContentScrollEnabled();

        }
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment, "");
        fragmentTransaction.commit();
    }
    private final BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.nav_home:
                    actionBar.setTitle(Html.fromHtml("<font color='#00000' style='font-size:30px'>Feed</font>"));
                    HomeFragment fragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment, "");
                    fragmentTransaction.commit();
                    return true;

                case R.id.nav_profile:
                    actionBar.setTitle(Html.fromHtml("<font color='#00000' style='font-size:30px'>Profile</font>"));
                    ProfileFragment fragment1 = new ProfileFragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content, fragment1);
                    fragmentTransaction1.commit();
                    return true;

                case R.id.nav_users:
                    actionBar.setTitle(Html.fromHtml("<font color='#00000' style='font-size:30px'>Search</font>"));
                    UserFragment fragment2 = new UserFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.content, fragment2, "");
                    fragmentTransaction2.commit();
                    return true;

                case R.id.nav_chat:
                    actionBar.setTitle(Html.fromHtml("<font color='#00000' style='font-size:30px'>Chats</font>"));
                    ChatListFragment listFragment = new ChatListFragment();
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.content, listFragment, "");
                    fragmentTransaction3.commit();
                    return true;

                case R.id.nav_addblogs:
                    actionBar.setTitle(Html.fromHtml("<font color='#00000' style='font-size:30px'>Add Posts</font>"));
                    AddblogFragment fragment4 = new AddblogFragment();
                    FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(R.id.content, fragment4, "g");
                    fragmentTransaction4.commit();
                    return true;
            }
            return false;
        }
    };


}