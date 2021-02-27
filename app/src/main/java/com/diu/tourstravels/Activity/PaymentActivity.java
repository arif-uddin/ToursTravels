package com.diu.tourstravels.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diu.tourstravels.Model.ModelPayment;
import com.diu.tourstravels.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {

    TextView bookingId,dateOfBooking,bookingName,phoneNo,dateForBooking,packageName,placeName,days,adult,child,stayAmount,foodAmount,totalAmount,
            payment,bookingStatus,bkash;
    String currentDate;

    EditText trxId;
    Button btnPay, btnCancel;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Payment");

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        currentDate=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        bookingId=findViewById(R.id.bookingId);
        dateOfBooking=findViewById(R.id.dateOfBooking);
        phoneNo=findViewById(R.id.phoneNo);
        bookingName=findViewById(R.id.bookingName);
        dateForBooking=findViewById(R.id.dateForBooking);
        packageName=findViewById(R.id.packageName);
        placeName=findViewById(R.id.placeName);
        days=findViewById(R.id.days);
        adult=findViewById(R.id.adult);
        child=findViewById(R.id.child);
        stayAmount=findViewById(R.id.stayAmount);
        foodAmount=findViewById(R.id.foodAmount);
        totalAmount=findViewById(R.id.totalAmount);
        payment=findViewById(R.id.payment);
        bookingStatus=findViewById(R.id.bookingStatus);
        trxId=findViewById(R.id.trxId);
        btnPay=findViewById(R.id.btnPay);
        btnCancel=findViewById(R.id.btnCancel);
        bkash=findViewById(R.id.bkash);


        bookingId.setText("Booking No: "+getIntent().getExtras().getString("bookingId"));
        dateOfBooking.setText("Date of Booking: "+getIntent().getExtras().getString("dateOfBooking"));
        phoneNo.setText("Phone No: "+getIntent().getExtras().getString("phoneNo"));
        bookingName.setText("Booking Name: "+getIntent().getExtras().getString("bookingName"));
        dateForBooking.setText("Date for Booking: "+getIntent().getExtras().getString("dateForBooking"));
        packageName.setText("Package Name: "+getIntent().getExtras().getString("packageName"));
        placeName.setText("Location: "+getIntent().getExtras().getString("location"));
        days.setText("Days: "+getIntent().getExtras().getString("days"));
        adult.setText("Adult: "+getIntent().getExtras().getString("adult"));
        child.setText("Child: "+getIntent().getExtras().getString("child"));
        stayAmount.setText("Stay Cost: "+getIntent().getExtras().getString("stayAmount"));
        foodAmount.setText("Food Cost: "+getIntent().getExtras().getString("foodAmount"));
        totalAmount.setText("Total Cost: "+getIntent().getExtras().getString("totalAmount"));
        payment.setText("Payment: "+getIntent().getExtras().getString("payment"));
        bookingStatus.setText("Booking Status: "+getIntent().getExtras().getString("status"));


        String paymentCheck=getIntent().getExtras().getString("payment");

        if (paymentCheck.equals("Not Paid")){
            btnCancel.setVisibility(View.VISIBLE);
            btnPay.setVisibility(View.VISIBLE);
            trxId.setVisibility(View.VISIBLE);
            bkash.setVisibility(View.VISIBLE);
        }



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference().child("bookings");
                query.orderByKey().equalTo(getIntent().getExtras().getString("bookingId")).addChildEventListener(new QueryForBookingCancel());
            }
        });


        btnPay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (trxId.getText().toString().trim().isEmpty()){
                    trxId.setError("Please Enter valid TrxId");
                    trxId.requestFocus();
                    return;
                }

                Query query = FirebaseDatabase.getInstance().getReference().child("bookings");
                query.orderByKey().equalTo(getIntent().getExtras().getString("bookingId")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("bookings");
                        databaseReference.child(getIntent().getExtras().getString("bookingId")).
                                child("payment").setValue(trxId.getText().toString().trim());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(PaymentActivity.this, "Done", Toast.LENGTH_SHORT).show();


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                String key = databaseReference.child("payments").push().getKey();

                ModelPayment modelPayment= new ModelPayment(key,currentDate,trxId.getText().toString().trim(),getIntent().getExtras().getString("bookingName"),
                        getIntent().getExtras().getString("phoneNo"),"Not Confirmed",getIntent().getExtras().getString("bookingId"),getIntent().getExtras().getString("totalAmount"),
                        getIntent().getExtras().getString("dateOfBooking"),getIntent().getExtras().getString("packageName"),firebaseUser.getUid());

                databaseReference.child("payments").child(key).setValue(modelPayment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(PaymentActivity.this,"Check payment status",Toast.LENGTH_LONG).show();
                            BookingListActivity.bookingListActivity.finish();
                            finish();
                        }
                        else {

                            Toast.makeText(PaymentActivity.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                        }

                    }
                });



            }
        });
    }

    private class QueryForBookingCancel implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("bookings");
            databaseReference.child(getIntent().getExtras().getString("bookingId")).removeValue();
            Toast.makeText(PaymentActivity.this, "Done", Toast.LENGTH_SHORT).show();
            BookingListActivity.bookingListActivity.finish();
            finish();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}