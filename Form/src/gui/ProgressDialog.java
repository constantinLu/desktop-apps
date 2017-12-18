package gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressDialog extends JDialog {

	private JButton cancelButton;
	private JProgressBar progressBar;
	private ProgressDialogListener listener;


	public ProgressDialog(Window parent, String title) {
		super(parent, title, ModalityType.APPLICATION_MODAL);

		
		cancelButton = new JButton("Cancel");
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		
		progressBar.setString("Retrieving messages. . . ");
		
		progressBar.setMaximum(10);

		//progressBar.setIndeterminate(true);

		//setSize(400, 200);
		//add from left to right
		setLayout(new FlowLayout());
		setLocationRelativeTo(parent);

		//set size of the progressBar
		Dimension size = cancelButton.getPreferredSize();
		size.width = 400;
		progressBar.setPreferredSize(size);


		add(progressBar);
		add(cancelButton);
		
		//functionality for cancelButton
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					if (listener != null) {
						listener.progressDialogCancelled();
						
					}
			}
			
		});
		//61 setting the "x" button on the frame of the progress bar
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (listener != null) {
					listener.progressDialogCancelled();
					
				}
			}
			
		});
		
		
		
		//srinks your frame around the controls
		pack();
	}
	
	//listener setter
	public void setListener(ProgressDialogListener listener) {
		this.listener = listener;
	}

	//set the maximum amonunt for the progressBar
	public void setMaximum(int value) {
		progressBar.setMaximum(value);
	}

	//
	public void setValue(int value) {
		
		int progress = 100 * value/progressBar.getMaximum();
		
		progressBar.setString(String.format("%d%% complete", progress));
		
		progressBar.setValue(value);
		
	}

	@Override
	public void setVisible(final boolean visible) {
		
		
		//if (visible)
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				if (visible == false) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					progressBar.setValue(0);
				}
				
				//61
				//set the cursor
				if(visible) {  
					//has inbuilt method
					//set the cursor over the progress bar to have the wait cursor
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
					
				} else {
					setCursor(Cursor.getDefaultCursor());
				}
				ProgressDialog.super.setVisible(visible);
			}
		});
	}

}
