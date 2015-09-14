package com.jvege.view;


// <editor-fold defaultstate="collapsed" desc="import statement"> 
import com.jvege.util.ReminderUtil;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import com.jvege.entity.Reminder;
import com.jvege.model.Constant;
import com.jvege.model.ReminderManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
// </editor-fold>

/**
 *
 * @author Dickson
 */
public class MonthCalendar extends JPanel {

    private static final boolean SYNC_WITH_DB = true; // to triger whether getting data from DB to is needed
    private Reminder tmpReminder = null;
    private JLabel lblHighlightDay = new JLabel(); // declare static bcoz it can clear when use as few month calendar
    private JLabel lblToday = new JLabel();
    private Border currentBorder; // to get whether is task day border
    private Calendar currentCalendar = Calendar.getInstance();
    private Calendar tmpCalendar = Calendar.getInstance();
    private int currentYear = currentCalendar.get(Calendar.YEAR);
    private int currentMonth = currentCalendar.get(Calendar.MONTH);
    private int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
    private GregorianCalendar gregorianCalendar;
    private SimpleDateFormat sdf;
    private Map<String, List<Reminder>> yearMap;
    protected JLabel[] lblDayArr = new JLabel[42];
    private int month;
    private int year;
    private int day;
    private Color weekPanelBackgroundColor;
    private Color weekForegroundColor;
    private Color highlightDayBorderColor;
    private Color dayLabelColor;
    private Color monthNameColor;
    private Color todayBorderColor;
    private Font monthNameFont;
    private Font weekFont;
    private Font dayFont;
    private Font dayNameFont;
    private Font mouseOverDayFont;
    private Dimension dayPrefDimension;
    private LineBorder highlightDayBorder;
    private LineBorder todayBorder;

    /** Initialize Calendar with current year, month and day */
    public MonthCalendar() {
        this.year = currentYear;
        this.month = currentMonth;
        this.day = currentDay;
        initComponent();
    }

    /** Initialize Calendar by passing Year and Month parameter
     * @param int year
     * @param int month
     */
    public MonthCalendar(int year, int month, int day) {        
        this.year = year;
        this.month = month;
        this.day = day < 0 ? currentDay : day;
        initComponent();
    }
    
    private void initComponent() {
        
        // initialize static variable
        weekPanelBackgroundColor = ReminderUtil.getColor("panel.header.color.start");
        weekForegroundColor = ReminderUtil.getColor("label.week.foreground.color");
        todayBorderColor = ReminderUtil.getColor("border.today.color");
        highlightDayBorderColor = ReminderUtil.getColor("border.day.highlight.color");
        dayLabelColor = ReminderUtil.getColor("label.day.name.color");
        monthNameColor = ReminderUtil.getColor("label.month.name.color");
        monthNameFont = ReminderUtil.getFont(monthNameFont, "label.month.name.font", 1, 13);
        weekFont = ReminderUtil.getFont(weekFont, "label.week.font", 0, 11);
        dayFont = ReminderUtil.getFont(dayFont, "label.day.font", 0, 11);
        dayNameFont = ReminderUtil.getFont(dayNameFont, "label.day.name.font", 1, 11);
        mouseOverDayFont = ReminderUtil.getFont(mouseOverDayFont, "label.highlight.name.font", Font.BOLD, 17);
        gregorianCalendar = gregorianCalendar == null ? new GregorianCalendar() : gregorianCalendar;
        sdf = sdf == null ? new SimpleDateFormat("dd") : sdf;
        yearMap = yearMap == null ? new HashMap() : yearMap;
        dayPrefDimension = dayPrefDimension == null ? new Dimension(26, 14) : dayPrefDimension;
        highlightDayBorder = highlightDayBorder == null ? new LineBorder(highlightDayBorderColor, 3, true) : highlightDayBorder;
        todayBorder = todayBorder == null ? new LineBorder(todayBorderColor, 3, true) : todayBorder;

        List<Reminder> monthList = null;
        boolean leapYear = gregorianCalendar.isLeapYear(this.year);
        currentCalendar.set(Calendar.MONTH, this.month);
        currentCalendar.set(Calendar.YEAR, this.year);
        String shortCurrentMonthText = currentCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());

