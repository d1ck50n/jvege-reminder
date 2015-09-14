package com.jvege.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import com.jvege.entity.Reminder;
import com.jvege.pojo.ReminderTaskForm;

/**
 *
 * @author Dickson
 */
public class ReminderController {

    ReminderManager reminderManager = new ReminderManager();

    public Reminder constructReminder(ReminderTaskForm reminderTaskForm, JLabel lblDate) throws Exception {
        Reminder reminder = new Reminder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
        String hour = Integer.toString(reminderTaskForm.getHour());
        String minute = Integer.toString(reminderTaskForm.getMinute());
        Date date = sdf.parse(lblDate.getText());
        Date time = stf.parse(hour + ":" + minute);
        reminder.setDate(date);
        reminder.setTime(time);
        reminder.setTask(reminderTaskForm.getReminderTask());
        reminder.setStatus("Init");        
        return reminder;
    }

    public void updateReminder(Reminder reminder) {
        reminderManager.addOrUpdateReminder(reminder);
    }

    public void deleteReminder(Reminder reminder){
        reminderManager.deleteReminder(reminder);
    }

    public Reminder getReminder(Reminder reminder){
        return reminderManager.getReminderByDateTime(reminder);
    }
}
