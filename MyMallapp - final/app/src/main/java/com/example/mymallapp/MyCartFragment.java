package com.example.mymallapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MyCartFragment extends Fragment {
    private TextView total_cart_pricebox,total_cart_amount,noprod;
    private String user;
    private ProgressBar pr;
    private RecyclerView cartItemsrecyclerView;
    private Button continueBtn;
    private LinearLayout linearLayout5;
    private List<CartItemModel> cartItemModelList;
    // Required empty public constructor
    private Handler objHand = new Handler(){
        public void handleMessage(Message mm){
            super.handleMessage(mm);
            //threadFunc();
            if(cartItemModelList.size()<1){

                pr.setVisibility(View.GONE);
                noprod.setVisibility(View.VISIBLE);
                cartItemsrecyclerView.setVisibility(View.GONE);
            }
            else{

                pr.setVisibility(View.GONE);
                noprod.setVisibility(View.GONE);
                cartItemsrecyclerView.setVisibility(View.VISIBLE);
                linearLayout5.setVisibility(View.VISIBLE);


                CartAdapter cartAdapter=new CartAdapter(cartItemModelList,user);
                cartItemsrecyclerView.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
            }
        }
    };
    public MyCartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartItemsrecyclerView=view.findViewById(R.id.cart_items_recycler_view);
        continueBtn=view.findViewById(R.id.cart_continue_btn);
        noprod=view.findViewById(R.id.cart_text_noproducts);
        pr=view.findViewById(R.id.cart_progressbar);
        linearLayout5=view.findViewById(R.id.linearLayout5);
        LinearLayoutManager layoutManager=new LinearLayoutManager((getContext()));
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsrecyclerView.setLayoutManager(layoutManager);

        SessionClass sessionManagement = new SessionClass(getContext());
        user = sessionManagement.getSession().toString();
        total_cart_pricebox=view.findViewById(R.id.total_cart_price);
        total_cart_amount=view.findViewById(R.id.total_cart_amount);
        cartItemModelList=new ArrayList<>();
        //cartItemModelList.add(new CartItemModel(0,R.mipmap.phone_2,"Pixel XL 2",2,"Rs.49999/-","Rs.59999/-",1,1,0));
        //cartItemModelList.add(new CartItemModel(0,R.mipmap.phone_2,"Pixel XL 2",0,"Rs.49999/-","Rs.59999/-",1,1,0));
        //cartItemModelList.add(new CartItemModel(0,R.mipmap.phone_2,"Pixel XL 2",2,"Rs.49999/-","Rs.59999/-",1,1,0));
        //code by prashant
        pr.setVisibility(View.VISIBLE);
        noprod.setVisibility(View.GONE);
        cartItemsrecyclerView.setVisibility(View.INVISIBLE);
        linearLayout5.setVisibility(View.GONE);


        Runnable c=new Runnable() {
            @Override
            public void run() {
                    Thread tt=new Thread();
                    try {
                        tt.sleep(20000);
                        String url2 = LinkClass.getLink() + "/CartDataServlet?username=" + user;
                        String sb = DataFetcher.fetchUrl(url2);
                        if (sb == null || sb.isEmpty() || sb.length() < 3) {
                        } else {
                            JSONArray jo = null;
                            try {
                                JsonParser jp = new JsonParser();
                                JsonArray ja = (JsonArray) jp.parse(sb.toString());
                                double totprice = 0, totmrp = 0;
                                int noofitems = 0;
                                for (JsonElement joo : ja) {
                                    JsonObject jb = (JsonObject) joo;
                                    cartItemModelList.add(new CartItemModel(0, Integer.parseInt(jb.get("prodid").getAsString()), jb.get("imageurl").getAsString(), jb.get("title").getAsString(), 0, "Rs." + jb.get("price").getAsString() + "/-", "Rs." + jb.get("mrp").getAsString() + "/-", jb.get("quantity").getAsInt(), 1, 0));
                                    noofitems++;
                                    totprice += Integer.parseInt(jb.get("price").getAsString());
                                    totmrp += Integer.parseInt(jb.get("mrp").getAsString());
                                }
                                cartItemModelList.add(new CartItemModel(1, "Price: (" + noofitems + " Items)", "Rs." + totprice + "/-", "Free", "Rs." + totprice + "/-", "Saved Price : " + (totmrp - totprice) + "/-"));
                                total_cart_pricebox.setText("Rs." + totprice + "/-");
                                total_cart_amount.setText("Total Amount");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            }
                            objHand.sendEmptyMessage(0);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        };
        Thread tt=new Thread(c);
        tt.start();



        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(getContext(),AddAddressActivity.class);
                getContext().startActivity(deliveryIntent);
            }
        });
        return view;
    }
public void threadFunc() {
    String url2 = LinkClass.getLink() + "/CartDataServlet?username=" + user;
    String sb = DataFetcher.fetchUrl(url2);
    if (sb == null || sb.isEmpty() || sb.length() < 3) {
    } else {
        JSONArray jo = null;
        try {
            JsonParser jp = new JsonParser();
            JsonArray ja = (JsonArray) jp.parse(sb.toString());
            double totprice = 0, totmrp = 0;
            int noofitems = 0;
            for (JsonElement joo : ja) {
                JsonObject jb = (JsonObject) joo;
                cartItemModelList.add(new CartItemModel(0, Integer.parseInt(jb.get("prodid").getAsString()), jb.get("imageurl").getAsString(), jb.get("title").getAsString(), 0, "Rs." + jb.get("price").getAsString() + "/-", "Rs." + jb.get("mrp").getAsString() + "/-", jb.get("quantity").getAsInt(), 1, 0));
                noofitems++;
                totprice += Integer.parseInt(jb.get("price").getAsString());
                totmrp += Integer.parseInt(jb.get("mrp").getAsString());
            }
            cartItemModelList.add(new CartItemModel(1, "Price: (" + noofitems + " Items)", "Rs." + totprice + "/-", "Free", "Rs." + totprice + "/-", "Saved Price : " + (totmrp - totprice) + "/-"));
            total_cart_pricebox.setText("Rs." + totprice + "/-");
            total_cart_amount.setText("Total Amount");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}}