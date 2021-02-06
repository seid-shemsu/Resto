package com.izhar.resto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.izhar.resto.ui.objects.Food;

public class AddFood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        final Button add = findViewById(R.id.add);
        final EditText name = findViewById(R.id.name);
        final EditText price = findViewById(R.id.price);
        final EditText table_number = findViewById(R.id.table_number);
        final LottieAnimationView loader = findViewById(R.id.loader);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.setVisibility(View.GONE);
                loader.setVisibility(View.VISIBLE);
                DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("items");
                data.child(System.currentTimeMillis() + "").setValue(new Food(name.getText().toString(), price.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                    }
                });
            }
        });
    }
}
