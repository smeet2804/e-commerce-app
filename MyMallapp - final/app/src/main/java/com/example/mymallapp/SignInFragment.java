package com.example.mymallapp;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.mymallapp.RegisterActivity.onResetPasswordFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

    }

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private EditText password;

    private ImageButton closeBtn;
    private Button signInBtn;
    private FirebaseAuth firebaseAuth;
    private String emailPattern="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private ProgressBar progressBar;

    private TextView forgotPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount= view.findViewById(R.id.tv_dont_have_an_account);
        parentFrameLayout= getActivity().findViewById(R.id.register_framelayout);

        email= view.findViewById(R.id.sign_in_email);
        password= view.findViewById(R.id.sign_in_password);
        email.setText("prashantgindani@gmail.com");
        password.setText("admin@1234");
        progressBar=view.findViewById(R.id.sign_in_progressbar);
        closeBtn=view.findViewById(R.id.sign_in_close_btn);
        signInBtn=view.findViewById(R.id.sign_in_btn);
        forgotPassword=view.findViewById(R.id.sign_in_forgot_password);

        firebaseAuth=FirebaseAuth.getInstance();

        checkSession();
        return view;
    }

    private void checkSession() {
        SessionClass sessionManagement = new SessionClass(getActivity());
        String user = sessionManagement.getSession();

        if(!user.equals("no")){
            //user id logged in and so move to mainActivity
            mainIntent();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onResetPasswordFragment=true;
                setFragment(new ResetPasswordFragment());
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void checkInputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                signInBtn.setEnabled(true);
                signInBtn.setTextColor(Color.rgb(255,255 ,255 ));
            }else{
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50,255 ,255 ,255));

            }

        }else{
            signInBtn.setEnabled(false);
            signInBtn.setTextColor(Color.argb(50,255 ,255 ,255));

        }
    }
    private void checkEmailAndPassword(){
        if(email.getText().toString().matches((emailPattern))){
           if(password.length() >=8){

               progressBar.setVisibility(View.VISIBLE);
               signInBtn.setEnabled(false);
               signInBtn.setTextColor(Color.argb(50,255 ,255 ,255));

               /*firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mainIntent();
                                }else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signInBtn.setEnabled(true);
                                    signInBtn.setTextColor(Color.rgb(255 ,255 ,255));

                                    String error= task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                }
                           }
                       });*/
                   String url2=LinkClass.getLink()+"/LoginServlet?username="+email.getText()+"&password="+password.getText();

               String sb=DataFetcher.fetchUrl(url2);
               progressBar.setVisibility(View.INVISIBLE);
                   if(parse(sb.toString()).equalsIgnoreCase("Access grant")) {

                       SessionClass sessionManagement = new SessionClass(getContext());
                       UserCredentials user=new UserCredentials(email.getText().toString());
                       sessionManagement.saveSession(user);
                       mainIntent();
                   }
                   else{
                       Toast.makeText(getActivity(),parse(sb.toString()),Toast.LENGTH_SHORT).show();
                   }
            }else{
               //Toast.makeText(getActivity(),"Incorrect email or password!",Toast.LENGTH_SHORT).show();
           }
        }else{
            //Toast.makeText(getActivity(),"Incorrect email or password!",Toast.LENGTH_SHORT).show();
        }
    }
    private void mainIntent(){
        Intent mainIntent= new Intent(getActivity(),Main2Activity.class);
        System.out.println("=================================="+email.getText().toString());
        mainIntent.putExtra("username",email.getText().toString());
        System.out.println("=================================="+mainIntent.getStringExtra("username"));
        startActivity(mainIntent);
        getActivity().finish();
    }
    public String parse(String response) {
        JSONObject jo= null;
        try {
            jo = new JSONObject(response);
        String ss=jo.getString("message");
            return ss;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
