package com.diu.tourstravels.Model;

public class ModelPayment {

    String paymentId;
    String dateOfPayment;
    String paymentTrxId;
    String bookingName;
    String phoneNo;
    String paymentStatus;
    String bookingId;
    String totalAmount;
    String bookingDate;
    String packageName;
    String userId;

    public ModelPayment() {
    }

    public ModelPayment(String paymentId, String dateOfPayment, String paymentTrxId, String bookingName, String phoneNo, String paymentStatus, String bookingId, String totalAmount, String bookingDate, String packageName, String userId) {
        this.paymentId = paymentId;
        this.dateOfPayment = dateOfPayment;
        this.paymentTrxId = paymentTrxId;
        this.bookingName = bookingName;
        this.phoneNo = phoneNo;
        this.paymentStatus = paymentStatus;
        this.bookingId = bookingId;
        this.totalAmount = totalAmount;
        this.bookingDate = bookingDate;
        this.packageName = packageName;
        this.userId = userId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(String dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getPaymentTrxId() {
        return paymentTrxId;
    }

    public void setPaymentTrxId(String paymentTrxId) {
        this.paymentTrxId = paymentTrxId;
    }

    public String getBookingName() {
        return bookingName;
    }

    public void setBookingName(String bookingName) {
        this.bookingName = bookingName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
