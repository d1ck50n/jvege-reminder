package com.jvege.view;

// <editor-fold defaultstate="collapsed" desc="import statement"> 
import com.jvege.model.Constant;
import java.awt.event.ActionEvent;
import javax.swing.event.PopupMenuEvent;
import com.jvege.util.ReminderUtil;
import com.jvege.pojo.ReminderTaskForm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuListener;
//</editor-fold>
/**
 * ReminderTask Panel
 * @author  Dickson
 */
public class ReminderTask extends JPanel implements MouseListener, KeyListener, ActionListener, PopupMenuListener{

    private static final int MAX_FIELD_ROW = 12;
    private static JLabel selectedMinuteLabel = new JLabel();
    private static JTextField selectedTaskField = new JTextField();
    private static JTextField oldSelectedTaskField = new JTextField();
    private static int currentRow = -1;
    private static int tmpRow = -1;
    private static JTextField textFieldToUnHighlight = null;
    private static JLabel labelToUnHighlight = null;
    private static JLabel lblHourHighlighted = null;
    private static JLabel lblTimeFormatHighlighted = null;
    private static boolean isRowLocked = false; //if value is true, means, current row is being selected, no highlight will set in label and task row
    private static ReminderTaskForm reminderTaskForm;
    protected JTextField[] txtTask = new JTextField[12];
    protected JPanel[] pnTxtTask = new JPanel[12];
    private int hour;
    protected static String tmpText = ""; // to store temporary text in text field when focus set, to restore back when value no commit
    private JLabel[] lblMinute = new JLabel[12];
    protected JLabel lblHour = new JLabel();
    protected JLabel lblTimeFormat = new JLabel();
    private JPanel pnHour = new GradientPanel("panel.hour.gradient.start", "panel.hour.gradient.end", "H");
    private JPanel pnMinutes = new GradientPanel("panel.minute.gradient.start", "panel.minute.gradient.end", "H");
    private JPanel pnTask = new GradientPanel("panel.task.gradient.start", "panel.task.gradient.end", "H");
    private JPanel pnHourHighlight ;
    private static Color hourFocusForegroundColor;
    private static Color hourForegroundColor;
    private static Color recursiveBorderColor;
    private static Color minuteForegroundColor;
    private static Color minuteFocusForegroundColor;
    private static Color txtFieldHighlightColor;
    private static Font hourFont;
    private static Font timeFormatFont;
    private static Font minuteFont;
    private static Font minuteFocusFont;
    private static Font hourFocusFont;
    private static Font timeFormatFocusFont;
    private static Font recursiveFont;
    private static String classSource; // for using in mouse listerner

    public ReminderTask(int iHour) {
        this.hour = iHour;
        initComponents();
    }

    public ReminderTask() {
        initComponents();
    }

