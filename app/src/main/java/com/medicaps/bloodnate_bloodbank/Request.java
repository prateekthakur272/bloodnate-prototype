package com.medicaps.bloodnate_bloodbank;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
// Object of Request class holds all the information for a request like
//TODO: add documentation
public class Request {
    String token;
    String name;
    String email;
    String address;
    RequestType requestType;
    @Nullable BloodGroup RequestBloodGroup;
    int volume;
    @Nullable BloodGroup DonorBloodGroup;
    // constructor
    public Request(String token, String name, String email, String address, RequestType requestType, @Nullable BloodGroup requestBloodGroup, int volume, @Nullable BloodGroup donorBloodGroup) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.address = address;
        this.requestType = requestType;
        RequestBloodGroup = requestBloodGroup;
        this.volume = volume;
        DonorBloodGroup = donorBloodGroup;
    }
    // constructor
    public Request(String name, String email, String address, @Nullable BloodGroup requestBloodGroup, int volume, @Nullable BloodGroup donorBloodGroup) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.RequestBloodGroup = requestBloodGroup;
        this.volume = volume;
        this.DonorBloodGroup = donorBloodGroup;
        this.requestType = (requestBloodGroup==null)?RequestType.DONATION_REQUEST:RequestType.COLLECTION_REQUEST;

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmss");
        this.token = name.toUpperCase(Locale.ROOT).substring(0,3)+ft.format(dNow);
    }
    // a function to display all the details of a request in terminal
    // for debugging purpose
    public void display(){
        System.out.println(this.token);
        System.out.println(this.getName());
        System.out.println(this.getEmail());
        System.out.println(this.getAddress());
        System.out.println(this.requestType);
        System.out.println(this.getRequestBloodGroup());
        System.out.println(this.volume);
        System.out.println(this.getDonorBloodGroup());
    }
    // getters and setters to all the properties of request class
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public BloodGroup getRequestBloodGroup() { return RequestBloodGroup; }
    public void setRequestBloodGroup(BloodGroup requestBloodGroup) { RequestBloodGroup = requestBloodGroup; }
    public int getVolume() { return volume; }
    public void setVolume(int volume) { this.volume = volume; }
    @Nullable
    public BloodGroup getDonorBloodGroup() { return DonorBloodGroup; }
    public void setDonorBloodGroup(@Nullable BloodGroup donorBloodGroup) {
        DonorBloodGroup = donorBloodGroup;
    }
    // toString function returns a string containing general details about a request to be shown in request list
    public String toString(){
        return this.token+"\n"+this.requestType.toString()+"\n"+BloodGroup.BG(requestType==RequestType.COLLECTION_REQUEST?getRequestBloodGroup():getDonorBloodGroup());
    }
}
