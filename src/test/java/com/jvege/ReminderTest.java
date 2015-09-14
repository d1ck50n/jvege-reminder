package com.jvege;

import com.jvege.entity.Reminder;
import com.jvege.model.ReminderManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Dickson
 */
@Ignore
public class ReminderTest {

    @Test
    public void testAddReminder() throws ParseException{
        ReminderManager remMag = new ReminderManager();
        String date = "2009-Feb-22";
        String time = "01:15:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        Date newDate = sdf.parse(date);
        Date newTime = stf.parse(time);
        Reminder rem = new Reminder();
        rem.setId(new Long(7));
        rem.setDate(newDate);
        rem.setTime(newTime);
        rem.setRecursiveId(new Long(2));
        rem.setTask("hello world");
        remMag.addOrUpdateReminder(rem);
    }
}
