package com.example.dnevnjak.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.dnevnjak.R;
import com.example.dnevnjak.adapter.CalendarAdapter;
import com.example.dnevnjak.adapter.MainAdapter;
import com.google.android.material.tabs.TabLayout;

import org.joda.time.DateTime;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Date date;
    private CalendarAdapter.DateViewHolder dayViewHolder;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.mainview);
        tabLayout = findViewById(R.id.main);

        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    public Date getDateSelected() {
        return date;
    }

    public void setDateViewSelected(CalendarAdapter.DateViewHolder dateViewSelected) {
        this.dayViewHolder = dateViewSelected;
    }

    public CalendarAdapter.DateViewHolder getDateViewSelected() {
        return dayViewHolder;
    }

    public void openPlan(Date date) {
        this.date = date;
        DateTime dateTime = new DateTime(date);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();
        setTitle(dateTime.getDayOfMonth() + "." + dateTime.getMonthOfYear() + "." + dateTime.getYear() + ".");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Updatuj event
        }
    }
}