/**
 * 
 */
package edu.ncsu.csc316.compression_manager.util;

import java.util.NoSuchElementException;

/**
 * @author Ady
 *
 */
public class LinkedList<E> {
	
	private class Node
	{
		E data;
		Node next;
		Node prev;
		

		public E getData(){
			return data;
		}
		
		@Override
		public String toString(){
			return data.toString();
		}
	}

	public Iterator<E> getIterator(){
		return new LinkedListIterator();
	}
	
	
    private Node front;
	private Node last;
	private int size;
	
	public LinkedList(){
		front = new Node();
		last = new Node();
		size = 0;
	}
	
	public int size(){
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void add (E data){
		Node n = new Node();
		n.data = data;
		Node current = front;
		
		if (isEmpty()){
			front.next = n;
			n.next = last;
			last.prev = n;
		}
		else {
			Node copy = front.next;
			front.next = n;
			n.next = copy;
			copy.prev = front.next;
		}

		size++;		
	}
	
	public boolean find(E data)
	{
		Node current = front.next;
		while (current.next != null){
			if (current.data.toString().equals(data.toString())) {
				return true;
			}
			current = current.next;
		}
		return false;
	}
	
	public String getFirstData(){
		return front.next.data.toString();
	}
	
	public String print(){
		Node current = front.next;
		String str= "";
		while (current.next != null){
			str += " " + current.data.toString();
			current = current.next;
		}
		return str;
	}
	
	
	private class LinkedListIterator implements Iterator<E>{
		private Node cursor = front.next;

		private int index = 0;
		private Node recent = null;
		
		
		public boolean hasNext(){
			return index < size();
		}
		
		public boolean hasPrevious(){
			return index > 0;
		}
		

		@Override
		public E next() {
			if (hasNext()) {
				E data = cursor.getData();
				recent = cursor;
				cursor = cursor.next;
				index++;
				return data;
			}
			else
				throw new NoSuchElementException();
		}

		@Override
		public E previous() {
			if (hasPrevious()){
				cursor = cursor.prev;
				recent = cursor;
				index--;
				return cursor.getData();
			}
			else
				throw new NoSuchElementException();
		}
		
		public void add(E data) {
			Node n = new Node();
			n.data = data;
			
			if (isEmpty()){
				front.next = n;
				n.next = last;
				last.prev = n;
			}
			else {
				Node copy = front.next;
				front.next = n;
				n.next = copy;
				copy.prev = front.next;
			}

			size++;		
		}
		

		public int remove(E data) {
			
			cursor = front;
			index = -1;
			
			if (isEmpty() || !find(data)){
				return index;
			}
			
			size--;
			if (front.next.data.toString().equals(data.toString())) {
				front = cursor.next;
				return 0;
			}

			while (next() != data){
				if (cursor == null)					
					return -1;
				else if (cursor != null && !hasNext()){
					index++;
					break;
				}
			}
			index--;
				
			 if (cursor == last) {
				cursor = cursor.prev;
				cursor.prev.next = last;
				last.prev = cursor;
			 }
			else {
				cursor.prev.next = cursor.next;
				cursor.next.prev = cursor.prev;
			}
			
			return index ;
		}
	}
	
}