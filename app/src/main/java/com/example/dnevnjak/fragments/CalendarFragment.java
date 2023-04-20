package com.example.dnevnjak.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dnevnjak.R;
import com.example.dnevnjak.adapter.CalendarAdapter;
import com.example.dnevnjak.differ.CalendarDiffer;

import org.joda.time.DateTime;

import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CalendarFragment extends Fragment {
    private RecyclerView recyclerView;
    private CalendarAdapter calendarAdapter;
    private CalendarRecyclerViewModel calendarRecyclerViewModel;

    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarRecyclerViewModel = new ViewModelProvider(this).get(CalendarRecyclerViewModel.class);

        setupVariables();
        setupCalendar();
        setupListeners();

        return view;
    }

    private void setupVariables() {
        recyclerView = view.findViewById(R.id.calendar);
    }

    private void setupCalendar() {
        calendarAdapter = new CalendarAdapter(new CalendarDiffer(), date -> {
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerView.setAdapter(calendarAdapter);
    }

    private void setupListeners() {
        calendarRecyclerViewModel.getDates().observe(getViewLifecycleOwner(), dates -> {
            calendarAdapter.submitList(dates);
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    Date date = calendarRecyclerViewModel.getDatesList().get(((GridLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findLastVisibleItemPosition());
                    DateTime dateTime = new DateTime(date);
                    if (dateTime.getDayOfMonth() >= 25) {
                        getActivity().setTitle(dateTime.getMonthOfYear() + "." + dateTime.getYear() + ".");
                    }
                }

                if (dy < 0) {
                    Date date = calendarRecyclerViewModel.getDatesList().get(((GridLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findFirstVisibleItemPosition());
                    DateTime dateTime = new DateTime(date);
                    if (dateTime.getDayOfMonth() <= 15) {
                        getActivity().setTitle(dateTime.getMonthOfYear() + "." + dateTime.getYear() + ".");
                    }
                }

                if (recyclerView.findFocus() != null) {
                    if (!recyclerView.findFocus().canScrollVertically(1)) {
                        calendarRecyclerViewModel.addMonth();
                    }

                    if (!recyclerView.findFocus().canScrollVertically(-1)) {
                        calendarRecyclerViewModel.addMonthToBeginning();
                    }
                }
            }
        });
    }
}