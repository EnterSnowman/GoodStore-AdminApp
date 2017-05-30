package com.example.android.goodstore_adminapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.goodstore_adminapp.ProductsActivity;
import com.example.android.goodstore_adminapp.R;

import java.util.ArrayList;

/**
 * Created by Valentin on 30.05.2017.
 */

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    ArrayList<String> categories;
    Context context;
    public CategoryAdapter(ArrayList<String> categories,Context context) {
        this.categories = categories;
        this.context = context;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        CategoryAdapter.CategoryHolder vh = new CategoryAdapter.CategoryHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        holder.category.setText(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        TextView category;
        public CategoryHolder(View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductsActivity.class);
                    intent.putExtra("category",categories.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
