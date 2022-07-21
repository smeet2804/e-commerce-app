package com.example.mymallapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class CartAdapter extends RecyclerView.Adapter {


    private List<CartItemModel> cartItemModelList;
    private String user;

    public CartAdapter(List<CartItemModel> cartItemModelList,String user) {
        this.cartItemModelList = cartItemModelList;
        this.user=user;
    }

    public int getItemViewType(int position){
        switch(cartItemModelList.get(position).getType()){
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch(viewType){
            case CartItemModel.CART_ITEM:
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout,viewGroup,false);
                return new CartItemViewHolder(view);
            case CartItemModel.TOTAL_AMOUNT:
                View totalview= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_amount_layout,viewGroup,false);
                return new CartTotalAmountViewholder(totalview);
            default:
                return null;
        }
    }
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (cartItemModelList.get(position).getType()){
            case CartItemModel.CART_ITEM:
                String resource=cartItemModelList.get(position).getProductImage();
                String title=cartItemModelList.get(position).getProductTitle();
                int freeCoupons=cartItemModelList.get(position).getFreeCoupons();
                String productPrice=cartItemModelList.get(position).getProductPrice();
                String cuttedPrice=cartItemModelList.get(position).getCuttedPrice();
                int offersApplied=cartItemModelList.get(position).getOffersApplied();
                int prodid=cartItemModelList.get(position).getProdid();

                ((CartItemViewHolder)viewHolder).setItemDetails(prodid,resource,title,freeCoupons,productPrice,cuttedPrice,offersApplied);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                String totalItems= cartItemModelList.get(position).getTotalitems();
                String totalItemsPrice= cartItemModelList.get(position).getTotalItemsPrice();
                String deliveryPrice= cartItemModelList.get(position).getDeliveryPrice();
                String totalAmount= cartItemModelList.get(position).getTotalAmount();
                String savedAmount= cartItemModelList.get(position).getSavedAmount();

                ((CartTotalAmountViewholder)viewHolder).setTotalAmount(totalItems,totalItemsPrice,deliveryPrice,totalAmount,savedAmount);

                break;

        }
    }

    @Override
    public int getItemCount() {
         return cartItemModelList.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private ImageView freeCouponsIcon;
        private TextView productTitle;
        private TextView freeCoupons;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView CouponsApplied;
        private TextView offersApplied;
        private TextView productQuantity;
        private Button remove_item_btn;
        private TextView total_cart_pricebox;
        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage= itemView.findViewById(R.id.product_image);
            productTitle=itemView.findViewById(R.id.product_title);
            freeCouponsIcon=itemView.findViewById(R.id.free_coupon_icon);
            freeCoupons=itemView.findViewById(R.id.tv_free_coupon);
            productPrice=itemView.findViewById(R.id.product_price);
            offersApplied=itemView.findViewById(R.id.offers_applied);
            CouponsApplied=itemView.findViewById(R.id.paymet_method);
            cuttedPrice=itemView.findViewById(R.id.cutted_price);
            productQuantity=itemView.findViewById(R.id.product_quantity);
            remove_item_btn=itemView.findViewById(R.id.remove_item_btn);
        }
        @SuppressLint("SetTextI18n")
        private void setItemDetails(final int prodid, String resource, String title, int freeCouponsNum, String productPriceText, String cuttedPriceText, int offersappliedNumber){
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
            if(freeCouponsNum > 0){
                freeCouponsIcon.setVisibility(View.VISIBLE);
                freeCoupons.setVisibility(View.VISIBLE);
                if(freeCouponsNum==1) {
                    freeCoupons.setText("free " + freeCouponsNum + "Coupon");
                }else{
                    freeCoupons.setText("free "+freeCouponsNum+"Coupons");
                }
            }else{
                freeCouponsIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);

            }
            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
            if(offersappliedNumber>0){
                offersApplied.setVisibility(View.VISIBLE);
                offersApplied.setText(offersappliedNumber+" offers Applied ");
            }else{
                offersApplied.setVisibility(View.INVISIBLE);
            }

            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog quantityDialog=new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);
                    final EditText quantityNo=quantityDialog.findViewById(R.id.quantity_number);
                    Button cancelBtn=quantityDialog.findViewById(R.id.cancel_btn);
                    Button okBtn=quantityDialog.findViewById(R.id.ok_btn);

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productQuantity.setText("Qty:"+ quantityNo.getText());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();
                }
            });
            remove_item_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //code by prashant
                        String url2=LinkClass.getLink()+"/CartRemoveServlet?username="+user+"&prodid="+prodid;

                    String sb=DataFetcher.fetchUrl(url2);
                        Toast.makeText(itemView.getContext(),parse(sb.toString()),Toast.LENGTH_SHORT).show();


                    //Toast.makeText(itemView.getContext(),"Delete",Toast.LENGTH_SHORT).show();
                    int noofitems;
                    double savmrp,totprice;
                    for(int i=0;i<cartItemModelList.size();i++){
                        if(prodid==cartItemModelList.get(i).getProdid()){
                            CartItemModel cc=cartItemModelList.get(cartItemModelList.size()-1),dd=cartItemModelList.get(i);
                            cartItemModelList.remove(cartItemModelList.size()-1);
                            String ss=cc.getTotalitems();
                            noofitems=Integer.parseInt(ss.substring(ss.indexOf("(")+1,ss.indexOf(" Items)")))-1;
                            ss=cc.getTotalAmount();
                            totprice=Double.parseDouble(ss.substring(ss.indexOf(".")+1,ss.indexOf("/-")));
                            ss=cc.getSavedAmount();
                            savmrp=Double.parseDouble(ss.substring(ss.indexOf(": ")+2,ss.indexOf("/-")));
                            System.out.println("----------------  no="+noofitems+"   sav"+savmrp+"   totp"+totprice);
                            ss=dd.getProductPrice();
                            totprice-=Double.parseDouble(ss.substring(ss.indexOf(".")+1,ss.indexOf("/-")));
                            savmrp+=Double.parseDouble(ss.substring(ss.indexOf(".")+1,ss.indexOf("/-")));
                            ss=dd.getCuttedPrice();
                            savmrp-=Double.parseDouble(ss.substring(ss.indexOf(".")+1,ss.indexOf("/-")));
                            cartItemModelList.add(new CartItemModel(1,"Price: ("+noofitems+" Items)","Rs."+totprice+"/-","Free","Rs."+totprice+"/-","Saved Price : "+(savmrp)+"/-"));
                            System.out.println("no="+noofitems+"   sav"+savmrp+"   totp"+totprice);
                            cartItemModelList.remove(i);
                            break;
                        }

                    }
                    refreshdataset();

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
    }

    public void refreshdataset(){

        this.notifyDataSetChanged();
    }
    class CartTotalAmountViewholder extends RecyclerView.ViewHolder{

        private TextView totalItems;
        private TextView totalItemPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;

        public CartTotalAmountViewholder(@NonNull View itemView) {
            super(itemView);

            totalItems=itemView.findViewById(R.id.total_items);
            totalItemPrice=itemView.findViewById(R.id.total_items_price);
            deliveryPrice=itemView.findViewById(R.id.delivery_price);
            totalAmount=itemView.findViewById(R.id.total_price);
            savedAmount=itemView.findViewById(R.id.saved_amount);
        }
        private void setTotalAmount(String totalItemText,String totalitempriceText,String deliveryText,String totalamounttext,String savedAmountText){
            totalItems.setText(totalItemText);
            totalItemPrice.setText(totalitempriceText);
            deliveryPrice.setText(deliveryText);
            totalAmount.setText(totalamounttext);
            savedAmount.setText(savedAmountText);
        }
    }
}
