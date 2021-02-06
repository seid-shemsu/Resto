package com.izhar.resto.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class PagerAdapter extends FragmentStatePagerAdapter {
    private int tab_numbers;
    public PagerAdapter(@NonNull FragmentManager fm, int tab_numbers) {
        super(fm);
        this.tab_numbers = tab_numbers;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                PendingTab pendingTab = new PendingTab();
                return pendingTab;
            case 1:
                FinishedTab finishedTab = new FinishedTab();
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
