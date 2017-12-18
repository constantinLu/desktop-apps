package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

	//controls for the panel

	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationFiled;
	private JButton okBtn;
	private FormListener formListener;
	private JList ageList;
	private JComboBox empCombo;
	private JCheckBox citizenCheck;
	//closerelation with citizenCheck
	private JTextField taxField;
	private JLabel taxLabel;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	//if male is selected , female is deselected // 18
	private ButtonGroup genderGroup;

	public FormPanel() {
		//setting the size of the FormPanel
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);
		setMinimumSize(dim);

		//instantiating the controlPanels
		nameLabel = new JLabel("Name:");
		occupationLabel = new JLabel("Occupation:");
		nameField = new JTextField(10);
		occupationFiled = new JTextField(10);
		ageList = new JList();
		empCombo = new JComboBox();
		citizenCheck = new JCheckBox();
		taxField = new JTextField(10);
		taxLabel = new JLabel("Tax ID: ");
		okBtn = new JButton("OK");
		
		//Set up mnemomics for OK button
		okBtn.setMnemonic(KeyEvent.VK_O);
		//Mnemonic for nameField through nameLabel
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);
		
		maleRadio = new JRadioButton("male");
		femaleRadio = new JRadioButton("female");
		genderGroup = new ButtonGroup();
		
		
		//Setup gender radios and groupButton
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
		//text for internal purposes
		maleRadio.setActionCommand("male");
		femaleRadio.setActionCommand("female");
		//default selection
		maleRadio.setSelected(true);
		

		//Setup tax ID
		taxLabel.setEnabled(false);
		taxField.setEnabled(false);
		citizenCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isTicked = citizenCheck.isSelected();
				taxLabel.setEnabled(isTicked);
				taxField.setEnabled(isTicked);

			}
		});

		//creating a default list ageModel //14
		DefaultListModel ageModel = new DefaultListModel<>();
		ageModel.addElement(new AgeCategory(0,"Under 18"));
		ageModel.addElement(new AgeCategory(1,"18 to 65"));
		ageModel.addElement(new AgeCategory(2,"65 or over"));
		ageList.setModel(ageModel);

		ageList.setPreferredSize(new Dimension(115, 60));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		ageList.setSelectedIndex(1);


		//16 set up comboBox with a defaultComobBox
		DefaultComboBoxModel empModel = new DefaultComboBoxModel<>();
		empModel.addElement("employed");
		empModel.addElement("self-employed");
		empModel.addElement("unemployed");
		empCombo.setModel(empModel);
		empCombo.setSelectedIndex(0);
		empCombo.setEditable(true);

		
		
		//actionListener to the button with an anonymus class
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String occupation = occupationFiled.getText();
				AgeCategory ageCat = (AgeCategory) ageList.getSelectedValue();
				String empCat = (String) empCombo.getSelectedItem();
				String taxId = taxField.getText();
				boolean usCitizen = citizenCheck.isSelected();
				String gender = genderGroup.getSelection().getActionCommand();

				System.out.println(ageCat.getId());
				System.out.println(empCat);


				//adding the functionality of the FormEvent
				FormEvent ev = new FormEvent(this, name, occupation, ageCat.getId(), empCat, taxId,
						usCitizen, gender);
				if(formListener != null) {
					formListener.formEventOccurred(ev);
				
					
				//added later
				nameField.setText("");
				occupationFiled.setText("");
				}
			}

		});


		//adding border to the fromPanel form the left
		Border innerBorder = BorderFactory.createTitledBorder("Add Person");
		Border outerBroder = BorderFactory.createEmptyBorder(5,5,5,5);
		//static method
		setBorder(BorderFactory.createCompoundBorder(outerBroder, innerBorder));

		//invoking the method
		layoutCmponents();
	}

	//setComponents Method
	public void layoutCmponents() {

		//creating a GridBagLayout and setting the controls tot the Panel
		setLayout(new GridBagLayout());
		//object constrain to use with the gridBagLayout object
		GridBagConstraints  gc = new GridBagConstraints();

		/*		
		Fields initialization of the GridBagConstraints
		laying components in a grid
		increase x left -> right
		increase y top -> bottom
		 */

		//controls how much space takes it takes to the other cells
		//the value compares with the other values of weights of the other cells;
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridy = 0;
		// take all the space in the cell or not (none in this case)
		gc.fill = GridBagConstraints.NONE;


		//add the top left component
		// ---- row 1 ----
		gc.gridx = 0;

		gc.anchor = GridBagConstraints.LINE_END;
		//adding indent to the label
		gc.insets = new Insets(0,0,0,5);
		add(nameLabel, gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0,0,0,5);
		add(nameField, gc);


		//  ----- row 2 ----
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		//gc.gridy = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		add(occupationLabel, gc);

		gc.gridx = 1;
		//gc.gridy = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(occupationFiled, gc);


		//  ----- next row ----
		//sets the last row to take the most space in the Form
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		//gc.gridy = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Age: "), gc);

		gc.gridx = 1;
		//gc.gridy = 2;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(ageList, gc);

		//  ----- next row ----
		//sets the last row to take the most space in the Form
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		//gc.gridy = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Employment: "), gc);

		gc.gridx = 1;
		//gc.gridy = 3;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(empCombo, gc);


		//  ----- next row ----
		//sets the last row to take the most space in the Form
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		//gc.gridy = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("US Citizen: "), gc);

		gc.gridx = 1;
		//gc.gridy = 3;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(citizenCheck, gc);

		//  ----- next row ----
		//sets the last row to take the most space in the Form
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		//gc.gridy = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(taxLabel, gc);

		gc.gridx = 1;
		//gc.gridy = 3;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(taxField, gc);

		//  ----- next row ----
		//sets the last row to take the most space in the Form
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.05;

		gc.gridx = 0;
		//gc.gridy = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Gender: "), gc);

		gc.gridx = 1;
		//gc.gridy = 3;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(maleRadio, gc);
		
		//  ----- next row ----
		//sets the last row to take the most space in the Form
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 1;
		//gc.gridy = 3;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(femaleRadio, gc);
		
		
		//  ----- next row ----
		//sets the last row to take the most space in the Form
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 2.0;



		gc.gridx = 1;
		//gc.gridy = 3;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(okBtn, gc);
	}

	//method for formListener //kind of setter
	void setFormListener(FormListener listener) {
		this.formListener = listener;
	}
}


/*
 * util class for ageCategory 
 * refering to the string JList as an ID to store in the TextPanel
 */
class AgeCategory {
	private int id;
	private String text;

	public AgeCategory(int id, String text) {
		this.id = id;
		this.text = text;
	}

	public String toString() {
		return text;
	}

	public int getId() {
		return id;
	}
}
