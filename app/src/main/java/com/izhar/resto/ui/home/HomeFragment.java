package com.izhar.resto.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.izhar.resto.AddFood;
import com.izhar.resto.R;
import com.izhar.resto.OrderActivity;
import com.izhar.resto.ui.adapters.OrderAdapter;
import com.izhar.resto.ui.dashboard.PagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    TextView pending_text, finished_text;
    View root;
    TabLayout tab;
    ViewPager view;
    PagerAdapter pagerAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences user = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        if (user.getString("user", "admin").equalsIgnoreCase("cashier")){
            root = inflater.inflate(R.layout.fragment_home, container, false);
            pending_text = root.findViewById(R.id.pending);
            finished_text = root.findViewById(R.id.finished);
            setValues();
            tab = root.findViewById(R.id.tab);
            view = root.findViewById(R.id.viewpager);
            pagerAdapter = new PagerAdapter(getFragmentManager(), tab.getTabCount(), user.getString("user", "admin"));
            view.setAdapter(pagerAdapter);
            view.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
            tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    view.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
        else {
            root = inflater.inflate(R.layout.not_access, container, false);
        }
        return root;
    }

    private void setValues() {
        final DatabaseReference request = FirebaseDatabase.getInstance().getReference("cashier").child("request").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        DatabaseReference approved = FirebaseDatabase.getInstance().getReference("cashier").child("approved").child(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        request.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    pending_text.setText(dataSnapshot.getChildrenCount() + "");
                }
                else
                    pending_text.setText("0");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        approved.addValueEventListener(new ValueEventListener() {
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
