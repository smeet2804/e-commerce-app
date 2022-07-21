package com.example.mymallapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {

    private String user;
    private TextView username,user_email;
    private Button sign_out_btn;
    public MyAccountFragment() {
    }
    private Button viewAllAddressBtn;
    public static final int MANAGE_ADDRESS=1;
    //private TextView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        SessionClass sessionManagement = new SessionClass(getContext());
        user = sessionManagement.getSession().toString();
        viewAllAddressBtn = view.findViewById(R.id.vieww_all_addresses_btn);
        viewAllAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myaddressesIntent = new Intent(getContext(), ActivityMyaddresses.class);
                myaddressesIntent.putExtra("username",user);
                myaddressesIntent.putExtra("MODE", MANAGE_ADDRESS);
                startActivity(myaddressesIntent);
            }
        });
        user_email=view.findViewById(R.id.user_email);
        username=view.findViewById(R.id.username);

        user_email.setText(user);
        username.setText(user.substring(0,user.indexOf("@")));

        sign_out_btn=view.findViewById(R.id.sign_out_btn);
        sign_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SessionClass sessionManagement = new SessionClass(MyAccountFragment.this.getContext());
                sessionManagement.removeSession();
                Intent RegisterIntent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(RegisterIntent);
                getActivity().finish();

            }
        });
        return view;
    }
}
