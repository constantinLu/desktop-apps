package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.EmploymentCategory;
import model.Person;
//wrapper for TablePanel
public class PersonTableModel extends AbstractTableModel {

	private List<Person> db;
	
	private String[] colNames = {"ID", "Name", "Occupation", "Age Category", "Employment Category", "Us Citizen", "Tax ID"};
	
	public PersonTableModel() {
		
	}
	
	//71 return a obj
	@Override
	public Class<?> getColumnClass(int col) {
		
		switch(col) {
		case 0:
			return Integer.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return EmploymentCategory.class;
		case 5:
			return Boolean.class; //set the checkbox for the table column
		case 6:
			return String.class;
		default:
			return null;
		}
	}


	//70
	@Override
	public boolean isCellEditable(int row, int col) {
		
		//any row in col 1 is editable
		switch(col) {
		case 1:
			return true;
		case 4:
			return true;
		case 5:
			return true; //make the boolean checkBox editable
		default:
			return false;
		}
	}
	//70 save the editing cell
	// get the value, give it the row and column, 
	//set the value in dataModel we are using for that row and column
	//cast the value
	@Override
	public void setValueAt(Object value, int row, int col) {
		
		//if is no data, return
		if (db == null) return;
		Person person = db.get(row);
		
		switch(col) {
		case 1:
			
			person.setName((String)value);
			break;
		case 4:
			person.setEmpCat((EmploymentCategory)value);  //set the value and cast it to that enum
			break;
		case 5:
			person.setUsCitizen((Boolean)value); //make the boolean checkBox editable
		default:
			return;
			
		}
	}


	public void setData(List<Person> db) {
		this.db = db;
	}
	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return db.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Person person = db.get(row);
		
		switch(col) {
		case 0:
			return person.getId();
		case 1:
			return person.getName();
		case 2:
			return person.getOccupation();
		case 3:
			return person.getAgeCategory();
		case 4:
			return person.getEmpCat();
		case 5:
			return person.isUsCitizen();
		case 6:
			return person.getTaxId();
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return colNames[column];
	}

}
