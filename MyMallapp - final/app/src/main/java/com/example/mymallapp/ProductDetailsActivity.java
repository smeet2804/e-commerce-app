package com.example.mymallapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
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

import static com.example.mymallapp.Main2Activity.showCart;

public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager productImagesViewPager;
    private TabLayout viewpagerIndicator;
    private ViewPager productDetailsViewpager;
    private TabLayout productDetailsTablayout;
    private String prodid,user;
    private TextView prodtitle,price,mrp,ratings,prodDescript,average_rating;
    //////rating layout
    private LinearLayout ratenow_container;
    //////rating layout
    private Button buyNowBtn,addCart;
    private static boolean AlreadyAddedToWishList=false;
    private FloatingActionButton addtoWishListBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productDetailsViewpager=findViewById(R.id.product_details_viewpager);
        productDetailsTablayout=findViewById(R.id.product_details_tab_layout);

        prodtitle=findViewById(R.id.product_title);
        price=findViewById(R.id.product_price);
        mrp=findViewById(R.id.cutted_price);
        ratings=findViewById(R.id.ratings_current);
        prodDescript=findViewById(R.id.tv_product_description);

        average_rating=findViewById(R.id.average_rating);
        productImagesViewPager=findViewById(R.id.product_images_viewpager);
        viewpagerIndicator=findViewById(R.id.viewpager_indicator);
        buyNowBtn=findViewById(R.id.buy_now_btn);
        addCart=findViewById(R.id.add_to_cart_btn);

        List<String> productImages=new ArrayList<>();
        //productImages.add(R.mipmap.phone_1);
        //productImages.add(R.mipmap.phone_2);
        //productImages.add(R.mipmap.phone_3);
        //productImages.add(R.mipmap.phone);

        prodid=getIntent().getStringExtra("Prodid");
        System.out.println("00000 :::::::::::::::    "+prodid);
        user=getIntent().getStringExtra("username");

        SessionClass sessionManagement = new SessionClass(this);
        user = sessionManagement.getSession().toString();
        if(prodid==null){
            prodid="121";
        }
        //code by prashant
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
                        prodtitle.setText(jb.get("title").getAsString());
                        price.setText(jb.get("price").getAsString());
                        mrp.setText(jb.get("mrp").getAsString());
                        ratings.setText(jb.get("ratings").getAsString());
                        average_rating.setText(jb.get("ratings").getAsString());
                        //prodid=jb.get("prodid").getAsString();
                        productImages.add(jb.get("imageurl").getAsString());
                        productImages.add(jb.get("imageurl").getAsString());
                        productImages.add(jb.get("imageurl").getAsString());
                        productImages.add(jb.get("imageurl").getAsString());
                        //prodDescript.setText(jb.get("descript").getAsString());
                        break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }



        ProductImagesAdapter productImagesAdapter=new ProductImagesAdapter(productImages);
        productImagesViewPager.setAdapter(productImagesAdapter);

        viewpagerIndicator.setupWithViewPager(productImagesViewPager,true);





        prodtitle=findViewById(R.id.product_title);
        price=findViewById(R.id.product_price);
        mrp=findViewById(R.id.cutted_price);
        ratings=findViewById(R.id.ratings_current);




        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code by prashant
                    String url2=LinkClass.getLink()+"/CartAddServlet?username="+user+"&prodid="+prodid;

                String sb=DataFetcher.fetchUrl(url2);
                Toast.makeText(v.getContext(),parse(sb.toString()),Toast.LENGTH_SHORT).show();

                //Toast.makeText(v.getContext(),"Added To Cart",Toast.LENGTH_SHORT).show();
            }
        });
        productDetailsViewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTablayout.getTabCount()));

        productDetailsViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));

        productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ratenow_container=findViewById(R.id.rate_now_container);

        for(int x=0;x<ratenow_container.getChildCount();x++){
            final int starPosition=x;
            ratenow_container.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });
        }

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent=new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
                System.out.println("=================================="+prodid);
                deliveryIntent.putExtra("prodid",prodid.toString());
                System.out.println("=================================="+deliveryIntent.getStringExtra("prodid"));
                startActivity(deliveryIntent);
                finish();
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
    private void setRating(int starPosition) {
        for(int x=0;x<ratenow_container.getChildCount();x++){
            ImageView starBtn= (ImageView)ratenow_container.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(x <= starPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            return true;
        } else if (id == R.id.main_cart_icon) {
            Intent cartIntent=new Intent(ProductDetailsActivity.this,Main2Activity.class);
            showCart=true;
            startActivity(cartIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
