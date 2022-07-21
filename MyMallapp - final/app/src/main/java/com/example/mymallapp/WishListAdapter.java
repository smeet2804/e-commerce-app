package com.example.mymallapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    private List<WishList> wishListList;
    private boolean wishList;
    private String user;
    public WishListAdapter(List<WishList> wishListList,Boolean wishList,String user) {
        this.wishListList = wishListList;
        this.wishList = wishList;
        this.user=user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String resource= wishListList.get(position).getProduct_image();
        String title= wishListList.get(position).getProduct_title();
        int freeCoupons=wishListList.get(position).getFree_coupons();
        int prodid=wishListList.get(position).getProdid();
        String ratings=wishListList.get(position).getRating();
        int totalratings=wishListList.get(position).getTotal_ratings();
        String productPrice=wishListList.get(position).getProduct_price();
        String paymentMethod=wishListList.get(position).getPayment_method();

        viewHolder.setData(prodid,resource,title,freeCoupons,ratings,productPrice,paymentMethod);

    }

    @Override
    public int getItemCount() {
        return wishListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupons;
        private ImageView couponIcon;
        private TextView ratings;
     //   private TextView totalRatings;
        private TextView productPrice;
        private TextView paymentMethod;
        private ImageButton delete_btn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            productTitle=itemView.findViewById(R.id.product_title);
            freeCoupons=itemView.findViewById(R.id.tv_free_coupon);
            couponIcon=itemView.findViewById(R.id.free_coupon_icon);
            ratings=itemView.findViewById(R.id.ratings_current);
        //    totalRatings=itemView.findViewById(R.id.total_ratings);
            productPrice=itemView.findViewById(R.id.product_price);
            paymentMethod=itemView.findViewById(R.id.paymet_method);
            delete_btn=itemView.findViewById(R.id.remove_btn);
        }
        private void setData(final int prodid, String resource, String title, int freeCouponsNo, String averageRate, final String price, String payment){
            //productImage.setImageResource(resource);

            URL url = null;
            try {
                if(resource.isEmpty()){
                    resource="https://blog.southindiajewels.com/wp-content/uploads/2018/08/gold-necklace-designs-in-15-grams-2.png";
                }
                url = new URL(resource);

                InputStream content = (InputStream)url.getContent();
                Drawable d = Drawable.createFromStream(content , "src");
                productImage.setImageDrawable(d);
            } catch (Exception e) {
                e.printStackTrace();
            }

            productTitle.setText(title);
            if(freeCouponsNo!=0){
                couponIcon.setVisibility(View.VISIBLE);
                if(freeCouponsNo==1){
                freeCoupons.setText("Free "+freeCouponsNo+" coupon");
                }
                else{
                    freeCoupons.setText("Free "+freeCouponsNo+" coupons");
                }
            }else{
                couponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }
            ratings.setText(averageRate);
//            totalRatings.setText(totalratings);
            productPrice.setText(price);
            paymentMethod.setText(payment);

            if(wishList){
                delete_btn.setVisibility(View.VISIBLE);
            }else{
                delete_btn.setVisibility(View.INVISIBLE);
            }

            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        String url2=LinkClass.getLink()+"/WishlistRemoveServlet?username="+user+"&prodid="+prodid;

                    String sb=DataFetcher.fetchUrl(url2);
                    Toast.makeText(itemView.getContext(),parse(sb.toString()),Toast.LENGTH_SHORT).show();

                    Toast.makeText(itemView.getContext(),"delete",Toast.LENGTH_SHORT).show();
                for(int i=0;i<wishListList.size();i++){
                    if(prodid==wishListList.get(i).getProdid()){
                    wishListList.remove(i);
                }
                }
                refreshdataset();
                    //Intent mainIntent= Intent.getIntent();

                }
            });
        }
    }
    public void refreshdataset(){
        this.notifyDataSetChanged();
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
