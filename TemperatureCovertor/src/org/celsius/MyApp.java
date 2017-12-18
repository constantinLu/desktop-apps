package org.celsius;
/*
 * 
 * 10.11.2017
 * @author LunguC.
 * info: created a simple temperature convertor
 */
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MyApp {

	public static void main(String[] args) {
		// run in multiplethreads
		//static method in swing utilities class, that implements the runnable interface with an anonymus object
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				//creating the frame
				CelsiusConvertor app = new CelsiusConvertor();
				//Setting up the JFrame
				Dimension dim = new Dimension(480,160);
				app.setSize(480,160);
				app.setMaximumSize(dim);
				app.setMinimumSize(dim);
				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				app.setVisible(true);
			}
		});

	}



}
