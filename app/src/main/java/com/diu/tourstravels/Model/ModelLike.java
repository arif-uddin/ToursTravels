package com.diu.tourstravels.Model;

public class ModelLike {

        String userID,imageKey;


        public ModelLike()
        {

        }

        public ModelLike(String userID, String imageKey) {
            this.userID = userID;
            this.imageKey = imageKey;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getImageKey() {
            return imageKey;
        }

        public void setImageKey(String imageKey) {
            this.imageKey = imageKey;
        }
}
