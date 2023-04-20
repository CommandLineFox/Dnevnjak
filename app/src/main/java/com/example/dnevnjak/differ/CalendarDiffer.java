package com.example.dnevnjak.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Date;

public class CalendarDiffer extends DiffUtil.ItemCallback<Date> {
    @Override
    public boolean areItemsTheSame(@NonNull Date oldItem, @NonNull Date newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Date oldItem, @NonNull Date newItem) {
        return oldItem.getTime() == newItem.getTime();
    }
}

