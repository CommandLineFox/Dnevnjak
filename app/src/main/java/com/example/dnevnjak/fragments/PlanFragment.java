package com.example.dnevnjak.fragments;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dnevnjak.R;
import com.example.dnevnjak.adapter.EventAdapter;
import com.example.dnevnjak.differ.EventDiffer;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class PlanFragment extends Fragment {
    private static CalendarViewModel calendarViewModel;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private View view;
    private SwitchCompat oldEventSwitch;
    private SearchView eventSearchView;
    private Button addEventButton;
    private String title = "";
    private static Context context = null;
    private PlanViewModel mViewModel;

    public static PlanFragment newInstance() {
        return new PlanFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);
        calendarViewModel = new ViewModelProvider(getActivity()).get(CalendarViewModel.class);
        context = getContext();

        setupVariables();
        setupPlan();

        return view;
    }

    private void setupVariables() {
        recyclerView = view.findViewById(R.id.plan);
        oldEventSwitch = view.findViewById(R.id.switch_event);
        eventSearchView = view.findViewById(R.id.search_event);
        tabLayout = view.findViewById(R.id.importance_tab_list);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Manje važni");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText("Srednje važni");
        Objects.requireNonNull(tabLayout.getTabAt(2)).setText("Vrlo važni");
        tabLayout.selectTab(null);
        addEventButton = view.findViewById(R.id.add_event_button);
    }

    private void setupPlan() {
        eventAdapter = new EventAdapter(new EventDiffer());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(eventAdapter);
    }
}