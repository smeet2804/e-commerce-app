package com.example.mymallapp;

public class UserCredentials {
    private String useremail;
    private String username;
    UserCredentials(String u){
        useremail=u;
    }
    public void setuser(String u){
        useremail=u;
    }
    public String getuser(){
        return useremail;
    }
}
