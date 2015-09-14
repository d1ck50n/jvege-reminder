package com.jvege;

import com.jvege.entity.Reminder;
import com.jvege.model.ReminderManager;
import java.util.List;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Dickson
 */
@Ignore
public class ConnectionTest {

    @Test
    public void accessDBForFirstRun(){
        ReminderManager remMag = new ReminderManager();
        List<Reminder> list = remMag.getAllReminder();
        Assert.assertEquals(1, list.size());
    }
}
