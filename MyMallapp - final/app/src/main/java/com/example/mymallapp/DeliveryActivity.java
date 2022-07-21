package com.example.mymallapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class DeliveryActivity extends AppCompatActivity {
    TextView total_cart_pricebox,total_cart_amount;
    private RecyclerView deliveryrecycler_view;
    private Button change_or_add_newaddressBtn,continueBtn;
    private TextView fullName;
    private String user,prodid;
    public static final int SELECT_ADDRESS=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        prodid=getIntent().getStringExtra("prodid");

        total_cart_pricebox=findViewById(R.id.total_cart_price);
        total_cart_amount=findViewById(R.id.total_cart_amount);
        deliveryrecycler_view=findViewById(R.id.delivery_recyclerview);
        change_or_add_newaddressBtn= findViewById(R.id.change_or_add_address_btn);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryrecycler_view.setLayoutManager(layoutManager);
        continueBtn=findViewById(R.id.cart_continue_btn);
        fullName=findViewById(R.id.fullName);

        SessionClass sessionManagement = new SessionClass(this);
        user = sessionManagement.getSession().toString();

        fullName.setText(user.substring(0,user.indexOf("@")));
        final List<CartItemModel> cartItemModelList=new ArrayList<>();
        //cartItemModelList.add(new CartItemModel(0,R.mipmap.phone_2,"Pixel XL 2",2,"Rs.49999/-","Rs.59999/-",1,1,0));
        //cartItemModelList.add(new CartItemModel(0,R.mipmap.phone_2,"Pixel XL 2",0,"Rs.49999/-","Rs.59999/-",1,1,0));
        //cartItemModelList.add(new CartItemModel(0,R.mipmap.phone_2,"Pixel XL 2",2,"Rs.49999/-","Rs.59999/-",1,1,0));

        //cartItemModelList.add(new CartItemModel(1,"Price: (3 Items)","Rs.49999/-","Free","Rs.49999/-","Saved Price : 5999/-"));
        //code by prashant
        System.out.println("qqweqweqwe  ------------ "+prodid);
        if(prodid==null) {

            System.out.println("qqweqweqwe  ------------ "+prodid);

                String url2 = LinkClass.getLink() + "/CartDataServlet?username=" + user;

            String sb=DataFetcher.fetchUrl(url2);


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
                            //System.out.println("---------------------------------------");
                            //System.out.println(((Object)jb.get("price")).toString());
                            System.out.println(jb.get("price").getAsString());
                            totprice += Integer.parseInt(jb.get("price").getAsString());
                            totmrp += Integer.parseInt(jb.get("mrp").getAsString());
                            //System.out.println(jb.get("price").getAsString());
                        }
                        cartItemModelList.add(new CartItemModel(1, "Price: (" + noofitems + " Items)", "Rs." + totprice + "/-", "Free", "Rs." + totprice + "/-", "Saved Price : " + (totmrp - totprice) + "/-"));
                        total_cart_pricebox.setText("Rs." + totprice + "/-");
                        total_cart_amount.setText("Total Amount");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


        }
        else{

            System.out.println("qqweqweqwe  ------------ "+prodid);
                String url2=LinkClass.getLink()+"/ProductDetailsServlet?prodid="+prodid;

            String sb=DataFetcher.fetchUrl(url2);


            JSONArray jo= null;
                    try {
                        JsonParser jp = new JsonParser();
                        JsonArray ja= (JsonArray) jp.parse(sb.toString());
                        double totprice=0,totmrp=0;
                        int noofitems=0;
                        for(JsonElement joo: ja){
                            JsonObject jb= (JsonObject) joo;
                            cartItemModelList.add(new CartItemModel(0, Integer.parseInt(jb.get("prodid").getAsString()), jb.get("imageurl").getAsString(), jb.get("title").getAsString(), 0, "Rs." + jb.get("price").getAsString() + "/-", "Rs." + jb.get("mrp").getAsString() + "/-", 1, 1, 0));
                            //prodDescript.setText(jb.get("descript").getAsString());
                            break;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }




        }
        CartAdapter cartAdapter=new CartAdapter(cartItemModelList,user);
        deliveryrecycler_view.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        change_or_add_newaddressBtn.setVisibility(View.VISIBLE);
        change_or_add_newaddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myaddressesIntent=new Intent(DeliveryActivity.this,ActivityMyaddresses.class);
                myaddressesIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myaddressesIntent);

            }
        });
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<cartItemModelList.size();i++){
                    //code by prashant
                    String url2=LinkClass.getLink()+"/OrderPlaceServlet?username="+user+"&prodid="+cartItemModelList.get(i).getProdid();

                    String sb=DataFetcher.fetchUrl(url2);


                    url2=LinkClass.getLink()+"/CartRemoveServlet?username="+user+"&prodid="+cartItemModelList.get(i).getProdid();

                    sb=DataFetcher.fetchUrl(url2);


                }
                Toast.makeText(v.getContext(),"Order Placed",Toast.LENGTH_SHORT).show();
                openNewActivity();
            }
        });
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
    public void openNewActivity(){
        Intent mainIntent= new Intent(this,Main2Activity.class);
        startActivity(mainIntent);
        finish();
    }
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if(id== android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
