package com.example.mymallapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import java.util.ArrayList;
import java.util.List;

import static com.example.mymallapp.DeliveryActivity.SELECT_ADDRESS;

public class ActivityMyaddresses extends AppCompatActivity {

    private RecyclerView myAddressesREcyclerView;
    private Button deliverHereBtn;
    private static AddressesAdapter addressesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaddresses);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myAddressesREcyclerView=findViewById(R.id.addresses_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myAddressesREcyclerView.setLayoutManager(layoutManager);
        deliverHereBtn=findViewById(R.id.deliver_hereBtn);

        List<AddressesModel> addressesModelList= new ArrayList<>();
        addressesModelList.add(new AddressesModel("Kunal padia","301,samrat appartments","390002",true));
        addressesModelList.add(new AddressesModel("Kunal padia","301,samrat appartments","390002",false));
        addressesModelList.add(new AddressesModel("Kunal padia","301,samrat appartments","390001",false));
        addressesModelList.add(new AddressesModel("Kunal padia","301,samrat appartments","390003",false));
        addressesModelList.add(new AddressesModel("Kunal padia","301,samrat appartments","390004",false));


        int mode=getIntent().getIntExtra("MODE",-1);
        if(mode==SELECT_ADDRESS){
            deliverHereBtn.setVisibility(View.VISIBLE);
        }else{
            deliverHereBtn.setVisibility(View.GONE);
        }
        addressesAdapter=new AddressesAdapter(addressesModelList,mode);
        myAddressesREcyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)myAddressesREcyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();
    }

    public static void refreshItem(int deselect,int select){
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
