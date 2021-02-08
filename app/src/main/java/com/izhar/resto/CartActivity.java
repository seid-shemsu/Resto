package com.izhar.resto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.izhar.resto.ui.adapters.CartAdapter;
import com.izhar.resto.ui.objects.Food;
import com.izhar.resto.ui.objects.Request;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    List<Food> foods;
    Button order;
    EditText table_number;
    RecyclerView recycle;
    CartAdapter cartAdapter;
    ArrayList<Integer> quantities;
    LottieAnimationView loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Cart");
        setContentView(R.layout.activity_cart);
        order = findViewById(R.id.btn_order);
        loader = findViewById(R.id.loader);
        table_number = findViewById(R.id.table_number);
        recycle = findViewById(R.id.recycle);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        foods =  (ArrayList<Food>)args.getSerializable("foods");
        foods.removeAll(Arrays.asList("", null));
        cartAdapter = new CartAdapter(foods, this);
        recycle.setAdapter(cartAdapter);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (table_number.getText().toString().length() == 0){
                    Toast.makeText(CartActivity.this, "enter table number", Toast.LENGTH_SHORT).show();
                }
                else {
                    loader.setVisibility(View.VISIBLE);
                    table_number.setEnabled(false);
                    order.setEnabled(false);
                    recycle.setEnabled(false);
                    quantities = cartAdapter.quantities;
                    quantities.removeAll(Arrays.asList(0,null));
                    List<Food> foodList = new ArrayList<>();
                    for (int i = 0 ; i < foods.size(); i++){
                        foodList.add(new Food(foods.get(i).getName(), foods.get(i).getPrice(), Integer.toString(quantities.get(i))));
                    }
                    SimpleDateFormat sdp = new SimpleDateFormat("hh:mm");
                    DatabaseReference ordered = FirebaseDatabase.getInstance().getReference().child("ordered").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) +"");
                    ordered.child(System.currentTimeMillis() + "").setValue(new Request(foodList, cartAdapter.getTotal() +"", sdp.format(new Date()), table_number.getText().toString()));
                    DatabaseReference pending = FirebaseDatabase.getInstance().getReference().child("cooker").child("pending").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) +"");
                    pending.child(System.currentTimeMillis() + "").setValue(new Request(foodList, cartAdapter.getTotal() +"", sdp.format(new Date()), table_number.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(CartActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                }

            }
        });

    }
}
