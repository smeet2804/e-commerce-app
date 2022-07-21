package com.example.mymallapp;


import android.content.Context;
import android.content.SharedPreferences;

public class SessionClass {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionClass(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(UserCredentials user){
        //save session of user whenever user is logged in
        String id = user.getuser();

        editor.putString(SESSION_KEY,id).commit();
    }

    public String getSession(){
        //return user id whose session is saved
        return sharedPreferences.getString(SESSION_KEY,"no");
    }

    public void removeSession(){
        editor.putString(SESSION_KEY,"no").commit();
    }
}