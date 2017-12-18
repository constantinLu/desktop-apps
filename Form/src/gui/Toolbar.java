package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener {


	private JButton saveButton;
	private JButton refreshButton;
	private TextPanel textPanel;
	//8
	private ToolbarListener textListener;
 
	
	
	//constructor
	public Toolbar() {
		// Get rid of the border if you want the toolbar draggable
		//setBorder(BorderFactory.createEtchedBorder());
		setFloatable(false);



		//adding action to the buttons
		saveButton = new JButton();
		saveButton.addActionListener(this);
		saveButton.setIcon(Utils.createIcon("/images/Save16.gif"));
		saveButton.setToolTipText("Save");
		
		refreshButton = new JButton();
		refreshButton.addActionListener(this);
		refreshButton.setIcon(Utils.createIcon("/images/Refresh16.gif"));
		refreshButton.setToolTipText("Refresh");


		//adding a different layout
		//FlowLayout()
		//setLayout(new FlowLayout(FlowLayout.LEFT)); //starting to put the buttons from the left;
		//45
		add(saveButton);
		addSeparator();
		add(refreshButton);	

	}
	
	//44 adding images for the buttons 
	//method for adding images
	
	

	public void setToolbarListener(ToolbarListener listener) {
		this.textListener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//retrieves the actual source of the event which are the buttons
		JButton clicked = (JButton) e.getSource(); //returns an object
		if (clicked == saveButton) {
			if (textListener != null) {
				textListener.saveEventOccured();
			}
		} else if (clicked == refreshButton) {
			if (textListener != null) {
				textListener.refreshEventOccured();
			}
		}
	}

}
