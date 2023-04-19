package com.example.dnevnjak.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dnevnjak.fragments.CalendarFragment;
import com.example.dnevnjak.fragments.PlanFragment;
import com.example.dnevnjak.fragments.ProfileFragment;

public class PageAdapter extends FragmentPagerAdapter {
    private static final int fragment1 = 0;
    private static final int fragment2 = 1;
    private static final int fragment3 = 2;
    private final int itemCount = 3;

    public PageAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case fragment1: {
                fragment = new CalendarFragment();
                break;
            }
            case fragment2: {
                fragment = new PlanFragment();
                break;
            }
            default: {
                fragment = new ProfileFragment();
                break;
            }
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return itemCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case fragment1: {
                return "Kalendar";
            }
            case fragment2: {
                return "Plan";
            }
            default: {
                return "Profil";
            }
        }
    }
}
