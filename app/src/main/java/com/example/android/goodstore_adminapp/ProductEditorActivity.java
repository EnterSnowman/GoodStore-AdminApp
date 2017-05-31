package com.example.android.goodstore_adminapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.Toast;

import com.example.android.goodstore_adminapp.models.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductEditorActivity extends AppCompatActivity {
    @BindView(R.id.category_spinner)
    Spinner category;
    @BindView(R.id.input_category)
    EditText inputCategory;
    @BindView(R.id.input_product_name)
    EditText name;
    @BindView(R.id.input_price)
    EditText price;
    @BindView(R.id.input_description)
    EditText description;
    @BindView(R.id.input_chars)
    EditText chars;
    @BindView(R.id.isAvailable)
    Switch isAvailvable;
    DatabaseReference mDatabase;
    ArrayAdapter<String> spinnerAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_editor);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        if (getIntent().getStringExtra("action").equals("add")) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("products");
            getSupportActionBar().setTitle(R.string.add_new_product);
            category.setVisibility(View.VISIBLE);
            final ArrayList<String> c = getIntent().getStringArrayListExtra("categories");
            c.add(getString(R.string.new_category));
            spinnerAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner_item, c);
            category.setAdapter(spinnerAdapter);
            category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == c.size() - 1)
                        inputCategory.setVisibility(View.VISIBLE);
                    else
                        inputCategory.setVisibility(View.GONE);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if (getIntent().getStringExtra("action").equals("edit")) {
            progressDialog.show();
            mDatabase = FirebaseDatabase.getInstance().getReference()
                    .child("products")
                    .child(getIntent().getStringExtra("category"))
                    .child(getIntent().getStringExtra("product_id"));
            initUI();
        }

    }

    public void initUI() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                name.setText(product.getName());
                price.setText(String.valueOf(product.getPrice()));
                description.setText(product.getDescription());
                chars.setText(product.getCharacteristics());
                isAvailvable.setChecked(product.isAvailable());
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean isAllFieldsFilled() {
        return (!name.getText().equals("") && !price.getText().equals("") && !description.getText().equals("") && !chars.getText().equals(""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.confirm_add_product) {
            if (getIntent().getStringExtra("action").equals("add"))
                if (isAllFieldsFilled()) {
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    hashMap.put("name", name.getText().toString());
                    hashMap.put("price", Float.valueOf(price.getText().toString()));
                    hashMap.put("description", description.getText().toString());
                    hashMap.put("characteristics", chars.getText().toString());
                    hashMap.put("isAvailable", isAvailvable.isChecked());
                    if (((String) category.getSelectedItem()).equals(getString(R.string.new_category)))
                        mDatabase.child(inputCategory.getText().toString()).push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProductEditorActivity.this, "Product added", Toast.LENGTH_SHORT).show();
                            }
                        });
                    else
                        mDatabase.child((String) category.getSelectedItem()).push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProductEditorActivity.this, "Product added", Toast.LENGTH_SHORT).show();
                            }
                        });
                } else
                    Toast.makeText(ProductEditorActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            if (getIntent().getStringExtra("action").equals("edit")) {
                if (isAllFieldsFilled()) {
                    if (isAllFieldsFilled()) {
                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        hashMap.put("name", name.getText().toString());
                        hashMap.put("price", Float.valueOf(price.getText().toString()));
                        hashMap.put("description", description.getText().toString());
                        hashMap.put("characteristics", chars.getText().toString());
                        hashMap.put("isAvailable", isAvailvable.isChecked());
                        mDatabase.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProductEditorActivity.this, "Product updated", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                //Intent intent = new Intent(this,ProductEditorActivity.class);
                //startActivity(intent);

            }
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
