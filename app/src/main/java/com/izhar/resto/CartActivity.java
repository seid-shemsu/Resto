package com.izhar.resto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
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
        recycle = findViewById(R.id.recycle);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        foods = (ArrayList<Food>) args.getSerializable("foods");
        foods.removeAll(Arrays.asList("", null));
        cartAdapter = new CartAdapter(foods, this);
        recycle.setAdapter(cartAdapter);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loader.setVisibility(View.VISIBLE);
                order.setEnabled(false);
                recycle.setEnabled(false);
                quantities = cartAdapter.quantities;
                quantities.removeAll(Arrays.asList(0, null));
                List<Food> foodList = new ArrayList<>();
                for (int i = 0; i < foods.size(); i++) {
                    foodList.add(new Food(foods.get(i).getName(), foods.get(i).getPrice(), Integer.toString(quantities.get(i))));
                }
                SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
                String name = user.getString("name", "username");
                SimpleDateFormat sdp = new SimpleDateFormat("hh:mm");
                String id = System.currentTimeMillis() + "";
                DatabaseReference ordered = FirebaseDatabase.getInstance().getReference().child("ordered").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "");
                ordered.child(id).setValue(new Request(foodList, cartAdapter.getTotal() + "", sdp.format(new Date()), name));
                ordered = FirebaseDatabase.getInstance().getReference("waiter").child("request").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "");
                ordered.child(id).setValue(new Request(foodList, cartAdapter.getTotal() + "", sdp.format(new Date()), name));
                DatabaseReference pending = FirebaseDatabase.getInstance().getReference().child("cashier").child("request").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "");
                pending.child(id).setValue(new Request(foodList, cartAdapter.getTotal() + "", sdp.format(new Date()), name)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(CartActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }

        });

    }
}
