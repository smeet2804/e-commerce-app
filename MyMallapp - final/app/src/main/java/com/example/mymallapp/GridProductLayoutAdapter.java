package com.example.mymallapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;


public class GridProductLayoutAdapter extends BaseAdapter {

    List<HorizontalProductModel> horizontalProductModels;
    String prodid;
    public GridProductLayoutAdapter(List<HorizontalProductModel> horizontalProductModels) {
        this.horizontalProductModels = horizontalProductModels;
    }

    @Override
    public int getCount() {
        return horizontalProductModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view;
        if (convertView==null){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            final String prodid2=""+horizontalProductModels.get(position).getProdid();
            System.out.println(".............      "+prodid2);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent=new Intent(parent.getContext(),ProductDetailsActivity.class);
                   System.out.println("===================qqqqqi==============="+prodid2.toString());
                   productDetailsIntent.putExtra("Prodid",prodid2.toString());
                   System.out.println("================najjkjaja=================="+productDetailsIntent.getStringExtra("Prodid"));
                    parent.getContext().startActivity(productDetailsIntent);
                }
            });
            ImageView productImage=view.findViewById(R.id.h_s_product_image);
            TextView productTitle=view.findViewById(R.id.h_s_product_title);;
            TextView productDescription=view.findViewById(R.id.h_s_product_description);
            TextView productPrice=view.findViewById(R.id.h_s_product_price);
            URL url = null;
            try {
                String resource=horizontalProductModels.get(position).getProductImage();
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
            //productImage.setImageResource(horizontalProductModels.get(position).getProductImage());
            productTitle.setText(horizontalProductModels.get(position).getProductTitle());
            productDescription.setText(horizontalProductModels.get(position).getProductDescription());
            productPrice.setText(horizontalProductModels.get(position).getProductPrice());
        }else{
            view=convertView;
        }
        return view;
    }
}
