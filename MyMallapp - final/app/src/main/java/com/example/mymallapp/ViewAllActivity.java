package com.example.mymallapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class ViewAllActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridView gridView;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Deals of the Day");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SessionClass sessionManagement = new SessionClass(this);
        user = sessionManagement.getSession().toString();

        String title=getIntent().getStringExtra("CategoryName");
        recyclerView = findViewById(R.id.recycler_view);
        gridView = findViewById(R.id.grid_view);

        int layout_code = getIntent().getIntExtra("layout_code", -1);

        if (layout_code == 0) {
            gridView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            List<WishList> wishLists = new ArrayList<>();
            /*wishLists.add(new WishList(R.mipmap.phone_2, "Pixel XL 2 (Mirror Black, 128GB)", 1, "3", "Rs. 49999/-", "Cash on Delivery"));
            wishLists.add(new WishList(R.mipmap.phone_2, "Pixel XL 2 (Mirror Black, 128GB)", 0, "3", "Rs. 49999/-", "Credit Card"));
            wishLists.add(new WishList(R.mipmap.phone_2, "Pixel XL 2 (Mirror Black, 128GB)", 2, "3", "Rs. 49999/-", "Net Banking"));
            wishLists.add(new WishList(R.mipmap.phone_2, "Pixel XL 2 (Mirror Black, 128GB)", 1, "3", "Rs. 49999/-", "Cash on Delivery"));
            wishLists.add(new WishList(R.mipmap.phone_2, "Pixel XL 2 (Mirror Black, 128GB)", 0, "3", "Rs. 49999/-", "Credit Card"));
            wishLists.add(new WishList(R.mipmap.phone_2, "Pixel XL 2 (Mirror Black, 128GB)", 2, "3", "Rs. 49999/-", "Net Banking"));
            */

            WishListAdapter horizontaladapter = new WishListAdapter(wishLists, false,user);
            recyclerView.setAdapter(horizontaladapter);
            horizontaladapter.notifyDataSetChanged();

        }
        else if (layout_code == 1) {
            recyclerView.setVisibility(View.INVISIBLE);
            gridView.setVisibility(View.VISIBLE);
            List<HorizontalProductModel> horizontalProductModelList = new ArrayList<>();
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
//            horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1, "Redmi 5A", "SD 425 Processor", "Rs 5999/-"));
            //code by prashant
            if(title==null){
                title="";
            }
                String url2=LinkClass.getLink()+"/ProductDataServlet?search="+title;

            String sb=DataFetcher.fetchUrl(url2);


            JSONArray jo= null;
                    try {
                        JsonParser jp = new JsonParser();
                        JsonArray ja= (JsonArray) jp.parse(sb.toString());
                        double totprice=0,totmrp=0;
                        int noofitems=0;
                        for(JsonElement joo: ja){
                            JsonObject jb= (JsonObject) joo;
                            //cartItemModelList.add(new CartItemModel(0,Integer.parseInt(jb.get("prodid").getAsString()),jb.get("imageurl").getAsString(),jb.get("title").getAsString(),0,"Rs."+jb.get("price").getAsString()+"/-","Rs."+jb.get("mrp").getAsString()+"/-",jb.get("quantity").getAsInt(),1,0));
                            horizontalProductModelList.add(new HorizontalProductModel(Integer.parseInt(jb.get("prodid").getAsString()),jb.get("imageurl").getAsString(),jb.get("title").getAsString(),"","Rs."+jb.get("price").getAsString()+"/-","Rs."+jb.get("mrp").getAsString()+"/-"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductModelList);
            gridView.setAdapter(gridProductLayoutAdapter);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
