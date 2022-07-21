 package com.example.mymallapp;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


 /**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {


    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    private EditText registeredEmail;
    private Button resetPasswordBtn;
    private TextView goback;
    private FrameLayout parentFrameLayout;

    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reset_password, container, false);

        registeredEmail= view.findViewById(R.id.forgot_password_email);
        registeredEmail.setText("prashantgindani@gmail.com");
        resetPasswordBtn= view.findViewById(R.id.reset_password_btn);
        goback=view.findViewById(R.id.tv_forgot_password_go_back);
        parentFrameLayout=getActivity().findViewById(R.id.register_framelayout);
        firebaseAuth=FirebaseAuth.getInstance();
        return view;
    }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);

         registeredEmail.addTextChangedListener(new TextWatcher() {
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

         goback.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 setFragment(new SignInFragment());
             }
         });

         /*resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 resetPasswordBtn.setEnabled(false);
                 resetPasswordBtn.setTextColor(Color.argb(50,255,255,255));

                 firebaseAuth.sendPasswordResetEmail(registeredEmail.getText().toString())
                         .addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    Toast.makeText(getActivity(),"Email sent successfully",Toast.LENGTH_LONG).show();
                                }else{
                                    String error= task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                }
                                 resetPasswordBtn.setEnabled(true);
                                 resetPasswordBtn.setTextColor(Color.rgb(255,255,255));
                             }
                         });

             }
         });*/

         //code by prashant
         resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 resetPasswordBtn.setEnabled(false);
                 resetPasswordBtn.setTextColor(Color.argb(50, 255, 255, 255));
                     String url2 = LinkClass.getLink()+"/ForgotServlet?username=" + registeredEmail.getText();

                 String sb=DataFetcher.fetchUrl(url2);


                     Toast.makeText(getActivity(), parse(sb.toString()), Toast.LENGTH_SHORT).show();
                     //Toast.makeText(getActivity(),"Email sent successfully",Toast.LENGTH_LONG).show();

             }
         });
     }

        private void checkInputs(){
            if(TextUtils.isEmpty(registeredEmail.getText())){
                resetPasswordBtn.setEnabled(false);
                resetPasswordBtn.setTextColor(Color.argb(50,255,255,255));
            }else{
                resetPasswordBtn.setEnabled(true);
                resetPasswordBtn.setTextColor(Color.rgb(255,255,255));
            }

        }
     private void setFragment(Fragment fragment){
         FragmentTransaction fragmentTransaction= getActivity().getSupportFragmentManager().beginTransaction();
         fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
         fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
         fragmentTransaction.commit();
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




