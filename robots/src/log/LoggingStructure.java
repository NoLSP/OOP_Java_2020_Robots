package log;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LoggingStructure implements Iterable<LogEntry>, Iterator<LogEntry>
{
	private Node head;
	private Node tall;
	private Node next;
	private int maxSize;
	private int localSize = 0;
	
	public LoggingStructure(int size)
	{
		maxSize = size;
	}
	
	public void add(LogEntry message)
	{
		Node newHead = new Node(message);
		if(head != null) 
		{
			newHead.setNext(head);
			head.setPrevious(newHead);
		}
		head = newHead;
		if (tall == null) 
			tall = head;
		if(localSize == maxSize)
			tall = tall.previous();
			
		else
			localSize++;
		next = head;
	}
	
	public int size()
	{
		return localSize;
	}

	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public LogEntry next() {
		if(hasNext())
		{
			Node result = next;
			next = next.next();
			return result.message();
		}
		else throw new NoSuchElementException();
	}

	@Override
	public Iterator<LogEntry> iterator() {
		return this;
	}

	public Iterable<LogEntry> subList(int startFrom, int indexTo) {
		Iterator<LogEntry> iterator = new Iterator<LogEntry>() {
			private int localIndex = 0;
			private Node localHead = head;
			
			@Override
			public boolean hasNext() {
				while(localIndex < startFrom)
					localHead = localHead.next();
				return localHead.next() != null && localIndex < indexTo;
			}

			@Override
			public LogEntry next() {
				if (this.hasNext())
				{
					Node result = localHead;
					localHead = head.next();
					localIndex++;
					return result.message();
				}
				else
					throw new NoSuchElementException(); 
			}
			
		};
		
		return new Iterable<LogEntry>() {

			@Override
			public Iterator<LogEntry> iterator() {
				return iterator;
			}
			
		};
	}
}
