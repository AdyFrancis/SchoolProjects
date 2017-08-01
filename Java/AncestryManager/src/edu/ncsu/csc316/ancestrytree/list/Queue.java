package edu.ncsu.csc316.ancestrytree.list;

/**
 * This class represents the Queue arr structure 
 * that will be used through out the project.
 * @author Ady
 *
 * @param <E> Generic Java Type
 */
public class Queue<E> {
	
	/** Default capacity of the Queue*/
	private static final int CAPACITY = 30;
	
	/** Instance fields */
	private E[] arr;
	private int front = 0;
	private int rear = 0;
	private int size = 0;

	
	/** 
	 * Default constructor for the queue.
	 * Creates a queue with capacity 30.
	 */
	public Queue() {
		this(CAPACITY);
	}
	
	/**
	 * Parameterized constructor for Queue
	 * @param cap the capacity of the Queue
	 */
	@SuppressWarnings("unchecked")
	public Queue(int cap) {
		arr = (E[]) new Object[cap];
	}
	
	/** 
	 * Returns the size of the queue.
	 * @return the size of the queue.
	 */
	public int size(){
		return size;
	}
	
	/**
	 * Checks to see if the queue is empty.
	 * @return true if size equals 0
	 */
	public boolean isEmpty(){
		return size == 0;
	}
	
	/**
	 * Inserts an element at the end of the queue.
	 * @param element the element to insert
	 * @throws IllegalStateException if the queue is full.
	 */
	public void enqueue(E element) throws IllegalStateException {
		if (size == arr.length){
			throw new IllegalStateException("Queue is full.");
		}
		
		int open = (front + size) % arr.length;
		arr[open] = element;
		size++;
		
		rear = (rear + 1) % arr.length;
	}
	
	/**
	 * Returns the first element in the queue and removes it
	 * @return first element from the queue or null if the queue is empty
	 */
	public E dequeue() {
		if (isEmpty())
			return null;
		E data = arr[front];
		arr[front] = null;
		
		front = (front + 1) % arr.length;
		size--;

		return data;
	}
	
	/**
	 * Returns the first element in the queue but does not remove it.
	 * @return first element from the queue or null if the queue is empty
	 */
	public E peek() {
		if (isEmpty())
			return null;
		
		return arr[front];
	}
}
