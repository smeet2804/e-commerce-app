package com.example.mymallapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool=new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()){
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
                default:
                    return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        switch (viewType){
            case HomePageModel.BANNER_SLIDER:
                View bannerSliderView= LayoutInflater.from((viewGroup.getContext())).inflate(R.layout.sliding_ad_layout,viewGroup,false);
                return new BannerSliderViewholder(bannerSliderView);
            case HomePageModel.STRIP_AD_BANNER:
                View  stripAdview= LayoutInflater.from((viewGroup.getContext())).inflate(R.layout.strip_ad_layout,viewGroup,false);
                return new StripAdBannerViewHolder(stripAdview);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View  horizontalAdview= LayoutInflater.from((viewGroup.getContext())).inflate(R.layout.horizontal_scroll_layout,viewGroup,false);
                return new HorizontalProductViewholder(horizontalAdview);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View  GridAdview= LayoutInflater.from((viewGroup.getContext())).inflate(R.layout.grid_product_layout,viewGroup,false);
                return new GridProductViewHolder(GridAdview);

            default:
                    return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
      switch (homePageModelList.get(position).getType()){
          case HomePageModel.BANNER_SLIDER:
              List<SliderModel> sliderModelList=homePageModelList.get(position).getSliderModelList();
              ((BannerSliderViewholder)viewHolder).setBannerSliderViewPager(sliderModelList);
              break;

              case HomePageModel.STRIP_AD_BANNER:
                  int resource=homePageModelList.get(position).getResource();
                  String color= homePageModelList.get(position).getBackgroundColor();
                  ((StripAdBannerViewHolder)viewHolder).setStripAd(resource,color);
                  break;
          case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String horizontalLayouttitle=homePageModelList.get(position).getTitle();
                List<HorizontalProductModel> horizontalProductModelList=homePageModelList.get(position).getHorizontalProductModelList();
              ((HorizontalProductViewholder)viewHolder).setHorizontalProductLayout(horizontalProductModelList,horizontalLayouttitle);
              break;
          case HomePageModel.GRID_PRODUCT_VIEW:
              String gridLayouttitle=homePageModelList.get(position).getTitle();
              List<HorizontalProductModel> gridProductModelList=homePageModelList.get(position).getHorizontalProductModelList();
              ((GridProductViewHolder)viewHolder).setGridProductLayout(gridProductModelList,gridLayouttitle);

              break;

              default:
                  return;
      }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class BannerSliderViewholder extends RecyclerView.ViewHolder{


        private ViewPager bannerSliderViewPager;
        private int CurrentPage;

        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME= 3000;
        private List<SliderModel> arrangedList;
        public BannerSliderViewholder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager=itemView.findViewById(R.id.banner_slider_view_pager);


        }
        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList){
            CurrentPage=2;
            if(timer !=null){
                timer.cancel();
            }
            arrangedList=new ArrayList<>();
            for(int x=0;x < sliderModelList.size();x++){
                arrangedList.add(x,sliderModelList.get(x));
            }
            arrangedList.add(0,sliderModelList.get(sliderModelList.size()-2));
            arrangedList.add(0,sliderModelList.get(sliderModelList.size()-1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));


            SliderAdapter sliderAdapter=new SliderAdapter(arrangedList);
            bannerSliderViewPager.setAdapter(sliderAdapter);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);

            bannerSliderViewPager.setCurrentItem(CurrentPage);

            ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int i) {
                    CurrentPage=i;
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                    if(i==ViewPager.SCROLL_STATE_IDLE){
                        pageLooper(arrangedList);
                    }
                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
            ////////BannerSlider

            startBannerSlideShow(arrangedList);

            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(arrangedList);
                    stopBannerSlideShow();
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        startBannerSlideShow(arrangedList);
                    }
                    return false;
                }
            });
        }
        private void pageLooper(List<SliderModel> sliderModelList){
            if(CurrentPage== sliderModelList.size()-2){
                CurrentPage=2;
                bannerSliderViewPager.setCurrentItem(CurrentPage,false);
            }
            if(CurrentPage== 1){
                CurrentPage=sliderModelList.size()-3;
                bannerSliderViewPager.setCurrentItem(CurrentPage,false);
            }
        }
        private void startBannerSlideShow(final List<SliderModel> sliderModelList){
            final Handler handler=new Handler();
            final Runnable update= new Runnable() {
                @Override
                public void run() {
                    if(CurrentPage>=sliderModelList.size()){
                        CurrentPage=1;
                    }
                    bannerSliderViewPager.setCurrentItem(CurrentPage++);
                }
            };
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            },DELAY_TIME,PERIOD_TIME);

        }
        private void stopBannerSlideShow(){
            timer.cancel();
        }
    }

    public class StripAdBannerViewHolder extends RecyclerView.ViewHolder{

        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;


        public StripAdBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            stripAdImage=itemView.findViewById(R.id.strip_ad_Image);
            stripAdContainer=itemView.findViewById(R.id.strip_ad_container);


        }

        private void setStripAd(int resource,String color){
            stripAdImage.setImageResource(resource);
            stripAdImage.setBackgroundColor(Color.parseColor(color));

        }


    }

    public class HorizontalProductViewholder extends RecyclerView.ViewHolder{
        private TextView horizontallayoutTitle;
        private Button horizontalviewAllButton;
        private RecyclerView horizontalrecyclerView;

        public HorizontalProductViewholder(@NonNull View itemView) {
            super(itemView);
            horizontallayoutTitle=itemView.findViewById(R.id.Horizontal_scroll_layout_title);
            horizontalviewAllButton=itemView.findViewById(R.id.Horizontal_scroll_layout_viewAllbutton);
            horizontalrecyclerView=itemView.findViewById(R.id.Horizontal_scroll_layout_recyler_view);
            horizontalrecyclerView.setRecycledViewPool(recycledViewPool);

        }
        private void setHorizontalProductLayout(List<HorizontalProductModel> horizontalProductModelList,String title){

            horizontallayoutTitle.setText(title);

            if(horizontalProductModelList.size()>8){
                horizontalviewAllButton.setVisibility(View.VISIBLE);
                horizontalviewAllButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
//                        viewAllIntent.putExtra("layout_code",0);
//                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            }
            else{
                horizontalviewAllButton.setVisibility(View.INVISIBLE);
            }
            HorizontalProductScrollAdapter horizontalProductScrollAdapter= new HorizontalProductScrollAdapter(horizontalProductModelList);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalrecyclerView.setLayoutManager(linearLayoutManager);

            horizontalrecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    public class GridProductViewHolder extends RecyclerView.ViewHolder{

        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllbtn;
        private GridLayout gridProductLayout;
        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle=itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllbtn=itemView.findViewById(R.id.grid_product_layout_viewall_btn);
            gridProductLayout=itemView.findViewById(R.id.grid_layout);
        }
        private void setGridProductLayout(final List<HorizontalProductModel> horizontalProductModelList,String title){
            gridLayoutTitle.setText(title);

            for(int x=0;x<4&&x<horizontalProductModelList.size();x++){
                ImageView productImage= gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_image);
                TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_title);
                TextView productDescription = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_description);
                TextView productPrice = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_price);
                URL url = null;
                try {
                    String resource=horizontalProductModelList.get(x).getProductImage();
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
                productTitle.setText(horizontalProductModelList.get(x).getProductTitle());
                productDescription.setText(horizontalProductModelList.get(x).getProductDescription());
                productPrice.setText(horizontalProductModelList.get(x).getProductPrice());
                gridProductLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));
                final int xx=x;
                gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent producDetailsIntent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                        System.out.println(" prodid ===============   "+horizontalProductModelList.get(xx).getProdid());
                        producDetailsIntent.putExtra("Prodid",""+horizontalProductModelList.get(xx).getProdid());
                        itemView.getContext().startActivity(producDetailsIntent);
                    }
                });
            }

            gridLayoutViewAllbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code",1);
                    itemView.getContext().startActivity(viewAllIntent);
                }
            });
        }
    }
}

