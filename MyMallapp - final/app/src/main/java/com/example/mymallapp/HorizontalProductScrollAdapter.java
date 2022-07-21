package com.example.mymallapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.StringJoiner;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {


    private List<HorizontalProductModel> horizontalProductModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProductModel> horizontalProductModelList) {
        this.horizontalProductModelList = horizontalProductModelList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewgroup, int viewType) {

        View view= LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.horizontal_scroll_item_layout,viewgroup,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder viewHolder, int position) {
        String resource=horizontalProductModelList.get(position).getProductImage();
        String title=horizontalProductModelList.get(position).getProductTitle();
        String description=horizontalProductModelList.get(position).getProductDescription();
        String price=horizontalProductModelList.get(position).getProductPrice();
        String prodid=""+horizontalProductModelList.get(position).getProdid();

        viewHolder.setProductImage(resource);
        viewHolder.setProductTitle(title);
        viewHolder.setProductDescription(description);
        viewHolder.setProductPrice(price);
        viewHolder.setProdid(prodid);
    }

    @Override
    public int getItemCount() {
        if(horizontalProductModelList.size()>8){
            return 8;
        }else {
            return horizontalProductModelList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView productDescription;
        private TextView productPrice;
        private String prodid;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            productImage= itemView.findViewById(R.id.h_s_product_image);
            productTitle=itemView.findViewById(R.id.h_s_product_title);
            productDescription=itemView.findViewById(R.id.h_s_product_description);
            productPrice=itemView.findViewById(R.id.h_s_product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    System.out.println(" prodid ===============   "+prodid);
                    productDetailsIntent.putExtra("Prodid",""+prodid);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }
        private void setProductImage(String resource){
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
            //productImage.setImageResource(resource);
        }
        private void setProductTitle(String title){
            productTitle.setText(title);
        }
        private void setProductDescription(String Description){
            productDescription.setText(Description);
        }
        private void setProductPrice(String price){
            productPrice.setText(price);
        }
        private void setProdid(String pp){
            prodid=pp;
        }
    }
}
