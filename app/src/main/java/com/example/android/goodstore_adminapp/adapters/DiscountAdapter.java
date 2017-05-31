package com.example.android.goodstore_adminapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.goodstore_adminapp.R;
import com.example.android.goodstore_adminapp.models.Discount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Valentin on 31.05.2017.
 */

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountHolder> {
    ArrayList<Discount> discounts;
    DatabaseReference discountReference;
    public  DiscountAdapter(ArrayList<Discount> discounts){
        this.discounts = discounts;
        discountReference = FirebaseDatabase.getInstance().getReference().child("discounts");
    }
    @Override
    public DiscountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_item, parent, false);
        DiscountAdapter.DiscountHolder vh = new DiscountAdapter.DiscountHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(DiscountHolder holder, int position) {
        holder.name.setText(discounts.get(position).getName());
        holder.category.setText(discounts.get(position).getCategory());
        holder.discount.setText("-"+(discounts.get(position).getValue()*100)+"%");
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    public class DiscountHolder extends RecyclerView.ViewHolder {
        ImageView removeButton;
        TextView name,discount,category;
        public DiscountHolder(View itemView) {
            super(itemView);
            removeButton = (ImageView) itemView.findViewById(R.id.remove_discount);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    discountReference.child(discounts.get(getAdapterPosition()).getCategory()+"_"+discounts.get(getAdapterPosition()).getId())
                            .setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            discounts.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        }
                    });
                }
            });
            name = (TextView) itemView.findViewById(R.id.name_discount);
            discount = (TextView) itemView.findViewById(R.id.discount);
            category = (TextView) itemView.findViewById(R.id.category_discount);
        }
    }
}
