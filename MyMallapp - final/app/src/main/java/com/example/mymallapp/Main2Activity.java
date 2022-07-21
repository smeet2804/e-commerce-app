package com.example.mymallapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import static com.example.mymallapp.RegisterActivity.setSignupFragment;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        private NavigationView navigationView;
        private FrameLayout frameLayout;
        private ImageView action_bar_logo;
        private int current_Fragment=-1;
        private AppBarConfiguration mAppBarConfiguration;
        private static final int HOME_FRAGMENT=0;
        private static final int CART_FRAGMENT=1;
        private static final int ORDERS_FRAGMENT=2;
        private static final int ACCOUNT_FRAGMENT=3;
        private static final int CHAT_FRAGMENT=4;
        private static final int SEARCH_FRAGMENT=5;
        private TextView main_full_name,main_email;
        private Window window;
        private Toolbar toolbar;
        private String user;
        public static boolean showCart=false;

        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        navigationView = findViewById(R.id.nav_view);
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main2, navigationView, false);
        main_email=headerView.findViewById(R.id.main_email);
        main_full_name=headerView.findViewById(R.id.main_full_name);
        main_email.setText("asadadassad@assad");
        main_full_name.setText("asasdsaa");

            System.out.println(" ::::::::::  "+main_email.getText()+"============================================");
            System.out.println(" ::::::::::  "+main_full_name.getText()+"============================================");
        setContentView(R.layout.activity_main2);
        toolbar = findViewById(R.id.toolbar);
        action_bar_logo=findViewById(R.id.action_bar_logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);


            navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            ConstraintLayout navheadermain=findViewById(R.id.nav_header2);
            navigationView.getMenu().getItem(0).setChecked(true);
            headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main2, navigationView, false);
            //navigationView.addHeaderView(headerView);
            main_email=headerView.findViewById(R.id.main_email);
            main_full_name=headerView.findViewById(R.id.main_full_name);
            System.out.println(" ::::::::::  "+main_email.getText()+"============================================");
            SessionClass sessionManagement = new SessionClass(this);
            user = sessionManagement.getSession().toString();

            System.out.println("---"+user+"---");
            if(!user.equals("no")){
                System.out.println("-------LOLOLOLOL----------");
                main_email.setText("asadadassad@assad");
                main_full_name.setText(user.substring(0,user.indexOf("@")));
            }
            main_email.setVisibility(View.GONE);
            main_email.setVisibility(View.VISIBLE);
            main_full_name.setVisibility(View.GONE);
            main_full_name.setVisibility(View.VISIBLE);
            System.out.println(" ::::::::::  "+main_email.getText()+"============================================");
            System.out.println(" ::::::::::  "+main_full_name.getText()+"============================================");

            window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        frameLayout=findViewById(R.id.main_frame_layout);
            if(showCart){
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("My Cart",new MyCartFragment(),-2);
        }else{
            ActionBarDrawerToggle toggle;
            toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(),HOME_FRAGMENT);
            }
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
            if(current_Fragment==HOME_FRAGMENT){
                Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
                getMenuInflater().inflate(R.menu.main2,menu);
            }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            if(current_Fragment == HOME_FRAGMENT){
                current_Fragment=-1;
                super.onBackPressed();
            }else {
                if (showCart) {
                    showCart=false;
                    finish();
                } else {
                    action_bar_logo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    navigationView.getMenu().getItem(0).setChecked(true);
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                }
            }

        }
    }

    @Override
        public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        System.out.println("========== in main ======================================");
        if(user.equals("no")){
            //user id logged in and so move to mainActivity
            Intent mainIntent= new Intent(this,RegisterActivity.class);
            startActivity(mainIntent);
            finish();
        }
        if (id == R.id.main_search_icon) {
            gotoFragment("Search",new MySearchFragment(),SEARCH_FRAGMENT);
            return true;
        } else if (id == R.id.main_cart_icon) {
            gotoFragment("My Cart",new MyCartFragment(),CART_FRAGMENT);
            return true;
        }else if(id==android.R.id.home){
            if(showCart){
                showCart=false;
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void gotoFragment(String title,Fragment fragment,int fragmentno) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(title);
            action_bar_logo.setVisibility(View.GONE);
            invalidateOptionsMenu();
            setFragment(fragment,fragmentno);
            if(fragmentno==CART_FRAGMENT) {
                navigationView.getMenu().getItem(3).setChecked(true);
            }
            else if(fragmentno==ORDERS_FRAGMENT){
                navigationView.getMenu().getItem(2).setChecked(true);
            }
            else if(fragmentno==SEARCH_FRAGMENT){
                navigationView.getMenu().getItem(1).setChecked(true);
            }
            else if(fragmentno==ACCOUNT_FRAGMENT){
                navigationView.getMenu().getItem(4).setChecked(true);
            }
            else if(fragmentno==CHAT_FRAGMENT){
                navigationView.getMenu().getItem(5).setChecked(true);
            }
    }


    @Override
        public boolean onSupportNavigateUp () {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_my_mall) {
            action_bar_logo.setVisibility(View.VISIBLE);
            invalidateOptionsMenu();
            setFragment(new HomeFragment(),HOME_FRAGMENT);
        } else if (id == R.id.nav_my_orders) {
            gotoFragment("Recent Orders",new MyOrdersFragment(),ORDERS_FRAGMENT);

        } else if (id== R.id.nav_search){
            gotoFragment("Search",new MySearchFragment(),SEARCH_FRAGMENT);
        }
         else if (id == R.id.nav_my_cart) {

            gotoFragment("Cart",new MyCartFragment(),CART_FRAGMENT);

        } else if (id == R.id.nav_my_account) {
            gotoFragment("Account",new MyAccountFragment(),ACCOUNT_FRAGMENT);

        }
        else if (id == R.id.nav_chat) {
            //gotoFragment("Whatsapp Us",new MyAccountFragment(),CHAT_FRAGMENT);
            DataFetcher.goToUrl(this,"https://wa.me/919429091718");
        }
        else if(id == R.id.nav_logout) {
            System.out.println("In Log out");
            SessionClass sessionManagement = new SessionClass(this);
            sessionManagement.removeSession();
            Intent mainIntent= new Intent(this,RegisterActivity.class);
            startActivity(mainIntent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    private void setFragment (Fragment fragment,int fragmentNum) {
        if (fragmentNum != current_Fragment) {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            current_Fragment = fragmentNum;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }
    }
}

