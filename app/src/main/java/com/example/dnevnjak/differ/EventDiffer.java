package com.example.dnevnjak.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.dnevnjak.model.Event;

public class EventDiffer extends DiffUtil.ItemCallback<Event> {

    @Override
    public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
        return (oldItem.getStartHour() == newItem.getStartHour() &&
                oldItem.getStartMinute() == newItem.getStartMinute() &&
                oldItem.getEndHour() == newItem.getEndHour() &&
                oldItem.getEndMinute() == newItem.getEndMinute() &&
                oldItem.getImportance().equals(newItem.getImportance()) &&
                oldItem.getTitle().equals(newItem.getTitle()) &&
                oldItem.getDescription().equals(newItem.getDescription()));
    }
}