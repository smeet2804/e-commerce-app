package com.example.mymallapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String title=getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView=findViewById(R.id.category_recycler_view);


        ////////BannerSlider
        List<SliderModel>sliderModelList=new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.mipmap.home_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.custom_error_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.green_email,"#077AE4"));

        sliderModelList.add(new SliderModel(R.mipmap.redemail,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.app_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.cart_black,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.profile_placeholder,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.home_icon,"#077AE4"));

        sliderModelList.add(new SliderModel(R.mipmap.custom_error_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.green_email,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.redemail,"#077AE4"));





        ////////BannerSlider


        //////////////// Strip Ad Layout
        //////////////// Strip Ad Layout



        ///////// Horizontal ProductLayout


        List<HorizontalProductModel> horizontalProductModelList= new ArrayList<>();
//        horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1,"Redmi 5A","SD 425 Processor","Rs 5999/-"));
//        horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1,"Redmi 5A","SD 425 Processor","Rs 5999/-"));
//        horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1,"Redmi 5A","SD 425 Processor","Rs 5999/-"));
//        horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1,"Redmi 5A","SD 425 Processor","Rs 5999/-"));
//        horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1,"Redmi 5A","SD 425 Processor","Rs 5999/-"));
//        horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1,"Redmi 5A","SD 425 Processor","Rs 5999/-"));
//        horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1,"Redmi 5A","SD 425 Processor","Rs 5999/-"));
//        horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1,"Redmi 5A","SD 425 Processor","Rs 5999/-"));
//        horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1,"Redmi 5A","SD 425 Processor","Rs 5999/-"));
//        horizontalProductModelList.add(new HorizontalProductModel(R.mipmap.phone_1,"Redmi 5A","SD 425 Processor","Rs 5999/-"));
        //code by prashant
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





        ///////// Horizontal ProductLayout


        /////// GRid product Layout


        LinearLayoutManager testingLayoutManager=new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList=new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        //homePageModelList.add(new HomePageModel(1,R.mipmap.galaxy,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deals of the Day",horizontalProductModelList));
        homePageModelList.add(new HomePageModel(3,"Deals of the Day",horizontalProductModelList));
        //homePageModelList.add(new HomePageModel(1,R.mipmap.image,"#000000"));
        homePageModelList.add(new HomePageModel(3,"Deals of the Day",horizontalProductModelList));
        homePageModelList.add(new HomePageModel(2,"Deals of the Day",horizontalProductModelList));
        //homePageModelList.add(new HomePageModel(1,R.mipmap.cart_black,"#ffffff"));



        HomePageAdapter adapter=new HomePageAdapter(homePageModelList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == R.id.main_search_icon) {

            return true;
        }else if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
