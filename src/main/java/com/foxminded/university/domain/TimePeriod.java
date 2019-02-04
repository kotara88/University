package com.foxminded.university.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimePeriod {
    private Date startTime;
    private Date endTine;

    public TimePeriod(Date startTime, Date endTine) {
        this.startTime = startTime;
        this.endTine = endTine;
    }

    public TimePeriod(String startTime, String endTine) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        try {
           this.startTime = format.parse(startTime);
           this.endTine = format.parse(endTine);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTine() {
        return endTine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimePeriod that = (TimePeriod) o;

        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        return endTine != null ? endTine.equals(that.endTine) : that.endTine == null;
    }

    @Override
    public int hashCode() {
        return startTime != null ? startTime.hashCode() : 0;
    }
}
