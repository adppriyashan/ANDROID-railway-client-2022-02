package com.codetek.railwayandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codetek.railwayandroid.Models.CustomUtils;

import java.util.ArrayList;
import java.util.List;

public class SeatSelection extends AppCompatActivity  implements View.OnClickListener {
    ViewGroup layout;

    ImageView backbtn;

    Button confirmationButton;

    //U - not available
    //A - Available
    //R Reserved
    //_ Space

    String seats = "";

    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 100;
    int seatGaping = 10;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    String selectedIds = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        for (int val:CustomUtils.selectedTrain.getBooked()){
            System.out.println(val);
        }

        backbtn=findViewById(R.id.seats_back);
        confirmationButton=findViewById(R.id.seat_booking_confirmation);

        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(selectedIds);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        int firstClassCheckIndex=1;
        for (int firstClasses=0;firstClasses<CustomUtils.selectedTrain.getFirstclass();firstClasses++){
            seats+="A";
            if(firstClassCheckIndex==2){
                seats+="__";
            }
            if(firstClassCheckIndex==4){
                seats+="/";
                firstClassCheckIndex=1;
            }else{
                firstClassCheckIndex++;
            }
        }
        seats+="/";
        seats+="/";
        int secondClassCheckIndex=1;
        for (int secondClasses=0;secondClasses<CustomUtils.selectedTrain.getSecondclass();secondClasses++){
            seats+="B";
            if(secondClassCheckIndex==2){
                seats+="__";
            }
            if(secondClassCheckIndex==4){
                seats+="/";
                secondClassCheckIndex=1;
            }else{
                secondClassCheckIndex++;
            }
        }
        seats+="/";
        seats+="/";
        int thirdClassCheckIndex=1;
        for (int thirdClasses=0;thirdClasses<CustomUtils.selectedTrain.getThirdclass();thirdClasses++){
            seats+="C";
            if(thirdClassCheckIndex==2){
                seats+="__";
            }
            if(thirdClassCheckIndex==4){
                seats+="/";
                thirdClassCheckIndex=1;
            }else{
                thirdClassCheckIndex++;
            }
        }

        layout = findViewById(R.id.layoutSeat);

        seats = "/" + seats;

        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;

        for (int index = 0; index < seats.length(); index++) {
            if (seats.charAt(index) == '/') {
                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);
            } else if (seats.charAt(index) == 'U') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_booked);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_BOOKED);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'A' || seats.charAt(index) == 'B' || seats.charAt(index) == 'C') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource((seats.charAt(index) == 'A')?R.drawable.ic_seats_book_first_class:((seats.charAt(index) == 'B')?R.drawable.ic_seats_book_second_class:R.drawable.ic_seats_book_third_class));
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.BLACK);
                view.setTag(STATUS_AVAILABLE);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'R') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_reserved);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_RESERVED);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == '_') {
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if ((int) view.getTag() == STATUS_AVAILABLE) {
            if (selectedIds.contains(view.getId() + ",")) {
                selectedIds = selectedIds.replace(+view.getId() + ",", "");
                view.setBackgroundResource(R.drawable.ic_seats_book);
            } else {
                selectedIds = selectedIds + view.getId() + ",";
                view.setBackgroundResource(R.drawable.ic_seats_selected);
            }
        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
        }
    }
}