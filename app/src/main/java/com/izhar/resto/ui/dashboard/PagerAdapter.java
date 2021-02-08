package com.izhar.resto.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class PagerAdapter extends FragmentStatePagerAdapter {
    private int tab_numbers;
    String user;
    public PagerAdapter(@NonNull FragmentManager fm, int tab_numbers, String user) {
        super(fm);
        this.tab_numbers = tab_numbers;
        this.user = user;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                PendingTab pendingTab = new PendingTab(user);
                return pendingTab;
            case 1:
                FinishedTab finishedTab = new FinishedTab(user);
                return finishedTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tab_numbers;
    }
}
