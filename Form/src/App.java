

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gui.MainFrame;

public class App {

	public static void main(String[] args) {

		// run in multiplethreads
		//static method in swing utilities class, that implements the runnable interface with an anonymus object
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainFrame();
			} 
		});


	}
} 
