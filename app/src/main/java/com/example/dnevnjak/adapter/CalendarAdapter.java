package com.example.dnevnjak.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnevnjak.R;
import com.example.dnevnjak.activity.MainActivity;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.function.Consumer;

public class CalendarAdapter extends ListAdapter<Date, CalendarAdapter.DateViewHolder> {

    private final Consumer<Date> onDateClicked;

    public CalendarAdapter(@NonNull DiffUtil.ItemCallback<Date> diffCallback, Consumer<Date> onDateClicked) {
        super(diffCallback);
        this.onDateClicked = onDateClicked;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_utility, parent, false);
        return new DateViewHolder(view, position -> {
            Date date = getItem(position);
            onDateClicked.accept(date);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        Date date = getItem(position);

        holder.bind(date);

        holder.itemView.setOnClickListener(v -> {
            ((MainActivity) holder.itemView.getContext()).openPlan(date);
            ((MainActivity) holder.itemView.getContext()).setDateViewSelected(holder);
        });
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        private TextView dayOfWeek;
        private TextView dateOfMonth;

        public DateViewHolder(@NonNull View itemView, Consumer<Integer> onItemClicked) {
            super(itemView);

            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getAdapterPosition());
                }
            });

            dayOfWeek = itemView.findViewById(R.id.weekday_number);
            dateOfMonth = itemView.findViewById(R.id.month_date);
        }

        public void bind(Date date) {
            DateTime dateTime = new DateTime(date);
            dayOfWeek.setText(dateTime.getDayOfWeek());
            dateOfMonth.setText(dateTime.getDayOfMonth());
        }
    }
}