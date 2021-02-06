package com.izhar.resto.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.izhar.resto.AddFood;
import com.izhar.resto.R;
import com.izhar.resto.OrderActivity;
import com.izhar.resto.ui.adapters.OrderAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView pending_text, finished_text;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button new_order = root.findViewById(R.id.new_order);
        Button add = root.findViewById(R.id.add_food);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddFood.class));
            }
        });
        new_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OrderActivity.class));
            }
        });

        pending_text = root.findViewById(R.id.pending);
        finished_text = root.findViewById(R.id.finished);
        setValues();
        return root;
    }

    private void setValues() {
        final DatabaseReference pending = FirebaseDatabase.getInstance().getReference().child("pending").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        DatabaseReference finished = FirebaseDatabase.getInstance().getReference().child("finished").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        pending.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()){
                    pending_text.setText(dataSnapshot.getChildrenCount() + "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        finished.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren())
                    finished_text.setText(dataSnapshot.getChildrenCount() + "");
                else
                    finished_text.setText("0");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
