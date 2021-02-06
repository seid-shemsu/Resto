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
    Map<Food, Integer> amount = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        order = findViewById(R.id.btn_order);
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
                for (int i = 0 ; i < foods.size(); i++){
                    Toast.makeText(CartActivity.this, foods.get(i).getName() + " " + cartAdapter.quantities.get(i), Toast.LENGTH_SHORT).show();
                }
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantities = cartAdapter.quantities;
                quantities.removeAll(Arrays.asList(0,null));
                List<Food> foodList = new ArrayList<>();
                for (int i = 0 ; i < foods.size(); i++){
                    foodList.add(new Food(foods.get(i).getName(), foods.get(i).getPrice(), Integer.toString(quantities.get(i))));
                }
                DatabaseReference pending = FirebaseDatabase.getInstance().getReference().child("pending").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) +"");
                SimpleDateFormat sdp = new SimpleDateFormat("hh:mm");
                pending.child(System.currentTimeMillis() + "").setValue(new Request(foodList, cartAdapter.getTotal() +"", sdp.format(new Date()), table_number.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
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
