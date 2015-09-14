package com.jvege.util;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import org.apache.log4j.Logger;
/**
 *
 * Global Calendar method shared among classes
 *
 * @author Dickson
 */
public class ReminderUtil {

    /**
     * Get 12 hour format value when pass in 24 hour format value as parameter
     *
     * @param int hour
     * @return String hour value
     */
    private final Logger logger = Logger.getLogger(ReminderUtil.class.getName());
    
    public static String getRealHour(int hour) {
        String realHour = null;
        if (hour > 0 && hour <= 11) {
            realHour = String.valueOf(hour);
        } else if (hour == 12) {
            realHour = String.valueOf(hour);
        } else if (hour > 12 && hour <= 24) {
            realHour = String.valueOf(hour - 12);
        }
        return realHour;
    }

    /**
     * Get 12 hour format value when pass in 24 hour format value as parameter
     *
     * @param int hour
     * @return String hour format
     */
    public static String getRealHourFormat(int hour) {
        String hourFormat = null;
        if (hour > 0 && hour <= 11) {
            hourFormat = "a.m";
        } else if (hour == 24) {
            hourFormat = "a.m";
        } else if (hour > 11 && hour < 24) {
            hourFormat = "p.m";
        }
        return hourFormat;
    }

    /**
     * @param propertiesKey
     * @return Color
     */
    public static Color getColor(String propertiesKey) {
        return Color.decode(ResourceBundleUtil.getText(propertiesKey));
    }

    /**
     * @param objectFont
     * @param propertiesKey
     * @param style
     * @param size
     * @return Font
     */
    public static Font getFont(Font objectFont, String propertiesKey, int style, int size) {
        Font tmpFont = objectFont;
        if (tmpFont == null) {
            tmpFont = new Font(ResourceBundleUtil.getText(propertiesKey), style, size);
        }
        return tmpFont;
    }

    /**
     * Get month value in integer which start from 0 = January
     *
     * @param month
     * @return int month;
     */
    public static int getMonth(String month) {
        if (month.equalsIgnoreCase("Jan") || month.equalsIgnoreCase("January")) {
            return 0;
        }
        if (month.equalsIgnoreCase("Feb") || month.equalsIgnoreCase("February")) {
            return 1;
        }
        if (month.equalsIgnoreCase("Mar") || month.equalsIgnoreCase("March")) {
            return 2;
        }
        if (month.equalsIgnoreCase("Apr") || month.equalsIgnoreCase("April")) {
            return 3;
        }
        if (month.equalsIgnoreCase("May")) {
            return 4;
        }
        if (month.equalsIgnoreCase("Jun") || month.equalsIgnoreCase("June")) {
            return 5;
        }
        if (month.equalsIgnoreCase("Jul") || month.equalsIgnoreCase("July")) {
            return 6;
        }
        if (month.equalsIgnoreCase("Aug") || month.equalsIgnoreCase("August")) {
            return 7;
        }
        if (month.equalsIgnoreCase("Sep") || month.equalsIgnoreCase("September")) {
            return 8;
        }
        if (month.equalsIgnoreCase("Oct") || month.equalsIgnoreCase("October")) {
            return 9;
        }
        if (month.equalsIgnoreCase("Nov") || month.equalsIgnoreCase("November")) {
            return 10;
        }
        if (month.equalsIgnoreCase("Dec") || month.equalsIgnoreCase("December")) {
            return 11;
        }
        return -1;
    }

    /**
     * Get this year's year
     *
     * @return int year
     */
    public static int getThisYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Get this month
     *
     * @return int month
     */
    public static int getThisMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * Get today in month
     *
     * @return int today's day
     */
    public static String getToday() {
        return (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) < 10)
                ? "0" + String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                : String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }
    
    public static Date constructDate(JLabel lblDate){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            return sdf.parse(lblDate.getText());
        } catch (ParseException ex) {
            //
        }
        return null;
    }
}
