package com.codetek.railwayandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codetek.railwayandroid.Models.CustomUtils;
import com.codetek.railwayandroid.Models.Ticket;
import com.codetek.railwayandroid.RecyclerViews.TicketRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    TextView name,email,dashboardEmptyText;
    ImageView logout;
    boolean doubleBackToExitPressedOnce;
    RecyclerView ticketsListView;
    TicketRecyclerAdapter ticketRecyclerAdapter;
    FloatingActionButton newBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initProcess();
    }

    private void initProcess() {
        name=findViewById(R.id.dashboard_name);
        email=findViewById(R.id.dashboard_email);
        dashboardEmptyText=findViewById(R.id.dashboard_empty);
        logout=findViewById(R.id.dashboard_logout);
        newBooking=findViewById(R.id.dashboard_new_booking);
        doubleBackToExitPressedOnce = false;

        if(CustomUtils.tickets.size()>0){
            dashboardEmptyText.setVisibility(View.INVISIBLE);
        }

        ticketsListView=findViewById(R.id.dashboard_recycler_view);
        ticketRecyclerAdapter=new TicketRecyclerAdapter(CustomUtils.tickets);

        ticketsListView.setAdapter(ticketRecyclerAdapter);
        ticketsListView.setLayoutManager(new LinearLayoutManager(this));

        name.setText(CustomUtils.userData.getName());
        email.setText(CustomUtils.userData.getEmail());

        newBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,Booking.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    new CustomUtils(Dashboard.this,"logout").doPost(new JSONObject(),true);
                                    startActivity(new Intent(Dashboard.this,Login.class));
                                }catch (Exception exception){
                                    exception.printStackTrace();
                                }

                            }
                        }
                ).start();

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}