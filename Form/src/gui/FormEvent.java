package gui;

import java.util.EventObject;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

public class FormEvent extends EventObject {
	//takes the info from the FomPanel`s Fields and sends`it to the mainFrame
	//stores the information temporarly
	private String name;
	private String occupation;
	private int ageCategory;
	private String empCat;
	private String taxId;
	private boolean usCitizen;
	private String gender;
	
	public FormEvent(Object source) {
		super(source);
		
	}
	
	public FormEvent(Object source, String name, String occupation, int ageCat, String empCat,
			String taxId, boolean usCitizen, String gender) {
		super(source);
		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCat;
		this.empCat = empCat;
		this.taxId = taxId;
		this.usCitizen = usCitizen;
		this.gender = gender;
		 
	}

	public String getGender() {
		return gender;
	}

	public String getTaxId() {
		return taxId;
	}

	public boolean isUsCitizen() {
		return usCitizen;
	}

	public String getEmpCat() {
		return empCat;
	}

	public void setEmpCat(String empCat) {
		this.empCat = empCat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public int getAgeCategory() {
		return ageCategory;
	}
	
	

}
