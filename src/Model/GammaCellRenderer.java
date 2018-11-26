/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ATX
 */
public class GammaCellRenderer extends DefaultListCellRenderer{
    public static final DefaultListCellRenderer DEFAULT_RENDERER = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
          Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );  
          Color foreground, background;
		    if (isSelected) {
		      foreground = Color.yellow;
		      background = Color.black;
		    } else {
		      if (index % 2 == 0) {
		        foreground = Color.blue;
		        background = Color.white;
		      } else {
		        foreground = Color.black;
		        background = Color.green;
		      }
		    }
		    c.setForeground(foreground);
		    c.setBackground(background);
		    return c;
    }
}
