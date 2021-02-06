package com.izhar.resto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    RecyclerView recycle;
    CartAdapter cartAdapter;
    ArrayList<Integer> quantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        order = findViewById(R.id.btn_order);
        recycle = findViewById(R.id.recycle);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        foods =  (ArrayList<Food>)args.getSerializable("items");
        foods.removeAll(Arrays.asList("", null));
        cartAdapter = new CartAdapter(foods, this);
        recycle.setAdapter(cartAdapter);
        quantities = cartAdapter.quantities;
        quantities.removeAll(Arrays.asList(0,null));
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference pendings = FirebaseDatabase.getInstance().getReference().child("pending").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) +"");
                SimpleDateFormat sdp = new SimpleDateFormat("hh:mm");
                pendings.child(System.currentTimeMillis() + "").setValue(new Request(foods, cartAdapter.getTotal() +"", sdp.format(new Date()))).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(CartActivity.this, MainActivity.class));
                        finish();
                    }
                });
                Toast.makeText(CartActivity.this, cartAdapter.quantities + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
