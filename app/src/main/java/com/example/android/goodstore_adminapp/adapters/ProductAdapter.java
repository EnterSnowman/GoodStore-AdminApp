package com.example.android.goodstore_adminapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.android.goodstore_adminapp.R;
import com.example.android.goodstore_adminapp.models.Product;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by Valentin on 24.04.2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    String category;
    ArrayList<Product> products;
    Context context;
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ProductAdapter.ProductHolder vh = new ProductAdapter.ProductHolder(v);
        return vh;
    }

    public ProductAdapter(ArrayList<Product> products, Context context) {

        this.category = category;
        this.products = products;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ProductHolder holder, int position) {
        ImageLoader imageLoader = ImageLoader.getInstance(); // Получили экземпляр
        imageLoader.init(ImageLoaderConfiguration.createDefault(context)); // Проинициализировали конфигом по умолчанию
        imageLoader.displayImage(products.get(position).getPhoto_url(), holder.productPhoto); // Запустили асинхронный показ картинки
        holder.productName_tv.setText(products.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        TextView productName_tv;
        ImageView productPhoto;
        public ProductHolder(View itemView) {
            super(itemView);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("category",category);
                    intent.putExtra("product_id",products.get(getAdapterPosition()).getId());
                    intent.putExtra("product_name",products.get(getAdapterPosition()).getName());
                    context.startActivity(intent);
                }
            });*/
            productName_tv = (TextView) itemView.findViewById(R.id.product_name);
            productPhoto = (ImageView) itemView.findViewById(R.id.product_photo);
        }
    }
}
