package org.random;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;

public class RandomizerPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	JButton randomizeButton;
	SpringLayout currentLayout;
	//labels
	JLabel lbMinimumRange;
	JLabel lblMaximumRange;
	JLabel lblResult;
	JLabel titleLabel;
	//textPanes
	JTextField minPane;
	JTextField maxPane;
	JTextField resultPane;
	int randomInt;
	//JTextField test;




	public RandomizerPanel() {

		
		randomizeButton = new JButton("RANDOMIZE");
		randomizeButton.setFont(new Font("Dubai", Font.BOLD, 14));
		randomizeButton.setIcon(null);
		randomizeButton.setForeground(new Color(255, 140, 0));
		currentLayout = new SpringLayout();
		maxPane = new JTextField();
		lbMinimumRange = new JLabel("Minimum Range:");
		lblMaximumRange = new JLabel("Maximum Range :");
		lblResult = new JLabel("Result");
		minPane = new JTextField();
		titleLabel = new JLabel("");
		
		resultPane = new JTextField();
		resultPane.setEditable(false);
		titleLabel.setToolTipText("Made by Lungu Catalin © copyright 2017. All rights reserved");
		//setup the button and the position of the labels , textlabels
		currentLayout.putConstraint(SpringLayout.NORTH, randomizeButton, 270, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.WEST, randomizeButton, 10, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, randomizeButton, -28, SpringLayout.SOUTH, this);
		currentLayout.putConstraint(SpringLayout.EAST, randomizeButton, -10, SpringLayout.EAST, this);
		
		
		
	
		currentLayout.putConstraint(SpringLayout.NORTH, lbMinimumRange, 120, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.WEST, lbMinimumRange, 70, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.EAST, lbMinimumRange, -329, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.EAST, lblMaximumRange, -10, SpringLayout.WEST, maxPane);
		currentLayout.putConstraint(SpringLayout.NORTH, lblMaximumRange, 53, SpringLayout.SOUTH, lbMinimumRange);
		currentLayout.putConstraint(SpringLayout.WEST, lblMaximumRange, 70, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, lblMaximumRange, -62, SpringLayout.NORTH, randomizeButton);


		currentLayout.putConstraint(SpringLayout.NORTH, resultPane, 15, SpringLayout.SOUTH, lblResult);
		currentLayout.putConstraint(SpringLayout.WEST, resultPane, -13, SpringLayout.WEST, lblResult);
		currentLayout.putConstraint(SpringLayout.EAST, resultPane, 0, SpringLayout.EAST, lblResult);
		currentLayout.putConstraint(SpringLayout.WEST, lblResult, 396, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.EAST, lblResult, -38, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, lblResult, -211, SpringLayout.SOUTH, this);
	
		
		
		currentLayout.putConstraint(SpringLayout.NORTH, minPane, 0, SpringLayout.NORTH, lbMinimumRange);
		currentLayout.putConstraint(SpringLayout.WEST, minPane, 0, SpringLayout.WEST, maxPane);
		currentLayout.putConstraint(SpringLayout.EAST, minPane, 0, SpringLayout.EAST, maxPane);
		currentLayout.putConstraint(SpringLayout.WEST, maxPane, 196, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.EAST, maxPane, -196, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, minPane, -49, SpringLayout.NORTH, maxPane);
		currentLayout.putConstraint(SpringLayout.NORTH, maxPane, 188, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, maxPane, -63, SpringLayout.NORTH, randomizeButton);

		currentLayout.putConstraint(SpringLayout.WEST, titleLabel, 172, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, titleLabel, -45, SpringLayout.NORTH, minPane);
		currentLayout.putConstraint(SpringLayout.EAST, titleLabel, -171, SpringLayout.EAST, this);

		setupPanel();
		pushButton();
		
		
	}
	

		



	private void setupPanel() {

		setBackground(new Color(204, 204, 255));
		setPreferredSize(new Dimension(500, 350));

		this.setLayout(currentLayout);
		this.add(randomizeButton);
		add(minPane);
		add(lbMinimumRange);
		add(lblMaximumRange);
		add(maxPane);
		add(lblResult);
		add(resultPane);
		add(titleLabel);
		titleLabel.setIcon(new ImageIcon("C:\\Users\\LunguC\\Desktop\\Rand.png"));
		
		titleLabel.setToolTipText("Made by Lungu Catalin © copyright 2017. All rights reserved");
	
		

		


	}
	
	String resultText = "";
	private void pushButton() {

		Random randomizer = new Random();


		randomizeButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				int minVal = 0;
				int maxVal = 0;

				try {
					minVal = (Integer.valueOf(minPane.getText()));	//receive input from text field

					maxVal = (Integer.valueOf(maxPane.getText()));	//receive input from text field

					// validation between the specified range
					if ((minVal < 0) || (maxVal > 1000)) {
						JOptionPane.showMessageDialog(RandomizerPanel.this, "Minimum and maximum values must be between 0 and 1000");
					}


				} catch (NumberFormatException es) {
					JOptionPane.showMessageDialog(RandomizerPanel.this, "No text allowed. Input must be a number between 0 and 1000");
				}


				for (int i = minVal; i < maxVal; i++) {
					randomInt = randomizer.nextInt(maxVal);
					resultText = randomInt + "";
				}
				resultPane.setText(Integer.valueOf(resultText).toString());
				System.out.println(resultPane);
				
				
				

			}
			

		});

	}
}


////labelCel.setToolTipText("");






