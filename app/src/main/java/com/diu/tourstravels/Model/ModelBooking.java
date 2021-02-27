package com.diu.tourstravels.Model;

public class ModelBooking {
    String packageName;
    String placeName;
    String adult;
    String child;
    String transport;
    String stayAmount;
    String foodAmount;
    String bookingName;
    String phoneNo;
    String days;
    String totalCost;
    String userId;
    String dateOfBooking;
    String dateForBooking;
    String payment;
    String bookingSerial;
    String bookingStatus;

    public ModelBooking() {
    }

    public ModelBooking(String packageName, String placeName, String adult, String child, String transport, String stayAmount, String foodAmount, String bookingName, String phoneNo, String days, String totalCost, String userId, String dateOfBooking, String dateForBooking, String payment, String bookingSerial, String bookingStatus) {
        this.packageName = packageName;
        this.placeName = placeName;
        this.adult = adult;
        this.child = child;
        this.transport = transport;
        this.stayAmount = stayAmount;
        this.foodAmount = foodAmount;
        this.bookingName = bookingName;
        this.phoneNo = phoneNo;
        this.days = days;
        this.totalCost = totalCost;
        this.userId = userId;
        this.dateOfBooking = dateOfBooking;
        this.dateForBooking = dateForBooking;
        this.payment = payment;
        this.bookingSerial = bookingSerial;
        this.bookingStatus = bookingStatus;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getStayAmount() {
        return stayAmount;
    }

    public void setStayAmount(String stayAmount) {
        this.stayAmount = stayAmount;
    }

    public String getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(String foodAmount) {
        this.foodAmount = foodAmount;
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(String dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public String getDateForBooking() {
        return dateForBooking;
    }

    public void setDateForBooking(String dateForBooking) {
        this.dateForBooking = dateForBooking;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getBookingSerial() {
        return bookingSerial;
    }

    public void setBookingSerial(String bookingSerial) {
        this.bookingSerial = bookingSerial;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
