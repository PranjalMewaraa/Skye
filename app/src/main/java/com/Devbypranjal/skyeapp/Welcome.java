package com.Devbypranjal.skyeapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class Welcome extends AppCompatActivity {

    private List<The_Slide_Items_Model_Class> listItems;
    private ViewPager page;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ActionBar actionBar= getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Button button1=findViewById(R.id.SignupButton);
        page = findViewById(R.id.my_pager) ;


        // Make a copy of the slides you'll be presenting.
        listItems = new ArrayList<>() ;
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item1,"Enjoy the vibe of Random\n" +
                "New age Social Media ","With our App you can interact with people of\n" +
                "your kind. Make friends, interact on random threads and socialize like never before."));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item1,"Enjoy the vibe of Random\n" +
                "New age Social Media ","With our App you can interact with people of\n" +
                "your kind. Make friends, interact on random threads and socialize like never before."));
        listItems.add(new The_Slide_Items_Model_Class(R.drawable.item1,"Enjoy the vibe of Random\n" +
                "New age Social Media ","With our App you can interact with people of\n" +
                "your kind. Make friends, interact on random threads and socialize like never before."));



        The_Slide_items_Pager_Adapter itemsPager_adapter = new The_Slide_items_Pager_Adapter(this, listItems);
        page.setAdapter(itemsPager_adapter);

        // The_slide_timer
        java.util.Timer timer = new java.util.Timer();
        The_slide_timer slide_timer =new The_slide_timer();
        timer.scheduleAtFixedRate(slide_timer,2000,3000);
    }

    public void LoginPage(View view) {
        startActivity(new Intent(Welcome.this,LoginActivity.class));
    }
    public void RegisterPage(View view) {
        startActivity(new Intent(Welcome.this,RegistrationActivity.class));
    }
    public class The_slide_timer extends TimerTask {
        @Override
        public void run() {

            Welcome.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (page.getCurrentItem()< listItems.size()-1) {
                        page.setCurrentItem(page.getCurrentItem()+1);
                    }
                    else
                        page.setCurrentItem(0);
                }
            });
        }
    }
}