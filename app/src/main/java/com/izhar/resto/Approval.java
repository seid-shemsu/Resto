package com.izhar.resto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.izhar.resto.R;

public class Approval extends AppCompatActivity {

    RecyclerView recycle;
    Button approve;
    LottieAnimationView loader;
    TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        approve = findViewById(R.id.approve);
        loader = findViewById(R.id.loader);
        total = findViewById(R.id.total_price);
        recycle = findViewById(R.id.recycle);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
    }
}
