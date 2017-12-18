package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import model.EmploymentCategory;

public class EmploymentCatergoryEditor extends AbstractCellEditor implements TableCellEditor {
	
	private JComboBox combo;
	
	//constructor
	public EmploymentCatergoryEditor() {
		combo = new JComboBox(EmploymentCategory.values());
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		combo.setSelectedItem(value);
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//stop using the editor and go back using the renderer
				fireEditingStopped();
			}
			
		});
		return combo; 
	}
	@Override
	public boolean isCellEditable(EventObject arg0) {
		return true;
	}

	@Override
	public Object getCellEditorValue() {
		return combo.getSelectedItem();
	}

	

}
 