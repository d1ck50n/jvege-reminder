package com.jvege.pojo;

/**
 *
 * @author Dickson
 */
public class ReminderTaskForm {

    private int hour;
    private String amPmHour;
    private int minute;
    private String reminderTask;

    public String getAmPmHour() {
        return amPmHour;
    }

    public void setAmPmHour(String amPmHour) {
        this.amPmHour = amPmHour;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getReminderTask() {
        return reminderTask;
    }

    public void setReminderTask(String reminderTask) {
        this.reminderTask = reminderTask;
    }

    @Override
    public String toString(){
        return  "Hour=" + this.hour+ ", " +
                "Minutes=" + this.minute + ", " +
                "Hour AM/PM=" +this.getAmPmHour() + ", " +
                "ReminderTask=" + this.getReminderTask();
    }
}
