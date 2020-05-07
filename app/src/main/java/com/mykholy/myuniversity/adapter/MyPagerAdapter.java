package com.mykholy.myuniversity.adapter;

import com.mykholy.myuniversity.model.MyTab;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;




public class MyPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<MyTab> tabs = new ArrayList<>();

    public MyPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabs.get(position).getFragment();
    }

    public void addTab(MyTab tab) {

        tabs.add(tab);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTabName();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
