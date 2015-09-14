package com.jvege.model;

/**
 *
 * @author Dickson
 */
public class Constant {

    public static final String USER_HOME = System.getProperty("user.home");
    public static final String DB_NAME = "Database.h2.db";
    public static final String DB_FILE_TO = USER_HOME + "/jvege/" + DB_NAME;
    public static final String DB_FILE_SOURCE = "/database/Database.h2.db";
    public static final String JDBC_URL = "jdbc:h2:file:~/jvege/Database;USER=sa";
    public static final String SCRIPT_FILE_URL = "/databaseInit.sql";
    public static final String ICON_PATH = "images/clock.jpg";

    // Even constant
    public static final String YEAR_CHANGE = "yearChange";
    public static final String DAY_CHANGE = "dayChange";
    public static final String DAY_CHANGE_FROM_DATE_DIALOG = "dayChangeFromDateDialog";
    public static final String MOUSE_CLICK_HOUR_CHANGE = "mouseClickHourChange";
    public static final String KEY_PRESS_HOUR_CHANGE = "keyPressHourChange";
    public static final String REMINDER_TASK_KEY_UP = "reminderTaskKeyUp";
    public static final String REMINDER_TASK_KEY_DOWN = "reminderTaskKeyDown";
    public static final String REMINDER_TASK_MOUSE_ENTER = "reminderTaskMouseEnter";
    public static final String ADD_REMINDER = "addReminder";
    public static final String SHOW_RECURSIVE_DIALOG = "showRecursiveDialog";
    public static final String DELETE_REMINDER = "deleteReminder";
    public static final String ADD_UPDATE_RECURSIVE = "addOrUpdateRecursive";
    public static final String DELETE_RECURSIVE = "deleteRecursive";
    
}
