/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appcode.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author coyha
 */
public class TableActionCellRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        
        PanelAction action = new PanelAction();
        if (row % 2 == 0){
            if (isSelected){
                action.setBackground(new Color(33, 103, 153));
            }else{
                action.setBackground(new Color(50, 50, 50));
            }
        }else{
            if (isSelected){
                action.setBackground(new Color(29, 86, 127));
            }else{
                action.setBackground(new Color(30, 30, 30));
            }
        }
        return action;
    }
    
}
