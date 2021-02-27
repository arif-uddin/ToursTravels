package com.diu.tourstravels.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.diu.tourstravels.Adapter.BookingListAdapter;
import com.diu.tourstravels.Adapter.ReceiptListAdapter;
import com.diu.tourstravels.Model.ModelBooking;
import com.diu.tourstravels.Model.ModelPayment;
import com.diu.tourstravels.Model.ModelUser;
import com.diu.tourstravels.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ReceiptListActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    RecyclerView recyclerView;
    ReceiptListAdapter receiptListAdapter;
    ArrayList<ModelPayment> payments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_list);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Payment Receipt");


        payments = new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("payments");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        LinearLayoutManager linearVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearVertical);
        receiptListAdapter = new ReceiptListAdapter(getApplicationContext(),payments);
        recyclerView.setAdapter(receiptListAdapter);


        Query query = FirebaseDatabase.getInstance().getReference().child("payments");
        query.orderByChild("userId").equalTo(firebaseAuth.getCurrentUser().getUid()).addChildEventListener(new ReceiptListActivity.QueryForPayments());
    }


    private class QueryForPayments implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            final ModelPayment modelPayment = dataSnapshot.getValue(ModelPayment.class);
            receiptListAdapter.setValues(modelPayment);
            receiptListAdapter.notifyDataSetChanged();

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