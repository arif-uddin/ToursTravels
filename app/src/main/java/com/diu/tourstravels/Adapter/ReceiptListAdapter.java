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

import com.diu.tourstravels.Activity.PaymentActivity;
import com.diu.tourstravels.Model.ModelBooking;
import com.diu.tourstravels.Model.ModelPayment;
import com.diu.tourstravels.R;

import java.util.ArrayList;

public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.ViewHolder> {

    private final Context context;
    ArrayList<ModelPayment> payments;

    public ReceiptListAdapter(Context context, ArrayList<ModelPayment> payments) {
        this.context = context;
        this.payments = payments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recepit_list,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptListAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.phoneNo.setText("Phone No.: "+payments.get(i).getPhoneNo());
        viewHolder.bookingName.setText("Booking Name: "+payments.get(i).getBookingName());
        viewHolder.packageName.setText("Package Name: "+payments.get(i).getPackageName());
        viewHolder.dateOfBooking.setText("Date of Booking: "+payments.get(i).getBookingDate());
        viewHolder.totalAmount.setText("Total Amount: "+payments.get(i).getTotalAmount());
        viewHolder.paymentDate.setText("Date of Payment: "+payments.get(i).getDateOfPayment());
        viewHolder.trxId.setText("Payment TrxId: "+payments.get(i).getPaymentTrxId());
        viewHolder.paymentStatus.setText("Payment Status: "+payments.get(i).getPaymentStatus());

        if (payments.get(i).getPaymentStatus().equals("Not Confirmed")){
            viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#FFEC6C61"));
        }

    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView packageName, phoneNo, bookingName, dateOfBooking, totalAmount, paymentDate, trxId, paymentStatus;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneNo = (TextView) itemView.findViewById(R.id.phoneNo);
            packageName = (TextView) itemView.findViewById(R.id.packageName);
            bookingName = (TextView) itemView.findViewById(R.id.bookingName);
            dateOfBooking = (TextView) itemView.findViewById(R.id.dateOfBooking);
            totalAmount = (TextView) itemView.findViewById(R.id.totalAmount);
            paymentDate = (TextView) itemView.findViewById(R.id.paymentDate);
            trxId = (TextView) itemView.findViewById(R.id.trxId);
            paymentStatus = (TextView) itemView.findViewById(R.id.paymentStatus);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }

    public void setValues(ModelPayment payment){
        payments.add(0,payment);
        notifyDataSetChanged();
    }
}
