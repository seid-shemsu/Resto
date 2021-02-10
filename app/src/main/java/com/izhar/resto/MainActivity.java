package com.izhar.resto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_admin)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //addBadgeView();

    }
    private void addBadgeView() {
        try {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) navView.getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(1); //set this to 0, 1, 2, or 3.. accordingly which menu item of the bottom bar you want to show badge
            View notificationBadge = LayoutInflater.from(this).inflate(R.layout.badge_layout, menuView, false);
            itemView.addView(notificationBadge);
            //notificationBadge.setVisibility(GONE);// initially badge will be invisible
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        final SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        if (item.getItemId() == R.id.user){
            final Dialog dialog = new Dialog(MainActivity.this); dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.user_selection);
            dialog.show();
            RadioButton cashier = dialog.findViewById(R.id.cashier);
            RadioButton waiter = dialog.findViewById(R.id.waiter);
            RadioButton cooker = dialog.findViewById(R.id.cooker);
            cashier.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    user.edit().putString("user", "cashier").apply();
                    dialog.dismiss();
                }
            });
            waiter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    user.edit().putString("user", "waiter").apply();
                    dialog.dismiss();
                }
            });
            cooker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    user.edit().putString("user", "cooker").apply();
                    dialog.dismiss();
                }
            });
        }
        return true;
    }
}
