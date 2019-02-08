package com.foxminded.university.domain;

import java.util.Date;

public class TimePeriod {
    private static final String TIME_PATTERN = "dd-MM-yyyy hh:mm";
    private Date startTime;
    private Date endTine;

    public TimePeriod(Date startTime, Date endTine) {
        this.startTime = startTime;
        this.endTine = endTine;
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