    private void initComponents() {

        // initialize static variable
        hourForegroundColor = ReminderUtil.getColor("label.hour.foreground.color");
        hourFocusForegroundColor = ReminderUtil.getColor("label.hour.highlight.foreground.color");
        minuteForegroundColor = ReminderUtil.getColor("label.minute.foreground.color");
        minuteFocusForegroundColor = ReminderUtil.getColor("label.minute.highlight.foreground.color");
        recursiveBorderColor = ReminderUtil.getColor("border.recursive.color");
        txtFieldHighlightColor = ReminderUtil.getColor("textfield.task.highlight.color");
        hourFont = ReminderUtil.getFont(hourFont, "label.hour.font", 1, 20);
        timeFormatFont = ReminderUtil.getFont(timeFormatFont, "label.time.format.font", 1, 12);
        minuteFont = ReminderUtil.getFont(minuteFont, "label.minute.font", 0, 11);
        minuteFocusFont = ReminderUtil.getFont(minuteFocusFont, "label.highlight.minute.font", 1, 16);
        hourFocusFont = ReminderUtil.getFont(hourFocusFont, "label.highlight.hour.font", Font.BOLD + Font.ITALIC, 22);
        timeFormatFocusFont = ReminderUtil.getFont(timeFormatFocusFont, "label.highlight.time.format.font", Font.BOLD + Font.ITALIC, 12);
        recursiveFont = ReminderUtil.getFont(minuteFocusFont, "label.recursive.font", 1, 14);
        //hourHighlightBorder = (SoftBevelBorder) (hourHighlightBorder == null ? BorderFactory.createBevelBorder(BevelBorder.LOWERED) : hourHighlightBorder);

        /* Only pnHour, pnTimeFormat, pnMinutes setMaximumSize of panel reason is to fixed the
         * size when display in different resolution monitor, yet pnTask did not setMaximumSize
         * of panel because the txt field is expanable
         */
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));


        pnHour.setMaximumSize(new Dimension(55, pnHour.getMaximumSize().height));
        pnHour.setPreferredSize(new Dimension(55, 240));
        pnHour.setLayout(new BoxLayout(pnHour, BoxLayout.X_AXIS));
        lblHour.setFont(hourFont);
        lblHour.setForeground(hourForegroundColor);
        lblHour.setText(ReminderUtil.getRealHour(hour) + " ");
        pnHour.add(lblHour);
        lblTimeFormat.setFont(timeFormatFont);
        lblTimeFormat.setForeground(hourForegroundColor);
        lblTimeFormat.setText(ReminderUtil.getRealHourFormat(hour));
        pnHour.add(lblTimeFormat);
        add(pnHour);

        pnMinutes.setMaximumSize(new Dimension(30, pnMinutes.getMaximumSize().height));
        pnMinutes.setPreferredSize(new Dimension(30, 0));
        pnMinutes.setLayout(new GridLayout(12, 1));

        int row = 0;
        for (int j = 0; j < 60; j = j + 5) {
            lblMinute[row] = new JLabel();
            lblMinute[row].setOpaque(false);
            lblMinute[row].setHorizontalAlignment(SwingConstants.CENTER);
            lblMinute[row].setVerticalAlignment(SwingConstants.CENTER);
            lblMinute[row].setForeground(minuteForegroundColor);
            lblMinute[row].setFont(minuteFont);
            lblMinute[row].addMouseListener(this);
            lblMinute[row].setName("lblMint" + row); //Set label name as row number
            if (j < 10) { // set the display text
                lblMinute[row].setText("0" + String.valueOf(j));
            } else {
                lblMinute[row].setText(String.valueOf(j));
            }
            pnMinutes.add(lblMinute[row]);
            add(pnMinutes);
            row += 1;
        }

        /* Initialize task text field
         * Text field will add into CENTER of panel,
         * recursive label is add into EAST of panel if there is any recursive task indicate
         * by addRecursiveLabel() or remove by removeRecursiveLabel()
         */
        pnTask.setPreferredSize(new Dimension(500, 240));
        pnTask.setLayout(new GridLayout(12, 1));
        for (row = 0; row < MAX_FIELD_ROW; row++) {
            pnTxtTask[row] = new JPanel(new BorderLayout());
            pnTxtTask[row].setOpaque(false);
            txtTask[row] = new JTextField();
            txtTask[row].setOpaque(false);
            txtTask[row].setName("txtTask" + row); //Set textField name as row number
            txtTask[row].setEditable(false);
            txtTask[row].addMouseListener(this);
            txtTask[row].addKeyListener(this);
            pnTxtTask[row].add(txtTask[row], BorderLayout.CENTER);
            pnTask.add(pnTxtTask[row]);
        }
        add(pnTask);

    }

    /**
     * Add label 'R' to indicate that is Recursive task
     * @param task row
     */
    protected void addRecursiveLabel(int row){
        JLabel recursive = new JLabel();
        recursive.setText("R");
        recursive.setFont(recursiveFont);
        recursive.setBorder(BorderFactory.createLineBorder(recursiveBorderColor, 3));        
        recursive.setHorizontalAlignment(SwingConstants.CENTER);
        recursive.setVerticalAlignment(SwingConstants.CENTER);
        recursive.setPreferredSize(new Dimension(20,0));
        pnTxtTask[row].add(recursive, BorderLayout.EAST);
        //pnTxtTask[3].remove(row);
    }

    /**
     * Remove label 'R' when task delete or recursive schedule remove
     * @param tsask row
     */
    protected void removeRecursiveLabel(int row){
        if(pnTxtTask[row].getComponentCount() == 2) // check whether "R" label add in, if there is, then component = 2 else = 1
        pnTxtTask[row].remove(1); // 1=label, 0=txtTask since pnTxtTask only consist 2 component
    }

    /**
     * get row number
     * @param componentName
     * @return row number
     */
    private int getRowNumber(String componentName) {
        return Integer.valueOf(componentName.substring(7));
    }

    /**
     * Construct HourTaskForm for Save, Update
     * @param minute
     * @param hour
     * @param checked
     * @return HourTaskForm
     */
    private com.jvege.pojo.ReminderTaskForm constructHourTaskForm(String minute, int hour, boolean checked) {
        com.jvege.pojo.ReminderTaskForm taskForm = new com.jvege.pojo.ReminderTaskForm();
        taskForm.setHour(hour);
        taskForm.setAmPmHour(ReminderUtil.getRealHour(hour) + ReminderUtil.getRealHourFormat(hour));
        taskForm.setMinute(Integer.parseInt(minute));
        taskForm.setReminderTask(selectedTaskField.getText());
        return taskForm;
    }

    /**
     * Focus hour panel by setting it's color
     */
    protected void setHourPanelFocus() {
        lblHourHighlighted = lblHour;
        lblHourHighlighted.setFont(hourFocusFont);
        lblTimeFormatHighlighted = lblTimeFormat;
        lblTimeFormat.setFont(timeFormatFocusFont);
        pnHourHighlight = pnHour;
        lblHourHighlighted.setForeground(hourFocusForegroundColor);
        lblTimeFormatHighlighted.setForeground(hourFocusForegroundColor);
    }

    /**
     * Release focus by reset color to default
     */
    protected void removeHourPanelFocus() {
        if (lblHourHighlighted != null && lblTimeFormatHighlighted != null) {
            lblHourHighlighted.setForeground(hourForegroundColor);
            lblTimeFormatHighlighted.setForeground(hourForegroundColor);
            lblHourHighlighted.setFont(hourFont);
            lblTimeFormat.setFont(timeFormatFont);
        }
    }
    /**
     * highlight text field and minute label when key press,
     * this method mostly call by other class
     * @param currentRow
     */
    protected void highlightFocusField(int currentRow) {
        JLabel label = lblMinute[currentRow];
        label.setFont(minuteFocusFont);
        JTextField txtField = txtTask[currentRow];
        txtField.setOpaque(true);
        txtField.setBackground(txtFieldHighlightColor);
    }

    /**
     * highlight text field and minute label when mouse over
     * @param textField
     * @param label
     */
    private void highlightFocusField(JTextField textField, JLabel label) {
        label.setFont(minuteFocusFont);
        textField.setOpaque(true);
        textField.setBackground(txtFieldHighlightColor);
    }

    /**
     * un-highlight text field and minute label when text field out of focus,
     * this method mostly call by other class
     * @param currentRow
     */
    protected void unHighlightFocusField(int row) {
        JLabel label = lblMinute[row];
        label.setFont(minuteFont);
        JTextField txtField = txtTask[row];
        txtField.setBackground(null);
        txtField.setOpaque(false);
    }

    /**
     * un-highlight text field and minute label when mouse cursor out of focus
     * @param currentRow
     */
    private void unHighlightFocusField(JTextField textField, JLabel label) {
        if (textField != null && label != null) {
            label.setFont(minuteFont);
            textField.setBackground(null);
            textField.setOpaque(false);
        }
    }

    /**
     * disable text field for input
     */
    private void disableFocusField() {        
        selectedMinuteLabel.setFont(minuteFont);
        selectedMinuteLabel.setForeground(minuteForegroundColor);
        selectedTaskField.setEditable(false);
        selectedTaskField.setOpaque(false);
        selectedTaskField.transferFocus();
        isRowLocked = false; // release the lock, highlight can be set in minute label and task field
    }

    /**
     * enable text field for input
     * @param currentRow
     */
    protected void enableFocusField(int currentRow) {
        selectedTaskField = txtTask[currentRow];
        selectedTaskField.setBackground(null);
        selectedTaskField.setOpaque(true);
        selectedTaskField.setEditable(true);
        selectedTaskField.requestFocusInWindow();
        selectedTaskField.setSelectionStart(0);
        //selectedTaskField.setText("");

        selectedMinuteLabel = lblMinute[currentRow];
        selectedMinuteLabel.setFont(minuteFocusFont);
        selectedMinuteLabel.setForeground(minuteFocusForegroundColor);
        isRowLocked = true; // lock is set, highlight can not be set in minute label and task field
    }

    private boolean checkTextLength(String text) {
        if (text.length() > 254) {
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Please shorten the reminder message.", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void fireAddReminder() {
        if(selectedTaskField.getText().length() == 0 && !tmpText.equals("")) performDeleteReminder();
        else{
            reminderTaskForm = constructHourTaskForm(selectedMinuteLabel.getText(), this.hour, true);
            firePropertyChange(Constant.ADD_REMINDER, null, reminderTaskForm);
            disableFocusField();
        }
    }

    private void performMouseClicked(int currentRow) {
        removeHourPanelFocus();
        setHourPanelFocus();
        unHighlightFocusField(textFieldToUnHighlight, labelToUnHighlight); // usefull when context menu show but click to other txtfield
        
        if (!oldSelectedTaskField.getText().trim().equals("") && !oldSelectedTaskField.getText().equals(tmpText)) {
            // here answer value will be : OK = 0, CANCEL = 2, CLOSE = -1
            int answer = JOptionPane.showConfirmDialog(null, "Save reminder message?", "Ask", JOptionPane.OK_CANCEL_OPTION);
            if (answer == 0 && checkTextLength(txtTask[currentRow].getText()) == true) {
                fireAddReminder();
            } else if (!tmpText.trim().equals("")) {
                oldSelectedTaskField.setText(tmpText);
            } else {
                oldSelectedTaskField.setText("");
            }
            disableFocusField();

        } else if (isRowLocked == true && currentRow != tmpRow) {
            disableFocusField();

        } else {
            disableFocusField();
            enableFocusField(currentRow);
        }
        tmpText = txtTask[currentRow].getText();
        tmpRow = currentRow;
    }

    private void performMouseRightClick(int currentRow){
        selectedTaskField = txtTask[currentRow];
    }

    private void performMouseEntered(JTextField textField, JLabel label) {
        if (isRowLocked == false) {
            unHighlightFocusField(textFieldToUnHighlight, labelToUnHighlight);
            highlightFocusField(textField, label);
            textFieldToUnHighlight = textField; // set textFieldToUnHighlight to current textField for un highlight later
            labelToUnHighlight = label; // set labelToUnHighlight to current label for un highlight later
            firePropertyChange(Constant.REMINDER_TASK_MOUSE_ENTER, "", currentRow);
        }
    }

    private void performUpDownKeyPress(String propertyName) {
        if (isRowLocked == false) {
            firePropertyChange(propertyName, "", "dumbValue");
        }
    }

    private void performShowRecursiveDialog() {
        selectedMinuteLabel = lblMinute[currentRow];
        reminderTaskForm = constructHourTaskForm(selectedMinuteLabel.getText(), hour, true);
        firePropertyChange(Constant.SHOW_RECURSIVE_DIALOG, null, reminderTaskForm);
        isRowLocked = false;
    }

    private void performDeleteReminder() {
        int answer = JOptionPane.showConfirmDialog(null, "Delete Reminder?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
        if (answer == 0) {
            selectedMinuteLabel = lblMinute[currentRow];
            reminderTaskForm = constructHourTaskForm(selectedMinuteLabel.getText(), hour, true);
            firePropertyChange(Constant.DELETE_REMINDER, null, reminderTaskForm);
            selectedTaskField.setText("");
        }
        else if (answer == 2 || answer == -1) { // this else statement will use only fireAddReminder().performDeleteReminder()
                oldSelectedTaskField.setText(tmpText);                
        }
        disableFocusField();
        //isRowLocked = false;
    }

    private void performShowContextMenu(Point pt) {        
        JPopupMenu menu = new JPopupMenu();
        menu.addPopupMenuListener(this);
        JMenuItem menuItemDelete = new JMenuItem("Delete");
        menuItemDelete.setActionCommand(Constant.DELETE_REMINDER);
        menuItemDelete.addActionListener(this);
        JMenuItem menuItemRecursive = new JMenuItem("Recursive");
        menuItemRecursive.setActionCommand("setRecursiveReminder");
        menuItemRecursive.addActionListener(this);
        menu.add(menuItemDelete);
        menu.addSeparator();
        menu.add(menuItemRecursive);
        menu.show(this, pt.x, pt.y);
    }

    // <editor-fold defaultstate="collapsed" desc="MouseListener">
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            classSource = e.getSource().getClass().getSimpleName();
            if (classSource.equals("JTextField")) {
                JTextField txtField = (JTextField) e.getSource();
                currentRow = getRowNumber(txtField.getName());
                performMouseClicked(currentRow);
                oldSelectedTaskField = txtField; //for the purpose of reset the text to originnal if task not save
            } else if (classSource.equals("JLabel")) {
                JLabel label = (JLabel) e.getSource();
                currentRow = getRowNumber(label.getName());
                performMouseClicked(currentRow);
                oldSelectedTaskField = txtTask[currentRow]; //for the purpose of reset the text to originnal if task not save
            }
        } else if (SwingUtilities.isRightMouseButton(e) && classSource.equals("JTextField")) {
            JTextField txtField = (JTextField) e.getSource();
            if (!txtField.getText().equals("") && isRowLocked == false) {
                Point pt = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), this);
                currentRow = getRowNumber(txtField.getName());
                performMouseRightClick(currentRow);
                performShowContextMenu(pt);
            }
        //btnOk.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "performOk");
        //btnOk.getActionMap().put("performOk", okAction);

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        classSource = e.getSource().getClass().getSimpleName();
        if (classSource.equals("JTextField")) {
            JTextField txtField = (JTextField) e.getSource();
            currentRow = getRowNumber(txtField.getName());
            performMouseEntered(txtField, lblMinute[currentRow]);
        } else if (classSource.equals("JLabel")) {
            JLabel label = (JLabel) e.getSource();
            currentRow = getRowNumber(label.getName());
            performMouseEntered(txtTask[currentRow], lblMinute[currentRow]);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (isRowLocked == false) {
            unHighlightFocusField(textFieldToUnHighlight, labelToUnHighlight);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="KeyListener">
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        JTextField txtField = (JTextField) e.getSource();
        currentRow = getRowNumber(txtField.getName());
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("Enter") && isRowLocked == true && checkTextLength(txtField.getText()) == true) {
            fireAddReminder();

        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("Escape") && isRowLocked == true) {
            if ((tmpText.trim().equals(""))) {
                txtTask[currentRow].setText("");
            } else {
                txtTask[currentRow].setText(tmpText);
            }
            disableFocusField();
            removeHourPanelFocus();
//
//        } else if (e.isAltDown() && KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("I") && rowLocked == false) {
//            //enableFocusField(currentRow);
//            tmpText = txtTask[currentRow].getText();
//            firePropertyChange("acceptInput", "", "dumpValue");

        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("Up")) {
                performUpDownKeyPress(Constant.REMINDER_TASK_KEY_UP);

// ************** BELOW CODE APPLY FOR LOCAL USE **************************
//                unHighlightFocusField(currentRow);
//                if (currentRow > 0) {
//                    currentRow -= 1;
//                    highlightFocusField(currentRow);
//                } else if (currentRow == 0) {
//                    currentRow = MAX_FIELD_ROW - 1;
//                    highlightFocusField(currentRow);
//                }

        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("Down")) {
             performUpDownKeyPress(Constant.REMINDER_TASK_KEY_DOWN);
             
// ************** BELOW CODE APPLY FOR LOCAL USE **************************
//                unHighlightFocusField(currentRow);
//                if (currentRow < MAX_FIELD_ROW - 1) {
//                    currentRow += 1;
//                    highlightFocusField(currentRow);
//                } else if (currentRow == MAX_FIELD_ROW - 1) {
//                    currentRow = 0;
//                    highlightFocusField(currentRow);
//                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ActionListerner">
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("setRecursiveReminder")){
            performShowRecursiveDialog();
        }
        else if (e.getActionCommand().equals(Constant.DELETE_REMINDER)){
            performDeleteReminder();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="PopupMenuListerner">
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        isRowLocked = true;
        //rowToUnHighlight = currentRow;
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    //rowLocked = false;
    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        isRowLocked = false;
//        unHighlightFocusField(currentRow);
    }
    // </editor-fold>
}
