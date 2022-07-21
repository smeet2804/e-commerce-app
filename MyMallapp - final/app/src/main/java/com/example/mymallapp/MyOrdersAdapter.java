package com.example.mymallapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {

    private List<MyOrderItemModel> myOrderItemModelList;
    private String user;
    public MyOrdersAdapter(List<MyOrderItemModel> myOrderItemModelList,String user) {
        this.myOrderItemModelList = myOrderItemModelList;
    }
    private LinearLayout ratenow_container;

    @NonNull
    @Override
    public MyOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_orderitem_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAdapter.ViewHolder holder, int position) {
        String resource=myOrderItemModelList.get(position).getProduct_image();
        int rating=myOrderItemModelList.get(position).getRating();
        String title=myOrderItemModelList.get(position).getProduct_title();
        String delivereddate=myOrderItemModelList.get(position).getDelivery_status();
        String orderid=myOrderItemModelList.get(position).getOrderid();
        holder.setData(orderid,resource,title,delivereddate,rating);

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView product_Image;
        private TextView product_Title;
        private TextView delivery_status;
        private ImageView order_indicator;
        private Button order_cancel_button;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            product_Image=itemView.findViewById(R.id.product_image);
            product_Title=itemView.findViewById(R.id.product_title);
            order_indicator=itemView.findViewById(R.id.order_indicator);
            delivery_status=itemView.findViewById(R.id.order_delivered_date);
            ratenow_container=itemView.findViewById(R.id.rate_now_container);
            order_cancel_button=itemView.findViewById(R.id.order_cancel_button);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent OrderDeatilsIntent=new Intent(itemView.getContext(),OrderDetailsActivity.class);
                    itemView.getContext().startActivity(OrderDeatilsIntent);
                }
            });
        }
        private void setData(final String orderid, String resource, String title, String deliveryDate, int rating) {
            //product_Image.setImageResource(resource);
           URL url = null;
            try {
                if(resource.isEmpty()){
                    resource="https://blog.southindiajewels.com/wp-content/uploads/2018/08/gold-necklace-designs-in-15-grams-2.png";
                }
                url = new URL(resource);

            InputStream content = (InputStream)url.getContent();
            Drawable d = Drawable.createFromStream(content , "src");
            product_Image.setImageDrawable(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
            product_Title.setText(title);
            if (deliveryDate.equals("CANCEL")) {
                order_indicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorPrimary)));
                order_cancel_button.setVisibility(View.INVISIBLE);
            } else {
                order_indicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.successGreen)));
                order_cancel_button.setVisibility(View.VISIBLE);
            }
            delivery_status.setText(deliveryDate);
            ratenow_container = itemView.findViewById(R.id.rate_now_container);
            setRating(rating);
            for (int x = 0; x < ratenow_container.getChildCount(); x++) {
                final int starPosition = x;
                ratenow_container.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(starPosition);
                    }
                });

            }
            order_cancel_button=itemView.findViewById(R.id.order_cancel_button);

            order_cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //code by prashant
                        String url2=LinkClass.getLink()+"/OrderCancelServlet?username="+user+"&orderid="+orderid;

                    String sb=DataFetcher.fetchUrl(url2);
                    Toast.makeText(itemView.getContext(),parse(sb.toString()),Toast.LENGTH_SHORT).show();


                    for(int i=0;i<myOrderItemModelList.size();i++){
                        if(orderid.equals(myOrderItemModelList.get(i).getOrderid())){
                            MyOrderItemModel mm=myOrderItemModelList.get(i);
                            myOrderItemModelList.remove(i);
                            mm.setDelivery_status("CANCEL");
                            myOrderItemModelList.add(i,mm);
                            break;
                        }

                    }
                    refreshdataset();

                }
            });
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

