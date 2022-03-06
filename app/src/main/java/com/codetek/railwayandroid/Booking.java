package com.codetek.railwayandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codetek.railwayandroid.Models.CustomResponse;
import com.codetek.railwayandroid.Models.CustomUtils;
import com.codetek.railwayandroid.Models.Location;
import com.codetek.railwayandroid.Models.Ticket;
import com.codetek.railwayandroid.Models.Train;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Booking extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ImageView backButton;
    public static TextView date;
    private Spinner start,end,availableTrains;

    private String startValue,endValue,trainValue;

    private Button seatSelectButton;

    ArrayList locationStrings;

    public static SimpleDateFormat format;

    public ArrayList<Train> availableTrainsList;
   public ArrayList<String> availableTrainsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        initProcess();
    }

    private void initProcess() {

        CustomUtils.selectedTrain=null;

        locationStrings=new ArrayList();

        backButton=findViewById(R.id.booking_back);
        date=findViewById(R.id.booking_date);
        start=findViewById(R.id.booking_start);
        end=findViewById(R.id.booking_end);
        availableTrains=findViewById(R.id.booking_trains);
        seatSelectButton=findViewById(R.id.booking_step1_btn);

        availableTrainsArray=new ArrayList<>();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        for(Location location : CustomUtils.locations){
            locationStrings.add(location.getLocation());
        }

        start.setOnItemSelectedListener(this);
        end.setOnItemSelectedListener(this);
        availableTrains.setOnItemSelectedListener(this);

        ArrayAdapter startAdapter= new ArrayAdapter( this,android.R.layout.simple_spinner_item,locationStrings);
        startAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter endAdapter= new ArrayAdapter( this,android.R.layout.simple_spinner_item,locationStrings);
        endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        start.setAdapter(startAdapter);
        end.setAdapter(endAdapter);

        format=new SimpleDateFormat("yyyy-MM-dd");

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        seatSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CustomUtils.selectedTrain!=null){
                    Intent intent=new Intent(Booking.this,SeatSelection.class);
                    intent.putExtra("date",date.getText().toString());
                    intent.putExtra("start",startValue);
                    intent.putExtra("end",endValue);
                    startActivity(intent);
                }else{
                    Toast.makeText(Booking.this, "Please select above details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getTrains(){
        if(startValue!=null && endValue!=null && date.getText()!=null && !date.getText().toString().isEmpty()){
            if(!startValue.equals(endValue)){
                  new Thread(new Runnable() {
                      @Override
                      public void run() {
                          try {
                              JSONObject data=new JSONObject();
                              data.put("date",date.getText());
                              data.put("start",startValue);
                              data.put("end",endValue);
                              CustomResponse resp= new CustomUtils(Booking.this,"available").doPost(data,true);
                              System.out.println(resp.body());
                              availableTrainsList=new Gson().fromJson(new JSONObject(resp.body()).get("data").toString(), new TypeToken<List<Train>>(){}.getType());

                              ArrayList<String> availableTrainsArray=new ArrayList<>();

                              for(Train train:availableTrainsList){
                                  String trainSlotName=train.getAlias()+" ( "+train.getSchedules().get(0).getSlot()+" - "+train.getSchedules().get(1).getSlot()+" )";
                                  availableTrainsArray.add(trainSlotName);
                              }

                              runOnUiThread(new Runnable() {
                                  public void run() {
                                      ArrayAdapter trainsAdapter= new ArrayAdapter( Booking.this,android.R.layout.simple_spinner_item,availableTrainsArray);
                                      trainsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                      availableTrains.setAdapter(trainsAdapter);
                                  }
                              });


                          }catch (Exception e){
                              e.printStackTrace();
                              runOnUiThread(new Runnable() {
                                  public void run() {
                                      Toast.makeText(Booking.this,"Something Wrong",Toast.LENGTH_SHORT).show();
                                  }
                              });
                          }
                      }
                  }).start();
            }else{
                Toast.makeText(this, "Invalid Stations", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.booking_start){
            startValue=Integer.toString(CustomUtils.locations.get(i).getId());
            getTrains();
        }

        if(adapterView.getId()==R.id.booking_end){
            endValue=Integer.toString(CustomUtils.locations.get(i).getId());
            getTrains();
        }

        if(adapterView.getId()==R.id.booking_trains){
            CustomUtils.selectedTrain=availableTrainsList.get(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            try {
                date.setText(new SimpleDateFormat("yyyy-MM-dd").format(format.parse(year+"-"+(month+1)+"-"+day)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}