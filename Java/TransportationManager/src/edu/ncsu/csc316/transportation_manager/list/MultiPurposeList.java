package edu.ncsu.csc316.transportation_manager.list;

import java.util.Arrays;

import edu.ncsu.csc316.transportation_manager.highway.Highway;

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
		if (size >= arr.length) {
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
	 * Adds a highway to the MPL in ascending order
	 * @param h the highway to add
	 */
	@SuppressWarnings("unchecked")
	public void addSorted(Highway h) {
		add((E)h);
		for (int i = size - 1; i > 0; i--) {
			Highway a = (Highway)get(i - 1);
			Highway b = (Highway)get(i);
			
			if (a.getCity1() > b.getCity1()) {
				Highway temp = (Highway) arr[i];
				arr[i] = arr[i - 1];
				arr[i - 1] = (E)temp;
			}
			else if (a.getCity1() == b.getCity1()) {
				if (a.getCity2() > b.getCity2()) {
					Highway temp = (Highway) arr[i];
					arr[i] = arr[i - 1];
					arr[i - 1] = (E)temp;
				}
				else if (a.getCity2() == b.getCity2()) {
					if (a.getCost() > b.getCost()) {
						Highway temp = (Highway) arr[i];
						arr[i] = arr[i - 1];
						arr[i - 1] = (E)temp;
					}
					else if (a.getCost() == b.getCost() && (a.getAsphalt() > b.getAsphalt())) {
						Highway temp = (Highway) arr[i];
						arr[i] = arr[i - 1];
						arr[i - 1] = (E)temp;
					}
				}
			}
			
		}
		
	}
	/**
	 * Adds an element at a specific index. 
	 * @param data the element to be added
	 * @param index the index to add
	 */
	public void addAt(E data, int index){
		arr[index] = data;
	}
	
	/**
	 * Sets the size of the MPL
	 * @param size new size of the MPL
	 */
	public void setSize(int size){
		this.size = size;
		checkCapacity();
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
	 * Sets the data in the array at a particular index
	 * @param data the new data
	 * @param index the index to set it at
	 */
	public void set(E data, int index) {
		arr[index] = data;
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
	
}