        // When SYNC_WITH_DB = true, get the Reminder month list and set into yearMap HashMap
        // yearMap declare as static because, this class able to create year calendar which considt multiple
        // MonthCalendar, so yearMap will continue adding without create new object.
        if (SYNC_WITH_DB == true) {
            monthList = ReminderManager.getMonthReminder(this.year, this.month);
            yearMap.put(shortCurrentMonthText, monthList);
        }

        // initialize panel layout
        setLayout(new BorderLayout());

        JPanel pnDays = new GradientPanel();
        JPanel pnNoOfWeeks = new JPanel();
        //JPanel pnMonthName = new JPanel(new BorderLayout()); // set this layout purposely for label to display center
        JPanel pnMonthName = new GradientPanel("panel.header.color.start", "panel.header.color.end", "H");
        pnDays.setLayout(new GridLayout(7, 7));
        pnNoOfWeeks.setLayout(new GridLayout(7, 1));
        pnNoOfWeeks.setBackground(weekPanelBackgroundColor);
        pnMonthName.setPreferredSize(new Dimension(0,25));
        pnNoOfWeeks.setPreferredSize(new Dimension(20,0));
        //pnMonthName.setBackground(borderColor);

        add(pnDays, BorderLayout.CENTER);

        int[] totalDayInMonth = {31, leapYear ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        String[] dayName = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        // new GregorianCalendar(2008, 0, 1) = 1st, January
        // in Calendar system, Calendar.MONTH will return 1 if current month is FEBRUARY
        int dayGap = (new GregorianCalendar(this.year, this.month, 1).get(Calendar.DAY_OF_WEEK) - 1);

        // Set the month name in NORTH panel
        JLabel lblMonthName = new JLabel(currentCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        lblMonthName = constructLabel(lblMonthName, monthNameFont, monthNameColor, null);
        pnMonthName.add(lblMonthName);
        add(pnMonthName, BorderLayout.NORTH);

        // Set the Day Name, for eg : Sun, Mon in CENTER panel
        for (int i = 0; i < 7; i++) {
            JLabel lblDayName = new JLabel(dayName[i]);
            lblDayName =  constructLabel(lblDayName, dayNameFont, dayLabelColor, dayPrefDimension);
            pnDays.add(lblDayName);
        }

        // Set the lead, put empty label in CENTER panel
        for (int i = 0; i < dayGap; i++) {
            JLabel lblGap = new JLabel();
            pnDays.add(lblGap);
        }

        int weekOfYear = dayGap; // MonthCalendar variable use to trace week of a year panel 

        for (int days = 1; days < 42 - dayGap; days++) {
            lblDayArr[days] = new JLabel();
            constructLabel(lblDayArr[days], dayFont, Color.BLACK, dayPrefDimension);
            if (SYNC_WITH_DB == true && monthList != null) {
                Iterator it = monthList.iterator();
                while (it.hasNext()) {
                    tmpReminder = (Reminder) it.next();
                    String remDay = sdf.format(tmpReminder.getDate());
                    if (Integer.parseInt(remDay) == days) {
                        borderedTaskDay(lblDayArr[days]);
                    }
                }
            }
            if (this.year == currentYear && this.month == currentMonth && days == this.day) {
                lblToday = lblDayArr[days];
                lblToday.setBorder(todayBorder);
            }
            if (days <= totalDayInMonth[this.month]) {
                String dayFullName = (days < 10 ? "0" + days : days) + "-" + shortCurrentMonthText + "-" + this.year;
                lblDayArr[days].addMouseListener(dayListener);
                lblDayArr[days].setName(dayFullName);
                lblDayArr[days].setText(String.valueOf(days));
                pnDays.add(lblDayArr[days]);
                weekOfYear += 1;
            } else {
                pnDays.add(new JLabel());
            }
        }

        // Set the number of week in a year in WEST panel
        //Calendar tmpCalendar = Calendar.getInstance(); // temp calendar instance use to manipulate logic
        tmpCalendar.set(Calendar.MONTH, this.month);
        tmpCalendar.set(Calendar.YEAR, this.year);
        int tmpDay = 6;
        if (weekOfYear > 35) {
            tmpDay = 7;
        } else if (weekOfYear < 29) {
            tmpDay = 5;
        }
        pnNoOfWeeks.add(new JLabel()); // Add an empty label to avaid display no of weeks in first row
        for (int i = 1; i < tmpDay; i++) {
            tmpCalendar.set(Calendar.DAY_OF_MONTH, (i * 7) - 6);
            int week = tmpCalendar.get(Calendar.WEEK_OF_YEAR);
            JLabel lblNoOfWeeks = new JLabel();
            constructLabel(lblNoOfWeeks, weekFont, weekForegroundColor, dayPrefDimension);
            String buttonText = Integer.toString(week);
            if (week < 10) {
                buttonText = "0" + buttonText;
            }
            lblNoOfWeeks.setText(buttonText);
            pnNoOfWeeks.add(lblNoOfWeeks);
        }
        add(pnNoOfWeeks, BorderLayout.WEST);

        //add an empty panel for month DECEMBER in SOUTH panel (Just to close to make it nicer :))
        if (this.month == Calendar.DECEMBER) {
            JPanel pnEmpty = new GradientPanel("panel.header.color.start", "panel.header.color.end", "H");
            pnEmpty.setPreferredSize(new Dimension(0, 25));
            add(pnEmpty, BorderLayout.SOUTH);
        }
    }

    MouseListener dayListener = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
            restoreDayLabel();
            JLabel label = (JLabel) e.getSource();
            highlightClickDay(label);
            firePropertyChange(Constant.DAY_CHANGE, null, label);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            label.setFont(mouseOverDayFont);
            label.setForeground(highlightDayBorderColor);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            label.setFont(dayFont);
            label.setForeground(Color.BLACK);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}
    };

    /**
     * Restore the label color and font once other day labal is click on
     */
    private void restoreDayLabel() {
        if (lblHighlightDay != null && lblHighlightDay.getBorder() != null) {
            lblHighlightDay.setFont(dayFont);
            if(lblHighlightDay == lblToday)
                lblHighlightDay.setBorder(BorderFactory.createLineBorder(todayBorderColor, 3));
            else if (currentBorder != null && currentBorder.equals(BorderFactory.createBevelBorder(BevelBorder.LOWERED))) // task day
                borderedTaskDay(lblHighlightDay);
            else
                lblHighlightDay.setBorder(BorderFactory.createEmptyBorder());
            }
        }

    /**
     * Construct label with passed in parameter
     * @param label
     * @param font
     * @param color
     * @return JLabel
     */
    private JLabel constructLabel(JLabel label, Font font, Color color, Dimension dimension) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        if(font != null)label.setFont(font);
        if(color != null)label.setForeground(color);
        if(dimension != null)label.setPreferredSize(dimension);
        return label;
    }

    private JLabel highlightClickDay(JLabel label) {
        if(label.getBorder() != null)currentBorder = label.getBorder(); // if that label is task day
        else currentBorder = null; // set to null for default day label
        lblHighlightDay = label;
        label.setFont(dayFont);
        label.setForeground(Color.BLACK);
        label.setBorder(highlightDayBorder);
        //label.setBorder(BorderFactory.createLineBorder(highlightDayBorderColor, 3));
        return label;
    }

    /**
     *
     * @param label
     * @return
     */
    private JLabel borderedTaskDay(JLabel label) {
        if (label != lblToday) {
            label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            return label;
        }
        return lblToday;
    }

    /**
     * Set day label to task day style (Border)
     * @param label
     */
    public void setBorderedTaskDay(JLabel label) {
        if (label != lblToday) {
            label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            highlightClickDay(label); // return highlight the label to prevent border loss
        }
    }

    /**
     * * Set day label to normal day style (Un-Border)
     * @param label
     */
    public void removeTaskBorder(JLabel label) {
            label.setBorder(BorderFactory.createEmptyBorder());
            highlightClickDay(label); // return highlight the label to prevent border loss
    }
/**
 * Set calendar to highlight selected date,
 * this method mostly user by other classes.
 * @param label
 */
    public void setGotoDate(JLabel label){
        restoreDayLabel();
        highlightClickDay(label);
    }
/**
 * Get Reminder list in selected month
 * @param month
 * @return List<Reminder>
 */
//    public static List<Reminder> getMonthList(String month) {
//        return (List<Reminder>) yearMap.get(month);
//    }
}
