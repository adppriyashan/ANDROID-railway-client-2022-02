package com.codetek.railwayandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codetek.railwayandroid.Models.CustomResponse;
import com.codetek.railwayandroid.Models.CustomUtils;
import com.codetek.railwayandroid.Models.Ticket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public class Payments extends AppCompatActivity {

    private TextView total;
    private Button confirmButton;
    ImageView backbtn;
    public String totalVal;
    public String[] seats;
    public String date;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        progress=new ProgressDialog(this);
        progress.setTitle("Please wait");
        progress.setMessage("Processing Seats & Payments");
        progress.setCancelable(false);

        backbtn=findViewById(R.id.payments_back_btn);
        total=findViewById(R.id.payments_total);
        confirmButton=findViewById(R.id.payment_btn);

        totalVal=getIntent().getStringExtra("total");
        seats=getIntent().getStringArrayExtra("seats");
        date=getIntent().getStringExtra("date");

        if(totalVal!=null){
            total.setText("LKR "+ totalVal);
        }

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progress.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject sendRequest=new JSONObject();
                            sendRequest.put("date",date);
                            sendRequest.put("starttime",CustomUtils.selectedTrain.getSchedules().get(0).getSlot());
                            sendRequest.put("endtime",CustomUtils.selectedTrain.getSchedules().get(1).getSlot());
                            sendRequest.put("start",Integer.toString(CustomUtils.selectedTrain.getStart()));
                            sendRequest.put("end",Integer.toString(CustomUtils.selectedTrain.getEnd()));
                            sendRequest.put("seats", Arrays.toString(seats));
                            sendRequest.put("amount",totalVal);
                            sendRequest.put("turn", CustomUtils.selectedTrain.getSchedules().get(0).getTurn());
                            CustomResponse resp=new CustomUtils(Payments.this,"enrollbooking").doPost(sendRequest,true);
                            CustomUtils.tickets=new Gson().fromJson(new JSONObject(resp.body().toString()).get("data").toString(), new TypeToken<List<Ticket>>(){}.getType());

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    progress.hide();
                                    startActivity(new Intent(Payments.this,Dashboard.class));
                                }
                            });

                        }catch (Exception e){
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    progress.hide();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }
}