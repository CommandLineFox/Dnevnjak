package com.example.dnevnjak.fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dnevnjak.model.Event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class CalendarViewModel extends ViewModel {

    private final HashMap<String, MutableLiveData<List<Event>>> events = new HashMap<>();

    private final HashMap<String, ArrayList<Event>> eventList = new HashMap<>();

    public CalendarViewModel() {
    }

    public void setMutableLiveData(String dataKey) {
        events.put(dataKey, new MutableLiveData<>());
        eventList.put(dataKey, new ArrayList<>());
    }

    public void addEvent(String date, Event event) {
        ArrayList<Event> current = eventList.get(date);

        if (current != null) {
            int i = findInsertPosition(current, event);

            current.add(i, event);
            ArrayList<Event> listToSubmit = new ArrayList<>(current);
            events.get(date).setValue(listToSubmit);
        } else {
            current = new ArrayList<>();
            current.add(event);
            eventList.put(date, current);

            MutableLiveData<List<Event>> mutableLiveData = new MutableLiveData<>();
            ArrayList<Event> listToSubmit = new ArrayList<>(current);
            mutableLiveData.setValue(listToSubmit);
            events.replace(date, mutableLiveData);
        }
    }

    public void deleteEvent(String date, Event event) {
        ArrayList<Event> ongoingEvents = eventList.get(date);
        int toDelete = -1;

        for (int i = 0; i < ongoingEvents.size(); i++) {
            if (ongoingEvents.get(i).getTitle().equals(event.getTitle()) && ongoingEvents.get(i).getStartHour() == event.getStartHour() && ongoingEvents.get(i).getStartMinute() == event.getStartMinute() && ongoingEvents.get(i).getEndHour() == event.getEndHour() && ongoingEvents.get(i).getEndMinute() == event.getEndMinute()) {
                toDelete = i;
                break;
            }
        }

        eventList.get(date).remove(toDelete);
        events.get(date).setValue(new ArrayList<>(eventList.get(date)));
    }

    public boolean updateEvent(String date, Event oldEvent, Event newEvent) {
        ArrayList<Event> ongoingEvents = eventList.get(date);
        int toUpdate = -1;

        for (int i = 0; i < ongoingEvents.size(); i++) {
            if (ongoingEvents.get(i).getTitle().equals(oldEvent.getTitle()) && ongoingEvents.get(i).getStartHour() == oldEvent.getStartHour() && ongoingEvents.get(i).getStartMinute() == oldEvent.getStartMinute() && ongoingEvents.get(i).getEndHour() == oldEvent.getEndHour() && ongoingEvents.get(i).getEndMinute() == oldEvent.getEndMinute()) {
                toUpdate = i;
                break;
            }
        }

        if (checkTimeAvailability(date, newEvent)) {
            eventList.get(date).set(toUpdate, newEvent);
            events.get(date).setValue(new ArrayList<>(eventList.get(date)));
            return true;
        }
        return false;
    }

    private int findInsertPosition(ArrayList<Event> ongoingEvents, Event event) {
        if (ongoingEvents.size() != 0) {
            int i = 0;
            for (; i < ongoingEvents.size(); i++) {
                int eventStart = event.getStartHour() * 100 + event.getStartMinute();
                int eventEnd = event.getEndHour() * 100 + event.getEndMinute();
                int currStart = ongoingEvents.get(i).getStartHour() * 100 + ongoingEvents.get(i).getStartMinute();
                int beforeCurrEnd = 0;
                if (i != 0) {
                    beforeCurrEnd = ongoingEvents.get(i - 1).getEndHour() * 100 + ongoingEvents.get(i - 1).getEndMinute();
                }

                if (eventEnd <= currStart) {
                    if (beforeCurrEnd <= eventStart) {
                        break;
                    }
                }
            }
            return i;
        } else {
            return 0;
        }
    }

    public MutableLiveData<List<Event>> getEvent(String date) {
        return events.get(date);
    }

    public boolean checkTimeAvailability(String date, Event event) {
        ArrayList<Event> DailyEvents = eventList.get(date);

        if (DailyEvents == null) {
            return false;
        }

        if (DailyEvents.size() == 0) {
            return true;
        }

        Comparator<Event> byTime = Comparator.comparing(Event::getEndHour).thenComparing(Event::getEndMinute).thenComparing(Event::getStartHour).thenComparing(Event::getStartMinute);
        DailyEvents.sort(byTime);

        int eventStart = 0;
        int eventEnd = 0;
        int currStart = 0;
        int beforeCurrEnd = 0;
        for (int i = 0; i < DailyEvents.size(); i++) {
            eventStart = event.getStartHour() * 100 + event.getStartMinute();
            eventEnd = event.getEndHour() * 100 + event.getEndMinute();
            currStart = DailyEvents.get(i).getStartHour() * 100 + DailyEvents.get(i).getStartMinute();
            beforeCurrEnd = 0;
            if (i != 0) {
                beforeCurrEnd = DailyEvents.get(i - 1).getEndHour() * 100 + DailyEvents.get(i - 1).getEndMinute();
            }

            if (eventEnd <= currStart) {
                return beforeCurrEnd <= eventStart;
            }
        }
        beforeCurrEnd = DailyEvents.get(DailyEvents.size() - 1).getEndHour() * 100 + DailyEvents.get(DailyEvents.size() - 1).getEndMinute();

        return eventStart >= beforeCurrEnd;
    }
}