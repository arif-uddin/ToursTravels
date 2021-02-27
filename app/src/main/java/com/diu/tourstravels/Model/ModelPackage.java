package com.diu.tourstravels.Model;

import com.google.firebase.database.Exclude;

public class ModelPackage {

    private String mThumbUrl;
    private String mImageUrl;
    private String mKey;
    private String userID;
    private String packageName;
    private String placeName;
    private String adult;
    private String child;
    private String description;
    private String days;
    private String stayAmount;
    private String foodAmount;
    private String busAmount;
    private String trainAmount;
    private String airLinesAmount;

    @Exclude
    public boolean hasUserLiked=false;

    @Exclude
    public int like_counter=0;

    @Exclude
    public String like_Key;


    public void addLike()
    {
        this.like_counter++;
    }

    public void removeLike()
    {
        this.like_counter--;
    }

    public ModelPackage() {
    }

    public ModelPackage(String mThumbUrl, String mImageUrl, String mKey, String userID, String packageName, String placeName, String adult, String child, String description, String days, String stayAmount, String foodAmount, String busAmount, String trainAmount, String airLinesAmount) {
        this.mThumbUrl = mThumbUrl;
        this.mImageUrl = mImageUrl;
        this.mKey = mKey;
        this.userID = userID;
        this.packageName = packageName;
        this.placeName = placeName;
        this.adult = adult;
        this.child = child;
        this.description = description;
        this.days = days;
        this.stayAmount = stayAmount;
        this.foodAmount = foodAmount;
        this.busAmount = busAmount;
        this.trainAmount = trainAmount;
        this.airLinesAmount = airLinesAmount;
    }

    public String getmThumbUrl() {
        return mThumbUrl;
    }

    public void setmThumbUrl(String mThumbUrl) {
        this.mThumbUrl = mThumbUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
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

    public String getBusAmount() {
        return busAmount;
    }

    public void setBusAmount(String busAmount) {
        this.busAmount = busAmount;
    }

    public String getTrainAmount() {
        return trainAmount;
    }

    public void setTrainAmount(String trainAmount) {
        this.trainAmount = trainAmount;
    }

    public String getAirLinesAmount() {
        return airLinesAmount;
    }

    public void setAirLinesAmount(String airLinesAmount) {
        this.airLinesAmount = airLinesAmount;
    }
}
