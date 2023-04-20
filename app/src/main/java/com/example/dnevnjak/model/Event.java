package com.example.dnevnjak.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Event implements Parcelable {
    private String title;
    private String description;
    private Importance importance;
    private int startHour;
    private int endHour;
    private int startMinute;
    private int endMinute;

    public Event(String title, String description, Importance importance, int startHour, int endHour, int startMinute, int endMinute) {
        this.title = title;
        this.description = description;
        this.importance = importance;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startMinute = startMinute;
        this.endMinute = endMinute;
    }

    public Event(Parcel parcel) {
        title = parcel.readString();
        startHour = parcel.readInt();
        startMinute = parcel.readInt();
        endHour = parcel.readInt();
        endMinute = parcel.readInt();
        description = parcel.readString();
        int importance = parcel.readInt();
        switch (importance) {
            case 1: {
                this.importance = Importance.LOW;
                break;
            }
            case 2: {
                this.importance = Importance.MEDIUM;
                break;
            }
            default: {
                this.importance = Importance.HIGH;
                break;
            }
        }
    }

    public static final Creator<Event> EVENT_CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel parcel) {
            return new Event(parcel);
        }

        @Override
        public Event[] newArray(int i) {
            return new Event[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeInt(startHour);
        parcel.writeInt(startMinute);
        parcel.writeInt(endHour);
        parcel.writeInt(endMinute);
        switch (importance) {
            case LOW: {
                parcel.writeInt(1);
                break;
            }
            case MEDIUM: {
                parcel.writeInt(2);
                break;
            }
            case HIGH: {
                parcel.writeInt(3);
                break;
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }
}
