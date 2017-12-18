
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controler.Controller;

public class MainFrame extends JFrame {

	//private TextPanel textPanel;
	private Toolbar toolbar;
	private FormPanel formPanel;
	// 23 general file chooser
	private JFileChooser fileChooser;
	private Controller controller;
	private TablePanel tablePanel;
	private Preferences prefs;
	//32
	private PrefsDialog prefsDialog;
	//46
	private JSplitPane splitPane;
	//47
	private JTabbedPane tabPane;
	//48
	private MessagePanel messagePanel;

	public MainFrame() {
		super("Form");
		setLayout(new BorderLayout());

		// creating the objects
		toolbar = new Toolbar();
		//textPanel = new TextPanel();
		formPanel = new FormPanel();
		fileChooser = new JFileChooser();
		controller = new Controller();
		tablePanel = new TablePanel();
		prefsDialog = new PrefsDialog(this);
		tabPane = new JTabbedPane();
		messagePanel = new MessagePanel(this);
		splitPane =  new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tabPane); //left and right split
		splitPane.setOneTouchExpandable(true);
		
		tabPane.addTab("Person Database", tablePanel);
		tabPane.addTab("Messages", messagePanel);
		
		//tabPane.setTab
		//35 adding preferences object to mainFrame
		prefs = Preferences.userRoot().node("s");

		// setting the TablePanel
		tablePanel.setData(controller.getPeople());
		
		//31
		tablePanel.setPersonTableListener(new PersonTableListener() {
			public void rowDeleted(int row) {
				controller.removePerson(row);
				//tablePanel.refresh();
				//System.out.println(row);
			} 
		});
		
		//64
		//adding listener to the tabPane
		tabPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int tabIndex = tabPane.getSelectedIndex();
				//the the index of the tabs
				//index 0 = PersonDatabase
				//index 1 = Messages
				//System.out.println(tabIndex);
				
				if(tabIndex == 1) {
					messagePanel.refresh();
				}
				
			}
			
		});

		//35
		prefsDialog.setPrefListener(new PrefsListener() {

			@Override
			public void prefReferencesSet(String user, String password, int port) {
				//System.out.println(user + "  : " + password);
				prefs.put("user", user);
				prefs.put("password", password);
				prefs.putInt("port", port);
				
				//69
				try {
					controller.configure(port, user, password);
				} catch (Exception e ){
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to re-connect");
				}
			}
			
		}); 
		
		String user = prefs.get("user", "");
		String password = prefs.get("password", "");
		Integer port = prefs.getInt("port", 3306);
		
		
		prefsDialog.setDefaults(user, password, port);
		
		try {
			controller.configure(port, user, password);
		} catch (Exception e) {
			// This shouldn`t happen - database is not connected
		 System.err.println("Can`t connect to database");
		}
		
		
		// 24 adding filter
		fileChooser.addChoosableFileFilter(new PersonFileFilter());

		// seting the MenuBar to the MainFrame
		setJMenuBar(createMeniuBar());

		// setting the reference to the Toolbar
		toolbar.setToolbarListener(new ToolbarListener() {
	
			public void saveEventOccured() {
				connect();
				try {
					controller.save();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to the database", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
			
				}
				
			}
			public void refreshEventOccured() {
				//method down below
				refresh();
			}
			
		});

		// functionality form the formPanel to add the data to the TextPanel
		formPanel.setFormListener(new FormListener() {
			public void formEventOccurred(FormEvent e) {
				controller.addPerson(e);
				// refresh the table when adding data
				tablePanel.refresh();
			}
		});
		//43 add new Window Listener
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
			System.out.println("Window Closing");
			dispose();
			System.gc();
			}
			
			
		});

		// adding the features to the JFrame
		//add(formPanel, BorderLayout.WEST);
		add(toolbar, BorderLayout.PAGE_START);
		add(splitPane, BorderLayout.CENTER);

		
		//refresh again
		refresh();
		
		// setting the MainFrame
		setMinimumSize(new Dimension(500, 400));
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	private void connect() {
		try {
			controller.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to the database", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
		}
	}

	// creating menuBar and menues and menuItem
	private JMenuBar createMeniuBar() {
		JMenuBar menuBar = new JMenuBar();

		//creating a menu
		JMenu fileMenu = new JMenu("File");
		//creating  itemMenu for FileMenu
		JMenuItem exportDataItem = new JMenuItem("Export Data...");
		JMenuItem importDataItem = new JMenuItem("Import Data...");
		JMenuItem exitItem = new JMenuItem("Exit");

		//adding to the Menu
		fileMenu.add(exportDataItem);
		fileMenu.add(importDataItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		//creating and adding itemMenu and subMenu for WindowMenu
		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show"); 
		JMenuItem prefsItem = new JMenuItem("Preferences...");
		JMenu about = new JMenu("About");

		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
		showFormItem.setSelected(true);

		windowMenu.add(showMenu);
		windowMenu.add(prefsItem);
		showMenu.add(showFormItem);

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);
		
		//actionListener for prefsItem
		prefsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				prefsDialog.setVisible(true);
				
			}
			
		});

		//add actionListener to the menu
		showFormItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//source is JboxMenuItem
				//if check box is selected the formPanel is shown else is not shown
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) ev.getSource();
				//46 set the devider to default after showing the form on the left
				if (menuItem.isSelected()) {
					splitPane.setDividerLocation((int)formPanel.getMinimumSize().getWidth());
				}
				formPanel.setVisible(menuItem.isSelected());					
			}
		});
		//adding memonics
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);

		//adding acelerator
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

		//adding the actionlisiner for imporDatItem
		importDataItem.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					//28 adding functionality for the item
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file.", "Error", JOptionPane.ERROR_MESSAGE);
						//e1.printStackTrace();
					}
					//System.out.println(fileChooser.getSelectedFile());
				}
			}
		});

		exportDataItem.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						tablePanel.refresh();
						controller.saveToFile(fileChooser.getSelectedFile());	
					} catch (IOException e1) { 
						JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file.", "Error", JOptionPane.ERROR_MESSAGE);
						//System.out.println(fileChooser.getSelectedFile());
					}
				}
			}
		});



		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//String text = JOptionPane.showInputDialog(MainFrame.this, "EnterValue?","Confirm",JOptionPane.OK_OPTION| JOptionPane.QUESTION_MESSAGE);
				//System.out.println(text);
				//popUp window (confirmDialog)
				int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit?","Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				if (action == JOptionPane.OK_OPTION) {
					//System.exit(0);
					//43, get the window closing on the shorcuts aswel;
					WindowListener[] listeners = getWindowListeners();
					
					for (WindowListener listener : listeners) {
						listener.windowClosing(new WindowEvent(MainFrame.this, 0));
			
		}
					
				}
			}
		});
		return menuBar;

	}
	
	public void refresh() {
		
		try {
			controller.connect();
			//System.out.println("Refresh");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to the database", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			controller.load();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Unable to load from the database", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tablePanel.refresh();
	}
 
}
