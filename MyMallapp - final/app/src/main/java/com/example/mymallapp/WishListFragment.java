package com.example.mymallapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WishListFragment extends Fragment {

    private String user;
    public WishListFragment() {
    }

    private RecyclerView wishListRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wish_list, container, false);

        wishListRecyclerView=view.findViewById(R.id.my_wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        wishListRecyclerView.setLayoutManager(linearLayoutManager);

        SessionClass sessionManagement = new SessionClass(getContext());
        user = sessionManagement.getSession().toString();
        List<WishList> wishLists=new ArrayList<>();
        //wishLists.add(new WishList(R.mipmap.phone_2,"Pixel XL 2 (Mirror Black, 128GB)",1,"3","Rs. 49999/-","Cash on Delivery"));
        //wishLists.add(new WishList(R.mipmap.phone_2,"Pixel XL 2 (Mirror Black, 128GB)",0,"3","Rs. 49999/-","Credit Card"));
        //wishLists.add(new WishList(R.mipmap.phone_2,"Pixel XL 2 (Mirror Black, 128GB)",2,"3","Rs. 49999/-","Net Banking"));
            String url2=LinkClass.getLink()+"/WishlistDataServlet?username="+user;

        String sb=DataFetcher.fetchUrl(url2);


        JSONArray jo= null;
                try {
                    JsonParser jp = new JsonParser();
                    JsonArray ja= (JsonArray) jp.parse(sb.toString());
                    for(JsonElement joo: ja){
                        JsonObject jb= (JsonObject) joo;
                        //myOrderItemModelyList.add(new MyOrderItemModel(jb.get("imageurl").getAsString(),3,jb.get("title").getAsString(),jb.get("status").getAsString()));
                        wishLists.add(new WishList(Integer.parseInt(jb.get("prodid").getAsString()),jb.get("imageurl").getAsString(),jb.get("title").getAsString(),2,"3",jb.get("price").getAsString(),"Net Banking"));
                    }
                    //myOrderItemModelyList.add(new MyOrderItemModel(1,"Price: ("+noofitems+" Items)","Rs."+totprice+"/-","Free","Rs."+totmrp+"/-","Saved Price : "+(totmrp-totprice)+"/-"));
                    //total_cart_pricebox.setText("Rs."+totprice+"/-");
                } catch (Exception e) {
                    e.printStackTrace();
                }

        WishListAdapter wishListAdapter=new WishListAdapter(wishLists,true,user);
        wishListRecyclerView.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();

        return view;
    }
}
