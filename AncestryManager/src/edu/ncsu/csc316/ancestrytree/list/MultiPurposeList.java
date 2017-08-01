package edu.ncsu.csc316.ancestrytree.list;

import java.util.Arrays;

/**
 * This class represents a Multi-purpose array based list
 * called MultiPurposeList (MPL). 
 * @author Ady
 *
 * @param <E> Generic Type
 */
public class MultiPurposeList<E> {
	
	private static final int CAPACITY = 30;
	private int size = 0;
	private E[] arr;
	
	/**
	 * Default constructor for MultiPurposeList (MPL).
	 * Initializes with a size of 30.
	 */
	public MultiPurposeList() {
		this(CAPACITY);
	}
	
	/**
	 * Parameterized private constructor
	 * @param cap the capacity of the MPL
	 */
	@SuppressWarnings("unchecked")
	private MultiPurposeList(int cap) {
		arr = (E[]) new Object[cap];
	}
	
	/**
	 * Checks the capacity of the array and increases
	 * it if the size of the MPL is equal to the length
	 * of the element array.
	 */
	private void checkCapacity() {
		if (size == arr.length) {
			int cap = arr.length * 2;
			arr = Arrays.copyOf(arr, cap);
		}
	}
	
	/**
	 * Adds an element to the end of MPL after checking capacity.
	 * @param data element to be added to the MPL
	 */
	public void add(E data) {
		checkCapacity();
		arr[size] = data;
		size++;
	}
	/**
	 * Add at a specific index
	 * @param index the index to add at
	 * @param data the data that is to be added at that index
	 */
	public void addAt(int index, E data) {
		arr[index] = data;			
	}
	
	/**
	 * Returns the element at the specified index
	 * @param index the index to retrieve the element from
	 * @return the element at the specified index
 	 * @throws IndexOutOfBoundsException if given an invalid index.
	 */
	public E get(int index) throws IndexOutOfBoundsException {
		if (!check(index))
			throw new IndexOutOfBoundsException();
		return arr[index];
	}
	
	/**
	 * Removes an element at the specified index
	 * @param index index of the element to be removed.
	 * @return the element to be removed.
	 * @throws IndexOutOfBoundsException if given an invalid index.
	 */
	public E remove(int index) throws IndexOutOfBoundsException {
		if (!check(index))
			throw new IndexOutOfBoundsException();

		E data = arr[index];
		arr[index] = null;
		size--;
		for (int i = index; i < size; i++) {
			arr[i] = arr[i + 1];
			arr[i + 1] = null;
		}
	
		return data;
	}
	
	/**
	 * Checks to see if valid index was passed.
	 * @param index index to be checked
	 * @return true if index is valid, false if
	 *         MPL is empty, index is less than 0
	 *         or if index is greater than or equal to size.
	 */
	private boolean check(int index) {
		if (isEmpty() || index < 0 || index >= size)
			return false;
		
		return true;
	}
	
	/**
	 * Returns the index of an element in the array.
	 * Returns -1 if element is not found
	 * @param data the element of which 
	 * @return the index of the element
	 */
	public int indexOf(E data) {

		for (int i = 0; i < arr.length; i++)
			if(data == arr[i])
				return i;
		
		return -1;
	}
	
	/**
	 * Checks to see if the MPL is empty
	 * @return true if the MPL is empty.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Returns the size of the MPL
	 * @return size of the MPL
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Sets the size of the MPL
	 * @param cap the size to set 
	 */
	public void setSize(int cap) {
		this.size = cap;
		if (cap > arr.length)
			arr = Arrays.copyOf(arr, cap);
		
	}
	
}
