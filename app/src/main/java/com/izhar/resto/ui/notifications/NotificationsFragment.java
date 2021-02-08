package com.izhar.resto.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.izhar.resto.R;
import com.izhar.resto.ui.dashboard.PagerAdapter;

public class NotificationsFragment extends Fragment {
    View root;
    TabLayout tab;
    ViewPager view;
    PagerAdapter pagerAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences user = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        if (user.getString("user", "admin").equalsIgnoreCase("cooker")){
            root = inflater.inflate(R.layout.fragment_notifications, container, false);
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
        else{
            root = inflater.inflate(R.layout.not_access, container, false);
        }
        return root;
    }
}
