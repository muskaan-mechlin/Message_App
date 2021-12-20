package com.example.messageapp;

public class Model {


        // variables for storing our data.
        private String phonenumber;

        public Model() {
            // empty constructor
            // required for Firebase.
        }

        // Constructor for all variables.
        public Model(String phonenumber) {
           this.phonenumber = phonenumber;
        }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }




    }


