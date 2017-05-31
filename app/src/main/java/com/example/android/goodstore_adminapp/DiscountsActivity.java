package com.example.android.goodstore_adminapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.android.goodstore_adminapp.adapters.DiscountAdapter;
import com.example.android.goodstore_adminapp.models.Discount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscountsActivity extends AppCompatActivity {
    @BindView(R.id.listOfDiscounts)
    RecyclerView recyclerView;
    DiscountAdapter discountAdapter;
    ArrayList<Discount> discounts;
    DatabaseReference databaseReference,productsReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounts);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.discounts));
        discounts = new ArrayList<>();
        discountAdapter = new DiscountAdapter(discounts);
        recyclerView.setAdapter(discountAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productsReference = FirebaseDatabase.getInstance().getReference().child("products");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("discounts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    final String c = d.getKey().split("_")[0];
                    final String id = d.getKey().split("_")[1];
                    final float disc = d.getValue(Float.class);
                    productsReference.child(c).child(id).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            discounts.add(new Discount(c,dataSnapshot.getValue(String.class),disc,id));
                            discountAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
