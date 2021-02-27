package com.diu.tourstravels.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diu.tourstravels.Adapter.BookingListAdapter;
import com.diu.tourstravels.Model.ModelBooking;
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

public class BookingListActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    static BookingListActivity bookingListActivity;

    RecyclerView recyclerVieworderList;
    BookingListAdapter bookingListAdapter;

    ArrayList<ModelBooking> bookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Booking List");
        bookingListActivity=this;

        bookings = new ArrayList<>();
        recyclerVieworderList=findViewById(R.id.recyclerView);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bookings");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        LinearLayoutManager linearVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerVieworderList.setLayoutManager(linearVertical);
        bookingListAdapter = new BookingListAdapter(getApplicationContext(),bookings);
        recyclerVieworderList.setAdapter(bookingListAdapter);


        Query query = FirebaseDatabase.getInstance().getReference().child("bookings");
        query.orderByChild("userId").equalTo(firebaseAuth.getCurrentUser().getUid()).addChildEventListener(new QueryForBookings());
    }


    private class QueryForBookings implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            final ModelBooking modelBooking = dataSnapshot.getValue(ModelBooking.class);
            bookingListAdapter.setValues(modelBooking);
            bookingListAdapter.notifyDataSetChanged();

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