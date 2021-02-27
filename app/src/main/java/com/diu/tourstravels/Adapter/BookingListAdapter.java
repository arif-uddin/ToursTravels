package com.diu.tourstravels.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.diu.tourstravels.Activity.BookingListActivity;
import com.diu.tourstravels.Activity.PackageDetailsActivity;
import com.diu.tourstravels.Activity.PaymentActivity;
import com.diu.tourstravels.Model.ModelBooking;
import com.diu.tourstravels.R;

import java.util.ArrayList;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.ViewHolder> {

    private final Context context;
    ArrayList<ModelBooking> bookings;

    public BookingListAdapter(Context context, ArrayList<ModelBooking> bookings) {
        this.context = context;
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_list_view,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingListAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.phoneNo.setText("Phone No.: "+bookings.get(i).getPhoneNo());
        viewHolder.bookingName.setText("Booking Name: "+bookings.get(i).getBookingName());
        viewHolder.dateOfBooking.setText("Date of Booking: "+bookings.get(i).getDateOfBooking());
        viewHolder.dateForBooking.setText("Date for Booking: "+bookings.get(i).getDateForBooking());
        viewHolder.totalAmount.setText("Total Amount: "+bookings.get(i).getTotalCost());
        viewHolder.payment.setText("Payment Status: "+bookings.get(i).getPayment());
        viewHolder.packageName.setText("Package Name: "+bookings.get(i).getPackageName());
        viewHolder.status.setText("Booking Status: "+bookings.get(i).getBookingStatus());

        if (bookings.get(i).getPayment().equals("Not Paid")){
            viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#FFEC6C61"));
        }


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PaymentActivity.class);
                intent.putExtra("packageName",bookings.get(i).getPackageName());
                intent.putExtra("location",bookings.get(i).getPlaceName());
                intent.putExtra("adult",bookings.get(i).getAdult());
                intent.putExtra("child",bookings.get(i).getChild());
                intent.putExtra("stayAmount",bookings.get(i).getStayAmount());
                intent.putExtra("foodAmount",bookings.get(i).getFoodAmount());
                intent.putExtra("totalAmount",bookings.get(i).getTotalCost());
                intent.putExtra("dateForBooking",bookings.get(i).getDateForBooking());
                intent.putExtra("dateOfBooking",bookings.get(i).getDateOfBooking());
                intent.putExtra("bookingId",bookings.get(i).getBookingSerial());
                intent.putExtra("phoneNo",bookings.get(i).getPhoneNo());
                intent.putExtra("transport",bookings.get(i).getTransport());
                intent.putExtra("days",bookings.get(i).getDays());
                intent.putExtra("bookingName",bookings.get(i).getBookingName());
                intent.putExtra("status",bookings.get(i).getBookingStatus());
                intent.putExtra("payment",bookings.get(i).getPayment());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView phoneNo,packageName, bookingName, dateOfBooking, dateForBooking,totalAmount,payment,status;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneNo=(TextView)itemView.findViewById(R.id.phoneNo);
            bookingName=(TextView)itemView.findViewById(R.id.bookingName);
            dateOfBooking=(TextView)itemView.findViewById(R.id.dateOfBooking);
            dateForBooking=(TextView)itemView.findViewById(R.id.dateForBooking);
            totalAmount=(TextView)itemView.findViewById(R.id.totalAmount);
            payment=(TextView)itemView.findViewById(R.id.payment);
            packageName=(TextView)itemView.findViewById(R.id.packageName);
            status=(TextView)itemView.findViewById(R.id.status);
            cardView=(CardView)itemView.findViewById(R.id.cardView);

        }
    }

    public void setValues(ModelBooking booking){
        bookings.add(0,booking);
        notifyDataSetChanged();
    }
}
