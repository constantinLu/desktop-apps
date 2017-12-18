package controler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.Message;

/*
 * simulated message server 
 * example
 */

public class MessageServer implements Iterable<Message> {
	private Map<Integer, List<Message>> messages;
	private List<Message> selected;

	public MessageServer() {
		selected = new ArrayList<Message>();
		messages = new TreeMap<Integer, List<Message>>();

		List<Message> list = new ArrayList<Message>();
		list.add(new Message("The cat is missing", "Have you seen Felix anywhere?!"));
		list.add(new Message("See you later?", "Are we still meeting in the pub?"));
		messages.put(0, list);

		list.add(new Message("How about dinner later?", "Are you doing anything later on ?"));
		messages.put(1, list);

	} 
	// set the server
	public void setSelectedServers(Set<Integer> servers) {
		selected.clear();
		for (Integer id : servers) {
			if (messages.containsKey(id)) {
				List<Message> serverMessages = messages.get(id);
				selected.addAll(messages.get(id));
			}

		}
	}
	
	public int getMessageCount() {
		return selected.size();
	}
	@Override
	public Iterator<Message> iterator() {
		return new MessageIterator(selected);
	}
 
}


class MessageIterator implements Iterator {
 
	private Iterator<Message> iterator;
	
	public MessageIterator(List<Message> messages) {
		iterator = messages.iterator();
	}
	
	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Object next() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
				///
		}
		return iterator.next();
	}
	
	public void remove() {
		iterator.remove();
	}
	
}
