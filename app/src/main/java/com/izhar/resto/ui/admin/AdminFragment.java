package com.izhar.resto.ui.admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.izhar.resto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFragment extends Fragment {

    public AdminFragment() {
        // Required empty public constructor
    }


    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences user = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        if (user.getString("user", "admin").equalsIgnoreCase("admin")){
            root = inflater.inflate(R.layout.fragment_admin, container, false);
        }
        else
            root = inflater.inflate(R.layout.not_access, container, false);
        return root;
    }
}
