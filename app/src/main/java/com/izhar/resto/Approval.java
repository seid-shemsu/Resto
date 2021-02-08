package com.izhar.resto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.izhar.resto.R;
import com.izhar.resto.ui.adapters.ApprovalAdapter;
import com.izhar.resto.ui.objects.Food;
import com.izhar.resto.ui.objects.Request;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Approval extends AppCompatActivity {

    RecyclerView recycle;
    Button approve;
    LottieAnimationView loader;
    TextView total;
    List<Food> foods = new ArrayList<>();
    String id;
    SharedPreferences user ;
    ApprovalAdapter adapter;
    Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        id = getIntent().getExtras().getString("orderId");
        user = getSharedPreferences("user", MODE_PRIVATE);
        approve = findViewById(R.id.approve);
        loader = findViewById(R.id.loader);
        total = findViewById(R.id.total_price);
        recycle = findViewById(R.id.recycle);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        getData();
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String next = getNext();
                DatabaseReference pending = FirebaseDatabase.getInstance().getReference(user.getString("user", "admin")).child("pending").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date())).child(id);
                DatabaseReference finished = FirebaseDatabase.getInstance().getReference(user.getString("user", "admin")).child("finished").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date())).child(id);
                finished.setValue(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Approval.this, "finished", Toast.LENGTH_SHORT).show();
                    }
                });
                pending.removeValue();
                pending = FirebaseDatabase.getInstance().getReference(next).child("pending").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date())).child(id);
                pending.setValue(request);
                if (user.getString("user", "admin").equalsIgnoreCase("waiter")){
                    finished = FirebaseDatabase.getInstance().getReference().child("finished").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date())).child(id);
                    finished.setValue(request);
                }
                startActivity(new Intent(Approval.this, MainActivity.class));
                finish();
            }
        });
    }

    private String getNext() {
        switch (user.getString("user", "admin")){
            case "cashier":
                return "cooker";
            case "cooker":
                return "waiter";
            default:
                return "admin";
        }
    }

    private void getData() {
        final DatabaseReference data = FirebaseDatabase.getInstance().getReference(user.getString("user", "admin")).child("pending").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date())).child(id);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                request = dataSnapshot.getValue(Request.class);
                for (DataSnapshot snapshot : dataSnapshot.child("foods").getChildren()){
                    foods.add(snapshot.getValue(Food.class));
                }
                total.setText(dataSnapshot.child("total").getValue().toString() + " ETB");
                adapter = new ApprovalAdapter(foods, Approval.this);
                loader.setVisibility(View.GONE);
                recycle.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
