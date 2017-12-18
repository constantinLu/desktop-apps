package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import controler.MessageServer;
import model.Message;


//50 



public class MessagePanel extends JPanel implements ProgressDialogListener {

	
	private JTree serverTree;
	private ServerTreeCellRenderer treeCellRenderer; //same as DefaultTreeCellRenderer
	private ServerTreeCellEditor treeCellEditor;
	private ProgressDialog progressDialog;
	
	//63
	private TextPanel textPanel;
	private JList messageList;
	private JSplitPane upperPane;
	private JSplitPane lowerPane;


	private Set<Integer> selectedServers;
	private MessageServer messageServer;
	private SwingWorker<List<Message>, Integer> worker;
	//64
	private DefaultListModel messageListModel;


	public MessagePanel(JFrame parent) {
		
		messageListModel = new DefaultListModel();
		progressDialog = new ProgressDialog(parent, "Messages Downloading. .  .");
		messageServer = new MessageServer();
		
		progressDialog.setListener(this);
		
		selectedServers = new TreeSet<Integer>();
		selectedServers.add(0);
		selectedServers.add(1);
		selectedServers.add(4);

		treeCellRenderer = new ServerTreeCellRenderer();

		serverTree = new JTree(createTree());
		serverTree.setCellRenderer(treeCellRenderer);
		treeCellEditor = new ServerTreeCellEditor();
		serverTree.setCellEditor(treeCellEditor);
		serverTree.setEditable(true);


		serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		
		messageServer.setSelectedServers(selectedServers);
		//54
		treeCellEditor.addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingCanceled(ChangeEvent arg0) {
			}
			@Override
			public void editingStopped(ChangeEvent arg0) {
				//System.out.println("Hello");
				ServerInfo info = (ServerInfo) treeCellEditor.getCellEditorValue();

				//System.out.println(info +  " : " + info.getId() + " : " + info.isChecked());

				int serverId = info.getId();
				if (info.isChecked()) {
					selectedServers.add(serverId);
				} else {
					selectedServers.remove(serverId);
				}

				messageServer.setSelectedServers(selectedServers);

				retrieveMessages();
			}

		});

		setLayout(new BorderLayout());
		textPanel = new TextPanel();
		messageList = new JList(messageListModel);
		//add the renderer from MessageListRenderer
		messageList.setCellRenderer(new MessageListRenderer());
		//66 
		messageList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Message message = (Message)messageList.getSelectedValue();
				
				textPanel.setText(message.getContents());
				 
				
			}
		});
		 

		//lowerPane63
		lowerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(messageList), textPanel);
		upperPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(serverTree), lowerPane);
		//setting the panes
		textPanel.setMinimumSize(new Dimension(10, 100));
		messageList.setMinimumSize(new Dimension(10, 100));
		//set the panel resize proportionaly
		upperPane.setResizeWeight(0.5);
		lowerPane.setResizeWeight(0.5);

		
		//adding the tree 
		add(upperPane, BorderLayout.CENTER);

	}
	
	//64
	public void refresh() {
		retrieveMessages();
	}

	//56
	private void retrieveMessages() {
		
		//System.out.println("Message waiting: " + messageServer.getMessageCount());
		progressDialog.setMaximum(messageServer.getMessageCount());
		progressDialog.setVisible(true);
			
		
	
		worker =  new SwingWorker<List<Message>, Integer>() {
			
			@Override
			protected void done() {
				progressDialog.setVisible(false);
				if(isCancelled()) return;
				try {
					List<Message>retrievedMessages = get();
					//64
					messageListModel.removeAllElements();
					for(Message message: retrievedMessages) {
						messageListModel.addElement(message);
					}
					
					//System.out.println("Retrieved " + retrievedMessages.size() + " messages.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				
				
			}
			@Override
			protected void process(List<Integer> counts) {
				int retrieved = counts.get(counts.size() - 1);
				progressDialog.setValue(retrieved);
			}
			@Override
			protected List<Message> doInBackground() throws Exception {
				List<Message> retrievedMessages = new ArrayList<Message>();
				int count = 0;
				for (Message message: messageServer) {
					
					if(isCancelled()) break;
					
					System.out.println(message.getTitle());
					retrievedMessages.add(message);
					count++;
					publish(count);
				}
				return retrievedMessages;
			}
		};
		worker.execute();
	}
	
	
	//creating the tree with the leaf
	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

		//branches
		DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
		DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(
				new ServerInfo("New York", 0, selectedServers.contains(0)));
		DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(
				new ServerInfo("Boston", 1, selectedServers.contains(3)));
		DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(
				new ServerInfo("Los Angeles", 2, selectedServers.contains(0)));

		branch1.add(server1);
		branch1.add(server2);
		branch1.add(server3);

		DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
		DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfo("London", 3, false));
		DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfo("Edinburgh", 4, true));

		branch2.add(server4);
		branch2.add(server5);

		top.add(branch1);
		top.add(branch2);

		return top;
	}

	@Override
	public void progressDialogCancelled() {
		if (worker != null) {
			worker.cancel(true);
		}
		System.out.println("Cancelled");
		
	}



}
