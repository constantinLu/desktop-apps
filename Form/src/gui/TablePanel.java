package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.EmploymentCategory;
import model.Person;

public class TablePanel extends JPanel {
	
	private JTable table;
	private PersonTableModel tableModel;
	private JPopupMenu popup;
	private PersonTableListener personTableListener;
		
	public TablePanel() { 
		
		tableModel = new PersonTableModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();
		
		//72// set comboBox to EmploymentCat
		table.setDefaultRenderer(EmploymentCategory.class, new EmploymentCategoryRenderer());
		//37 set the editing
		table.setDefaultEditor(EmploymentCategory.class, new EmploymentCatergoryEditor());
		table.setRowHeight(20);
		
		//item for the popup to remove row from table
		JMenuItem removeItem = new JMenuItem("Delete row");
		popup.add(removeItem);
		
		//show popup when right click over the row
		table.addMouseListener(new MouseAdapter() {
			@Override
			
			public void mousePressed(MouseEvent e) {
				//method to select the table  pointed
				int row = table.rowAtPoint(e.getPoint());
				//System.out.println(row);
				
				table.getSelectionModel().setSelectionInterval(row, row);
				if (e.getButton() == MouseEvent.BUTTON3) {
					popup.show(table, e.getX(), e.getY());
				}
				
			}
			
		});
		
		//adding actionlistener to my removeItem
		removeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				
				if (personTableListener != null) {
					personTableListener.rowDeleted(row);
					
					//kind of refresh as the method we have
					tableModel.fireTableRowsDeleted(row, row);
				}
				System.out.println(row);
				
			}
		});
		
		
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(table), BorderLayout.CENTER);
		
	}
	
	
	public void setData(List<Person> db) {
		tableModel.setData(db);
		
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
	
	//31
	public void setPersonTableListener(PersonTableListener listener) {
		this.personTableListener = listener;
	}

}
