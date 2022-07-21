package com.example.mymallapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
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
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdaptor categoryAdaptor;
    private RecyclerView testing;
    private List<CategoryModel> categoryModelList;
    private FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_home,container,false);

        categoryRecyclerView= view.findViewById(R.id.category_recycler_view);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryModelList= new ArrayList<CategoryModel>();
        categoryAdaptor=new CategoryAdaptor(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdaptor);

        String title="";

      /**  firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));
                            }
                            categoryAdaptor.notifyDataSetChanged();
                        }else{
                            String error= task.getException().getMessage();
                            Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            **/
        List<CategoryModel> categoryModelList= new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("Link","Home"));
        categoryModelList.add(new CategoryModel("Link","Electronics"));
        categoryModelList.add(new CategoryModel("Link","Appliances"));
        categoryModelList.add(new CategoryModel("Link","Furniture"));
        categoryModelList.add(new CategoryModel("Link","Fashion"));
        categoryModelList.add(new CategoryModel("Link","Toys"));
        categoryModelList.add(new CategoryModel("Link","Sports"));
        categoryModelList.add(new CategoryModel("Link","Wall Arts"));
        categoryModelList.add(new CategoryModel("Link","Books"));
        categoryModelList.add(new CategoryModel("Link","Shoes"));

        categoryAdaptor=new CategoryAdaptor(categoryModelList);

        categoryRecyclerView.setAdapter(categoryAdaptor);
        categoryAdaptor.notifyDataSetChanged();



        ////////BannerSlider



        List<SliderModel>sliderModelList=new ArrayList<SliderModel>();


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
        System.out.println("Works 111");
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


        testing=view.findViewById(R.id.home_page_recycler_view);
        LinearLayoutManager testingLayoutManager=new LinearLayoutManager((getContext()));
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList=new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.galaxy,"#ffffff"));
        homePageModelList.add(new HomePageModel(2,"Deals of the Day",horizontalProductModelList));
        homePageModelList.add(new HomePageModel(3,"Deals of the Day",horizontalProductModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.image,"#ffffff"));
        homePageModelList.add(new HomePageModel(3,"Deals of the Day",horizontalProductModelList));
        homePageModelList.add(new HomePageModel(2,"Deals of the Day",horizontalProductModelList));
        //homePageModelList.add(new HomePageModel(1,R.mipmap.cart_black,"#ffffff"));
        //homePageModelList.add(new HomePageModel(0,sliderModelList));

//        homePageModelList.add(new HomePageModel(0,sliderModelList));
//        homePageModelList.add(new HomePageModel(1,R.mipmap.galaxy,"#000000"));
//        homePageModelList.add(new HomePageModel(2,"Deals of the Day",horizontalProductModelList));
//        homePageModelList.add(new HomePageModel(3,"Deals of the Day",horizontalProductModelList));
//        homePageModelList.add(new HomePageModel(1,R.mipmap.image,"#000000"));
//        homePageModelList.add(new HomePageModel(3,"Deals of the Day",horizontalProductModelList));
//        homePageModelList.add(new HomePageModel(2,"Deals of the Day",horizontalProductModelList));
//        homePageModelList.add(new HomePageModel(1,R.mipmap.cart_black,"#ffffff"));
//        homePageModelList.add(new HomePageModel(0,sliderModelList));



        HomePageAdapter adapter=new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }


}
