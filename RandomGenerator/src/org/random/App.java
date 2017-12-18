package org.random;
/*
 * 11.11.2017
 * @author Lungu Catalin
 * The Randomizer
 * 
 */
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
		//run
		//static method in swing utilities class, that implements the runnable interface with an anonymus object
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {


				try {
					Randomizer frame = new Randomizer();
					frame.setSize(new Dimension(500, 350));
					frame.setPreferredSize(new Dimension(500, 350));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
