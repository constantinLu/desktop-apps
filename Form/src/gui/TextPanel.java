package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPanel extends JPanel {
	
	
	private JTextArea textArea;
	
	public TextPanel() {
		textArea = new JTextArea();
		//indent the text
		textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		//setting the font to the textArea
		textArea.setFont(new Font("Serif", Font.PLAIN, 20));
		
		//putting the layout for the JPanel
		setLayout(new BorderLayout());
		//adding the ScrollBar in the pane text area
		add(new JScrollPane(textArea), BorderLayout.CENTER);	
	}
	
	//method for appending text
	//sends command in the mainFrame for adding text
	public void appendText(String text) {
		textArea.append(text);
	}
	public void setText(String text) { 
		textArea.setText(text);
	}
	

}
