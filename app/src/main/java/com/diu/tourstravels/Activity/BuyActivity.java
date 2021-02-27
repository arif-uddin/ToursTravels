package com.diu.tourstravels.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.diu.tourstravels.Model.ModelBooking;
import com.diu.tourstravels.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class BuyActivity extends AppCompatActivity {

    TextView packageName, location, days, adult, child, stayAmount, foodAmount, totalAmount, selectDate;
    ImageView image;
    EditText phoneNo,name;
    private String imageUrl, food, date="", transport="No Transport Service",currentDate;
    private int fAmount=0,sAmount=0, sum=0;
    CheckBox bus,train,airplane;

    FirebaseUser firebaseUser;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Buy Package");

        currentDate=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        packageName=findViewById(R.id.packageName);
        location=findViewById(R.id.location);
        days=findViewById(R.id.days);
        adult=findViewById(R.id.adult);
        child=findViewById(R.id.child);
        stayAmount=findViewById(R.id.stayAmount);
        foodAmount=findViewById(R.id.foodAmount);
        image=findViewById(R.id.image);
        totalAmount=findViewById(R.id.totalAmount);
        selectDate=findViewById(R.id.selectDate);
        phoneNo=findViewById(R.id.phoneNo);
        name=findViewById(R.id.name);


        bus=(CheckBox) findViewById(R.id.bus);
        train=(CheckBox)findViewById(R.id.train);
        airplane=(CheckBox)findViewById(R.id.airplane);
        bus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(bus.isChecked()){
                    train.setChecked(false);
                    airplane.setChecked(false);
                    int busAmount=Integer.parseInt(getIntent().getExtras().getString("busAmount").trim());
                    totalAmount.setText(String.valueOf(busAmount+fAmount+sAmount));
                    sum=busAmount+fAmount+sAmount;
                    transport="Bus Cost: "+getIntent().getExtras().getString("busAmount")+" BDT";
                }
                else{
                    totalAmount.setText(String.valueOf(fAmount+sAmount));
                    transport="No Transport Service";
                }
            }
        });

        train.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(train.isChecked()){
                    bus.setChecked(false);
                    airplane.setChecked(false);
                    int trainAmount=Integer.parseInt(getIntent().getExtras().getString("trainAmount").trim());
                    totalAmount.setText(String.valueOf(trainAmount+fAmount+sAmount));
                    sum=trainAmount+fAmount+sAmount;
                    transport="Train Cost: "+getIntent().getExtras().getString("trainAmount")+" BDT";
                }
                else {
                    totalAmount.setText(String.valueOf(fAmount+sAmount));
                    transport="No Transport Service";
                }

            }
        });


        airplane.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(airplane.isChecked()){
                    bus.setChecked(false);
                    train.setChecked(false);
                    int airplaneAmount=Integer.parseInt(getIntent().getExtras().getString("airlinesAmount").trim());
                    totalAmount.setText(String.valueOf(airplaneAmount+fAmount+sAmount));
                    sum=airplaneAmount+fAmount+sAmount;
                    transport="Air Fare: "+getIntent().getExtras().getString("airlinesAmount")+" BDT";
                }
                else {
                    totalAmount.setText(String.valueOf(fAmount+sAmount));
                    transport="No Transport Service";
                }

            }
        });

        imageUrl=getIntent().getExtras().getString("imageThumb");
        Glide
                .with(getApplicationContext())
                .load(imageUrl)
                .into(image);


        packageName.setText("Package Name: "+getIntent().getExtras().getString("packageName"));
        location.setText("Location: "+getIntent().getExtras().getString("place"));
        days.setText("Days: "+getIntent().getExtras().getString("days"));
        adult.setText("Adult: "+getIntent().getExtras().getString("adult"));
        child.setText("Child: "+getIntent().getExtras().getString("child"));
        stayAmount.setText("Stay Cost: "+getIntent().getExtras().getString("stayAmount")+" BDT");
        foodAmount.setText("Food Cost: "+getIntent().getExtras().getString("foodAmount")+" BDT");


        fAmount=Integer.parseInt(getIntent().getExtras().getString("foodAmount").trim());
        sAmount=Integer.parseInt(getIntent().getExtras().getString("stayAmount").trim());
        totalAmount.setText(String.valueOf(fAmount+sAmount));


        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH);
                int day= cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(BuyActivity.this,
                        AlertDialog.THEME_HOLO_DARK,
                        mDateSetListener,
                        year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month= month+1;
                date= dayOfMonth+"-"+month+"-"+year;
                selectDate.setText(date);
            }
        };
    }

    public void btnConfirm(View view) {

        String BookingName=name.getText().toString().trim();
        String PhoneNo=phoneNo.getText().toString().trim();
        String PackageName=getIntent().getExtras().getString("packageName");
        String Location=getIntent().getExtras().getString("place");
        String Days=getIntent().getExtras().getString("days");
        String Adult=getIntent().getExtras().getString("adult");
        String Child=getIntent().getExtras().getString("child");
        String Stay=getIntent().getExtras().getString("stayAmount");
        String Food=getIntent().getExtras().getString("foodAmount");
        String Total=totalAmount.getText().toString();

        if(BookingName.isEmpty()) {
            name.setError("Name is required");
            name.requestFocus();
            return;
        }

        if(PhoneNo.isEmpty()) {
            phoneNo.setError("Phone No. is required");
            phoneNo.requestFocus();
            return;
        }

        if(date==""){
            Toast.makeText(BuyActivity.this,"Please select date for Booking!",Toast.LENGTH_LONG).show();
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String key = databaseReference.child("bookings").push().getKey();
        ModelBooking modelBooking= new ModelBooking(PackageName,Location,Adult,Child,transport,Stay,Food,BookingName,PhoneNo,Days,
                Total,firebaseUser.getUid(),currentDate,date,"Not Paid",key,"Not confirmed");
        databaseReference.child("bookings").child(key).setValue(modelBooking).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(BuyActivity.this,"Confirmed! Please complete payment!",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(BuyActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {

                    Toast.makeText(BuyActivity.this,"Not confirmed! Please check connection!",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}