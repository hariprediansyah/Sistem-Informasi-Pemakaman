package appcode.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class TableActionCellEditor extends AbstractCellEditor implements TableCellEditor {

    private PanelAction actionPanel;
    private Object currentValue;

    private TableActionEvent event;

    public TableActionCellEditor(TableActionEvent event) {
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        actionPanel = new PanelAction();
        actionPanel.initEvent(event, row);
        if (row % 2 == 0){
            if (isSelected){
                actionPanel.setBackground(new Color(33, 103, 153));
            }else{
                actionPanel.setBackground(new Color(50, 50, 50));
            }
        }else{
            if (isSelected){
                actionPanel.setBackground(new Color(29, 86, 127));
            }else{
                actionPanel.setBackground(new Color(30, 30, 30));
            }
        }
        this.currentValue = value;  // Simpan nilai asli (ID) jika perlu digunakan
        return actionPanel;
    }

    @Override
    public Object getCellEditorValue() {
        // Kembalikan nilai asli (misalnya ID) agar tidak jadi false/true
        return currentValue;
    }
}
