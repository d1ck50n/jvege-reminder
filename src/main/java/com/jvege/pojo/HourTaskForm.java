package com.jvege.pojo;

/**
 *  Construct HourForm pojo for HourSelector
 * @author Dickson
 */
public class HourTaskForm {

    private String hourText;
    private int minute;
    private int hourValue;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * get hour value in 12 hour format plus am/pm
     * @param hour
     */
    public String getHourText() {
        return hourText;
    }

    /**
     * get hour value in 12 hour format plus am/pm
     * @param hour
     */
    public void setHourText(String hourText) {
        this.hourText = hourText;
    }

    /**
     * get hour value in 24 hour format
     * @param hour
     */
    public int getHourValue() {
        return hourValue;
    }

    /**
     * set hour value in 24 hour format
     * @param hour
     */
    public void setHourValue(int hourValue) {
        this.hourValue = hourValue;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
    
    @Override
    public String toString(){
        return  "Hour Text=" + this.hourText+ ", " +
                "Minutes=" + this.minute + ", " +
                "Hour Value=" +this.hourValue + ", " +
                "Checked=" + this.checked;
    }
}
