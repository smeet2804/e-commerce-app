package com.example.mymallapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        SystemClock.sleep(3000);



    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser currentUser= firebaseAuth.getCurrentUser();
        SessionClass sessionManagement = new SessionClass(this);
        String user = sessionManagement.getSession();

        /*if(user.equals("no")){
            //user id logged in and so move to mainActivity
            Intent RegisterIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(RegisterIntent);
            finish();
        }
        else{
            Intent mainIntent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(mainIntent);
            finish();
        }*/
        if(currentUser == null){
            Intent RegisterIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(RegisterIntent);
            finish();
        }else{
            Intent mainIntent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(mainIntent);
            finish();
        }

    }
}
