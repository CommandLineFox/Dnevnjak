package com.example.dnevnjak.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnevnjak.R;
import com.example.dnevnjak.activity.MainActivity;
import com.example.dnevnjak.activity.SingleEvent;
import com.example.dnevnjak.model.Event;

public class EventAdapter extends ListAdapter<Event, EventAdapter.EventViewHolder> {
    public EventAdapter(@NonNull DiffUtil.ItemCallback<Event> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_utility, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event boundableEvent = getItem(position);
        holder.bind(boundableEvent);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), SingleEvent.class);
            Event event = getCurrentList().get(holder.getAdapterPosition());
            intent.putExtra("event", getCurrentList().get(holder.getAdapterPosition()));
            intent.putExtra("naziv", ((MainActivity) holder.itemView.getContext()).getTitle());

            holder.itemView.getContext().startActivity(intent);
        });

        holder.getDeleteEventButton().setOnClickListener(v -> {
            //Brisanje
        });

        holder.getEditEventButton().setOnClickListener(v -> {
            Intent intent = new Intent(((MainActivity) holder.itemView.getContext()), SingleEvent.class);
            intent.putExtra("title", ((MainActivity) holder.itemView.getContext()).getTitle().toString());
            intent.putExtra("edit", true);
            intent.putExtra("oldEvent", boundableEvent);
            ((MainActivity) holder.itemView.getContext()).startActivityForResult(intent, 1);
        });
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView eventTime;
        private TextView eventTitle;
        private Button editEventButton;
        private Button deleteEventButton;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            eventTime = itemView.findViewById(R.id.event_time);
            eventTitle = itemView.findViewById(R.id.event_title);
        }

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void bind(Event event) {
            eventTime.setText(String.format("%02d", event.getStartHour()) + ":" + String.format("%02d", event.getStartMinute()) + " - " + String.format("%02d", event.getEndHour()) + ":" + String.format("%02d", event.getEndMinute()));

            eventTitle.setText(event.getTitle());
        }

        public Button getEditEventButton() {
            editEventButton = itemView.findViewById(R.id.edit_event);
            return editEventButton;
        }

        public Button getDeleteEventButton() {
            deleteEventButton = itemView.findViewById(R.id.delete_event);
            return deleteEventButton;
        }
    }
}