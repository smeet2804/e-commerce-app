package com.example.mymallapp;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ProductImagesAdapter extends PagerAdapter {

    private List<String> productImages;

    public ProductImagesAdapter(List<String> productImages) {
        this.productImages = productImages;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        URL url = null;
        ImageView productImage=new ImageView(container.getContext());
        String resource=productImages.get(position);
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
        //productImage.setImageResource(productImages.get(position));
        container.addView(productImage,0);
        return productImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }

    @Override
    public int getCount() {
        return productImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}
