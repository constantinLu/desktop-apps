package org.celsius;
/*
* 10.11.2017
* @author LunguC.
* info: created a simple temperature convertor
*/

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

public class CelsiusConvertor extends JFrame {

	//declaring the components
	protected JLabel labelFahr;
	protected JLabel labelCel;

	protected JTextField fahrField;
	protected JTextField celField;


	protected JButton fToCel;
	protected JButton celToF;


	//constructor
	public CelsiusConvertor() {
		super("Temperature Convertor");
		//adding toolbar
		setJMenuBar(createMenuBar());
		//Setting up a new FlowLayout
		setLayout(new FlowLayout());

		//Initializing the components
		labelCel = new JLabel("Celsius: ", SwingConstants.LEFT);
		labelFahr = new JLabel("Fahrenheit: ", SwingConstants.LEFT);
		celField = new JTextField(10);
		fahrField = new JTextField(10);
		celToF = new JButton("Convert Celsius > Fahrenheit > Fahr");
		fToCel = new JButton("Convert Fahrenheit > Celsius");

		

		//Setting up the labels
		labelCel.setToolTipText("This is a scale and unit of measurement for temperature");

		//ADDING components 
		add(labelCel);
		add(celField);
		add(labelFahr);
		add(fahrField);
		add(celToF);
		add(fToCel);

		//adding to the main panel
		JPanel mainPanel = new JPanel(new GridLayout(2,2,12,6));
		mainPanel.setBorder(BorderFactory.createEtchedBorder());
		//mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		//mainPanel.setLayout(null);
		mainPanel.setBounds(100, 80, 35, 20);
		
		mainPanel.add(labelCel);
		mainPanel.add(celField);
		mainPanel.add(labelFahr);
		mainPanel.add(fahrField);
		add(mainPanel, BorderLayout.NORTH);

		//adding to the button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(celToF);
		buttonPanel.add(fToCel);
		add(buttonPanel, BorderLayout.SOUTH);


		//methods for conversion
		fToCel.addActionListener(new FahrListener());
		celToF.addActionListener(new CelListener());
		

	}
	
	
	
	//adding menu bar
	private JMenuBar createMenuBar() {
		JMenuBar menu = new JMenuBar();
		
		//creating menu objects
		JMenu fileMenu = new JMenu("File");
		JMenu window = new JMenu("Window");
		
		//adding objects into menuBar
		menu.add(fileMenu);
		menu.add(window);
		
		//creating item for the FileMenu
		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		
		
		//creating item for About
		JMenuItem about = new JMenuItem("About");
		window.add(about);
		
		
		//set functionality for the items
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == about) {
				JOptionPane.showMessageDialog(CelsiusConvertor.this, "This is a app made by Lungu Catalin");
				//about.add(ab);
				}
			}
		});
		
		//setting up shorcuts
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exit.setMnemonic(KeyEvent.VK_X);
		window.setMnemonic(KeyEvent.VK_W);
		//adding an accelerator
		
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		return menu;
	}

	private class FahrListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == fToCel){
				int conFahToCel = (int) ((5.0/9.0 * (((Double.parseDouble(fahrField.getText())) -32))));
				celField.setText(conFahToCel + " °C");
				fahrField.requestFocus();
				fahrField.selectAll();
			} 

		} 
	} 

	private class CelListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == celToF){
				int conCelToFah = (int) (( 9.0/5.0 * (((Double.parseDouble(celField.getText())) + 32))));
				fahrField.setText(conCelToFah + " °F");
				celField.requestFocus();
				celField.selectAll();
			} 
		}
	}
}
