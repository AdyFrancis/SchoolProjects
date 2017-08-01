package edu.ncsu.csc316.spell_checker.list;

import java.util.Arrays;


/**
 * This class represents a Multi-purpose array based list
 * called MultiPurposeList (MPL). 
 * @author Ady
 * @param <E> java generic
 */
public class MultiPurposeList<E extends String> {
	
	private static final int CAPACITY = 10;
	private int size = 0;
	private String[] arr;
	
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
		arr =  new String[cap];
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
	public void add(String data) 
	{
		checkCapacity();
		arr[size] =  data;
		size++;
	}

	/**
	 * Adds to the MPL in ascending order
	 * @param data the data to add
	 */
	public void addSorted(String data)
	{
		add(data);
		for (int i = size - 1; i > 0; i--) {
			String a = get(i - 1).toLowerCase();
			String b = get(i).toLowerCase(); 
			if (a.compareTo(b) > 0) {
				String temp = arr[i];
				arr[i] = arr[i - 1];
				arr[i - 1] = temp;
			}
				
		}
	}

	
	/**
	 * Returns the element at the specified index
	 * @param index the index to retrieve the element from
	 * @return the element at the specified index
 	 * @throws IndexOutOfBoundsException if given an invalid index.
	 */
	public String get(int index) throws IndexOutOfBoundsException {
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
	public String remove(int index) throws IndexOutOfBoundsException {
		if (!check(index))
			throw new IndexOutOfBoundsException();

		String data = arr[index];
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
	 * @param data the element to look for
	 * @return the index of the element
	 */
	public int indexOf(String data) {

		for (int i = 0; i < arr.length; i++)
			if(data.equals(arr[i]))
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
	 * Returns a string representation of the MPL
	 * @return string representation of the MPL
	 */
	public String toString() 
	{
		if (size == 0)
			return "";

		StringBuilder str = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (arr[i] != null)
				str.append(arr[i] + ", ");
		}
		return str.toString().substring(0, str.toString().length() - 2);
	}
	
}
