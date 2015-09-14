/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jvege.view;

import com.jvege.model.Constant;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Dickson
 */
public class YearSelector extends JComboBox {

    protected  int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    public YearSelector() {
        init();
    }

    public YearSelector(int yearToSet) {
        this.currentYear = yearToSet;
        init();
    }

    private void init() {
        int minYear = 2009;
        int maxYear = currentYear + 100;
        setPreferredSize(new Dimension(61, 20));
        
        DefaultComboBoxModel cmbModel = new DefaultComboBoxModel();
        for (int i = minYear; i <= maxYear; i++) {
            cmbModel.addElement(i);
        }
        setModel(cmbModel);
        setSelectedItem(currentYear);
        addItemListener(itemListener);
    }
    
    ItemListener itemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            currentYear = Integer.parseInt(e.getItem().toString());
            firePropertyChange(Constant.YEAR_CHANGE, "", currentYear);
        }
    };
}
