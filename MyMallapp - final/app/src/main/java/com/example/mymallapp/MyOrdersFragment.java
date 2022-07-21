package com.example.mymallapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment {


    private String user;
    private ProgressBar pr;
    private RecyclerView myOrdersRecyclerView;
    private TextView noprod;
    private List<MyOrderItemModel> myOrderItemModelyList;
    private Handler objHand = new Handler(){
        public void handleMessage(Message mm){
            super.handleMessage(mm);
            //threadFunc();
            if(myOrderItemModelyList.size()<1){

                pr.setVisibility(View.GONE);
                noprod.setVisibility(View.VISIBLE);
                myOrdersRecyclerView.setVisibility(View.GONE);
            }
            else{

                pr.setVisibility(View.GONE);
                noprod.setVisibility(View.GONE);
                myOrdersRecyclerView.setVisibility(View.VISIBLE);


                Collections.reverse(myOrderItemModelyList);
                MyOrdersAdapter myOrdersAdapter=new MyOrdersAdapter(myOrderItemModelyList,user);
                myOrdersRecyclerView.setAdapter(myOrdersAdapter);
                myOrdersAdapter.notifyDataSetChanged();

            }
        }
    };
    public MyOrdersFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_orders, container, false);

        myOrdersRecyclerView= view.findViewById(R.id.my_orders_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        myOrdersRecyclerView.setLayoutManager(layoutManager);
        noprod=view.findViewById(R.id.order_noproducts);
        pr=view.findViewById(R.id.order_progressbar);

        SessionClass sessionManagement = new SessionClass(getContext());
        user = sessionManagement.getSession().toString();
        myOrderItemModelyList=new ArrayList<>();
        //myOrderItemModelyList.add(new MyOrderItemModel(R.mipmap.phone_2,2,"PIXEL 2 XL (BLACK)","Delivered on Monday 15th Jan,2020"));
        //myOrderItemModelyList.add(new MyOrderItemModel(R.mipmap.phone_1,1,"PIXEL 2 XL (BLACK)","Delivered on Monday 25th Jan,2020"));
        //myOrderItemModelyList.add(new MyOrderItemModel(R.mipmap.phone,0,"PIXEL 2 XL (BLACK)","Cancelled"));
        //myOrderItemModelyList.add(new MyOrderItemModel(R.mipmap.phone_3,3,"PIXEL 2 XL (BLACK)","Delivered on Monday 30th Jan,2020"));
        pr.setVisibility(View.VISIBLE);
        noprod.setVisibility(View.GONE);
        myOrdersRecyclerView.setVisibility(View.INVISIBLE);
        //code by prashant
        Runnable c=new Runnable() {
            @Override
            public void run() {
                Thread tt=new Thread();
                try {
                    tt.sleep(20000);

                    String url2=LinkClass.getLink()+"/OrderDataServlet?username="+user;

        String sb=DataFetcher.fetchUrl(url2);

        JSONArray jo= null;
                try {
                    JsonParser jp = new JsonParser();
                    JsonArray ja= (JsonArray) jp.parse(sb.toString());
                    for(JsonElement joo: ja){
                        JsonObject jb= (JsonObject) joo;
                        myOrderItemModelyList.add(new MyOrderItemModel(jb.get("orderid").getAsString(),jb.get("imageurl").getAsString(),3,jb.get("title").getAsString(),jb.get("status").getAsString()));
                      }
                    //myOrderItemModelyList.add(new MyOrderItemModel(1,"Price: ("+noofitems+" Items)","Rs."+totprice+"/-","Free","Rs."+totmrp+"/-","Saved Price : "+(totmrp-totprice)+"/-"));
                    //total_cart_pricebox.setText("Rs."+totprice+"/-");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                    objHand.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
            };
            Thread tt=new Thread(c);
        tt.start();


        return view;

    }

}
