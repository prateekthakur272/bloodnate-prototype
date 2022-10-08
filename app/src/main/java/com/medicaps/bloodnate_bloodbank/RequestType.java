package com.medicaps.bloodnate_bloodbank;

import androidx.annotation.NonNull;

import java.util.Locale;
// an enum class for Request type
public enum RequestType {
    // types of request
    COLLECTION_REQUEST,DONATION_REQUEST;
    // override toString method
    @NonNull
    @Override
    public String toString() {
        String s="";
        switch (this){
            case COLLECTION_REQUEST:
                s="Collection";break;
            case DONATION_REQUEST:
                s="Donation";break;
        }return s;
    }
    // to convert a string into request type
    public static RequestType getRequestType(String s){
        RequestType requestType=RequestType.DONATION_REQUEST;
        switch (s.toUpperCase()){
            case "DONATION":requestType= RequestType.DONATION_REQUEST;break;
            case "COLLECTION":requestType = RequestType.COLLECTION_REQUEST;break;
        }return requestType;
    }

}
