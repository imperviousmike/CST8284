package cst8284.asgmt1;

import java.util.Calendar;

/*
    Course Name: CST8284
    Student Name: Michael Daly
    Class name: Object Oriented Programming (Java)
    Date: February 9th, 2020
*/

public class TimeBlock {

    private Calendar startTime,endTime;

    public TimeBlock() {
        this(new Calendar.Builder().setFields(Calendar.HOUR_OF_DAY, 8).build(),
                new Calendar.Builder().setFields(Calendar.HOUR_OF_DAY, 23).build());

    }

    public TimeBlock(Calendar start, Calendar end) {
        setStartTime(start);
        setEndTime(end);
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public int duration() {

        return getEndTime().get(Calendar.HOUR_OF_DAY) - getStartTime().get(Calendar.HOUR_OF_DAY);
    }

    public boolean overlaps(TimeBlock newBlock) {

        return getStartTime().before(newBlock.getEndTime()) && newBlock.getStartTime().before(getEndTime());
    }

    @Override
    public String toString() {
        return String.format("%s:00 - %s:00", getStartTime().get(Calendar.HOUR_OF_DAY),
                getEndTime().get(Calendar.HOUR_OF_DAY));
    }

}