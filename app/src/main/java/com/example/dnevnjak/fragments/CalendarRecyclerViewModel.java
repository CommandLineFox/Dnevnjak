package com.example.dnevnjak.fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.joda.time.DateTime;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarRecyclerViewModel extends ViewModel {
    private final MutableLiveData<List<Date>> dates = new MutableLiveData<>();
    private final ArrayList<Date> datesList = new ArrayList<>();

    public CalendarRecyclerViewModel() {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.minusDays(dateTime.getDayOfMonth());

        YearMonth yearMonthObject = YearMonth.of(dateTime.getYear(), dateTime.getMonthOfYear());
        int daysToAdd = yearMonthObject.lengthOfMonth();

        while (dateTime.getDayOfWeek() != 1) {
            dateTime = dateTime.minusDays(1);
            daysToAdd += 1;
        }

        for (int i = 0; i < daysToAdd; i++) {
            datesList.add(dateTime.plusDays(i).toDate());
        }

        ArrayList<Date> listToSubmit = new ArrayList<>(datesList);
        dates.setValue(listToSubmit);
    }

    public void addMonth() {
        DateTime dateTime = new DateTime(datesList.get(datesList.size() - 1));

        YearMonth yearMonthObject = YearMonth.of(dateTime.getYear(), dateTime.getMonthOfYear());
        int daysInMonth = yearMonthObject.lengthOfMonth();

        for (int i = 0; i < daysInMonth; i++) {
            dateTime = new DateTime(datesList.get(datesList.size() - 1)).plusDays(1);
            datesList.add(dateTime.toDate());
        }

        ArrayList<Date> listToSubmit = new ArrayList<>(datesList);
        dates.setValue(listToSubmit);
    }

    public void addMonthToBeginning() {
        int daysToAdd;

        DateTime dateTime = new DateTime(datesList.get(0));
        DateTime minus = dateTime.minusDays(1);
        if (minus.getMonthOfYear() == dateTime.getMonthOfYear()) {
            daysToAdd = dateTime.getDayOfMonth() - 1;
        } else {
            YearMonth yearMonthObject = YearMonth.of(dateTime.getYear(), dateTime.getMonthOfYear());
            daysToAdd = yearMonthObject.lengthOfMonth();
        }

        for (int i = 0; i < daysToAdd; i++) {
            minus = new DateTime(datesList.get(0)).minusDays(1);
            datesList.add(0, minus.toDate());
        }

        Date date = new Date(datesList.get(0).getDate());
        dateTime = new DateTime(date);
        while (dateTime.getDayOfWeek() != 1) {
            dateTime = dateTime.minusDays(1);
            datesList.add(0, dateTime.toDate());
        }

        ArrayList<Date> listToSubmit = new ArrayList<>(datesList);
        dates.setValue(listToSubmit);
    }

    public MutableLiveData<List<Date>> getDates() {
        return dates;
    }

    public ArrayList<Date> getDatesList() {
        return datesList;
    }
}