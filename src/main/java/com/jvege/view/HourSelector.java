package com.jvege.view;

import com.jvege.model.Constant;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import com.jvege.util.ReminderUtil;
import com.jvege.pojo.HourTaskForm;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class HourSelector extends JComboBox {

    protected List hourFormList = new ArrayList();
    private String[] arrCmbValueText;
    private Color[] arrCmbForecolor;

    public HourSelector() {
        initializeValue();
        arrCmbValueText = new String[hourFormList.size()];
        arrCmbForecolor = new Color[hourFormList.size()];
        initComponent();
    }

    public HourSelector(List pojoList) {
        this.hourFormList = pojoList;
        arrCmbValueText = new String[hourFormList.size()];
        arrCmbForecolor = new Color[hourFormList.size()];
        initComponent();
    }

    private void initializeValue() {

        setPreferredSize(new Dimension(100, 20));
        //first hour in a day is 12 a.m, so is not in the below loop list
        HourTaskForm hourFormFirstHour = new HourTaskForm();
        hourFormFirstHour.setChecked(false);
        hourFormFirstHour.setHourText(ReminderUtil.getRealHour(24) + ReminderUtil.getRealHourFormat(24) + " / " + 24 + ".oo");
        hourFormFirstHour.setHourValue(0);
        hourFormList.add(hourFormFirstHour);

        for (int i = 1; i < 24; i++) {
            HourTaskForm hourForm = new HourTaskForm();
            hourForm.setChecked(false);
            hourForm.setHourText(ReminderUtil.getRealHour(i) + ReminderUtil.getRealHourFormat(i) + " / " + i + ".oo");
            hourForm.setHourValue(i);
            hourFormList.add(hourForm);
        }
    }

    private void initComponent() {
        DefaultComboBoxModel cmbModel = new DefaultComboBoxModel();
        HourTaskForm pojo = new HourTaskForm();
        for (int i = 0; i < hourFormList.size(); i++) {
            cmbModel.addElement(i);
            pojo = (HourTaskForm) hourFormList.get(i);
            arrCmbValueText[i] = pojo.getHourText();
            if (pojo.isChecked() == true) {
                arrCmbForecolor[i] = Color.BLUE;
            } else {
                arrCmbForecolor[i] = new Color(0, 0, 0);
            }
        }
        setModel(cmbModel);
        addKeyListener(keylistener);
        addActionListener(actionListener);
        MyListRenderer renderer = new MyListRenderer();
        setRenderer(renderer);
    }

    protected void resetColor() {
        HourTaskForm pojo = new HourTaskForm();
        for (int i = 0; i < hourFormList.size(); i++) {
            pojo = (HourTaskForm) hourFormList.get(i);
            if (pojo.isChecked() == true) {
                arrCmbForecolor[i] = Color.BLUE;
            } else {
                arrCmbForecolor[i] = new Color(0, 0, 0);
            }
        }
    }
    ActionListener actionListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            if (e.getModifiers() == KeyEvent.BUTTON1_MASK) {
                int currentIndex = getSelectedIndex(); // use selected index to represent hour in combobox
                firePropertyChange(Constant.MOUSE_CLICK_HOUR_CHANGE, -1, currentIndex);
            }

        }
    };
     
    KeyListener keylistener = new KeyListener() {

        public void keyTyped(KeyEvent e) {}

        public void keyPressed(KeyEvent e) {
            if (KeyEvent.getKeyText(e.getKeyCode()).equals("Enter")) {
                int currentIndex = getSelectedIndex(); // use selected index to represent hour in combobox
                firePropertyChange(Constant.KEY_PRESS_HOUR_CHANGE, -1, currentIndex);
            }
        }

        public void keyReleased(KeyEvent e) {}
    };

    class MyListRenderer extends JLabel implements ListCellRenderer {

        public MyListRenderer() {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            int selectedIndex = ((Integer) value).intValue();
            String val = arrCmbValueText[selectedIndex];
            Color txtForeground = arrCmbForecolor[selectedIndex];

            setText(val);
            setForeground(txtForeground);
            return this;

        }
    }
}